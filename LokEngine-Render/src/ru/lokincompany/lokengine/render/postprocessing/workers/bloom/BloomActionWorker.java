package ru.lokincompany.lokengine.render.postprocessing.workers.bloom;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.render.camera.Camera;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.frame.DisplayDrawer;
import ru.lokincompany.lokengine.render.frame.FrameBufferWorker;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.postprocessing.workers.PostProcessingActionWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.blur.BlurActionWorker;
import ru.lokincompany.lokengine.render.window.Window;

import static org.lwjgl.opengl.GL11.*;

public class BloomActionWorker extends PostProcessingActionWorker {

    private FrameBufferWorker frameBufferWorker1;
    private FrameBufferWorker frameBufferWorker2;
    private Shader filterShader;
    private Shader mixerShader;
    private Window window;

    private int blurAction;
    private BloomSettings bloomSettings;

    public BloomActionWorker(Window window) {
        this.frameBufferWorker1 = new FrameBufferWorker(window.getResolution());
        this.frameBufferWorker2 = new FrameBufferWorker(window.getResolution());
        this.window = window;

        filterShader = new Shader("#/resources/shaders/bloom/BloomFilterVertShader.glsl", "#/resources/shaders/bloom/BloomFilterFragShader.glsl") {
            @Override
            public void update(Camera activeCamera) {
                Window window = activeCamera.getWindow();
                window.getFrameBuilder().getRenderProperties().useShader(this);
                setRawOrthoProjection(window.getResolution().x, window.getResolution().y, 1);
            }
        };

        mixerShader = new Shader("#/resources/shaders/bloom/BloomMixerVertShader.glsl", "#/resources/shaders/bloom/BloomMixerFragShader.glsl") {
            @Override
            public void update(Camera activeCamera) {
                Window window = activeCamera.getWindow();
                window.getFrameBuilder().getRenderProperties().useShader(this);
                setRawOrthoProjection(window.getResolution().x, window.getResolution().y, 1);
            }
        };

        filterShader.update(window.getActiveCamera());
        mixerShader.update(window.getActiveCamera());
    }

    public BloomSettings getBloomSettings() {
        return bloomSettings;
    }

    public void setBloomSettings(BloomSettings bloomSettings) {
        this.blurAction = bloomSettings.getBlurTexture(window);
        this.bloomSettings = bloomSettings;
    }

    @Override
    public int render(int sourceFrame) {
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        if (blurAction == 0) return sourceFrame;

        if (!frameBufferWorker1.getResolution().equals(window.getResolution())) {
            filterShader.update(window.getActiveCamera());
            mixerShader.update(window.getActiveCamera());
        }

        BlurActionWorker blur = window.getFrameBuilder().getPostProcessingActionWorker(BlurActionWorker.class);
        RenderProperties renderProperties = window.getFrameBuilder().getRenderProperties();

        frameBufferWorker1.bindFrameBuffer(DrawMode.Display, renderProperties);
        renderProperties.useShader(filterShader);
        filterShader.setUniformData("BrightnessLimit", bloomSettings.brightnessLimit);

        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        DisplayDrawer.renderScreen(sourceFrame, window);

        frameBufferWorker1.unbindCurrentFrameBuffer();

        glDisable(GL_ALPHA_TEST);
        int blured = blur.onceRender(frameBufferWorker1.getTextureBuffer(), blurAction);

        frameBufferWorker2.bindFrameBuffer(DrawMode.Display, renderProperties);

        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        renderProperties.useShader(mixerShader);
        DisplayDrawer.bindTexture("frame2", blured, 1, renderProperties);
        DisplayDrawer.renderScreen(sourceFrame, window);

        frameBufferWorker2.unbindCurrentFrameBuffer();

        return frameBufferWorker2.getTextureBuffer();
    }
}
