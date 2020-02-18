package ru.lokincompany.lokengine.render.postprocessing.workers.blur;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.frame.DisplayDrawer;
import ru.lokincompany.lokengine.render.frame.FrameBufferWorker;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.postprocessing.actions.PostProcessingAction;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurAction;
import ru.lokincompany.lokengine.render.postprocessing.workers.PostProcessingActionWorker;
import ru.lokincompany.lokengine.render.window.Window;

import static org.lwjgl.opengl.GL11.*;

public class BlurActionWorker extends PostProcessingActionWorker {

    private Shader shader;
    private FrameBufferWorker blurPostProcessingFrameWorker;
    private FrameBufferWorker blurSceneFrameWorker1;
    private FrameBufferWorker blurSceneFrameWorker2;
    private FrameBufferWorker blurSceneFrameWorker3;
    private Window window;

    public BlurActionWorker(Window window) {
        blurPostProcessingFrameWorker = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker1 = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker2 = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker3 = new FrameBufferWorker(window.getResolution());
        this.window = window;

        shader = new Shader("#/resources/shaders/blur/BlurVertShader.glsl", "#/resources/shaders/blur/BlurFragShader.glsl") {
            @Override
            public void update(Camera activeCamera) {
                activeCamera.getWindow().getFrameBuilder().getRenderProperties().useShader(this);
                setProjection(window.getResolution().x, window.getResolution().y, 1);
            }
        };
        shader.update(window.getCamera());
    }

    private int blurPostProcess(int postFrame, int originalFrame) {
        checkResizeWindow();
        RenderProperties renderProperties = window.getFrameBuilder().getRenderProperties();
        blurSceneFrameWorker1.bindFrameBuffer(DrawMode.Display, renderProperties);
        renderProperties.useShader(shader);
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, renderProperties);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        shader.setUniformData("direction", new Vector2f(0, 1));
        DisplayDrawer.renderScreen(originalFrame, window);

        blurSceneFrameWorker1.unbindCurrentFrameBuffer();
        blurSceneFrameWorker2.bindFrameBuffer(DrawMode.Display, renderProperties);
        renderProperties.useShader(shader);
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, renderProperties);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        shader.setUniformData("direction", new Vector2f(0.866f / ((float) window.getResolution().x / (float) window.getResolution().y), 0.5f));
        DisplayDrawer.renderScreen(blurSceneFrameWorker1.getTextureBuffer(), window);

        blurSceneFrameWorker2.unbindCurrentFrameBuffer();
        blurSceneFrameWorker3.bindFrameBuffer(DrawMode.Display, renderProperties);
        renderProperties.useShader(shader);
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, renderProperties);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        shader.setUniformData("direction", new Vector2f(0.866f / ((float) window.getResolution().x / (float) window.getResolution().y), -0.5f));
        DisplayDrawer.renderScreen(blurSceneFrameWorker2.getTextureBuffer(), window);

        renderProperties.unUseShader();
        blurSceneFrameWorker3.unbindCurrentFrameBuffer();

        return blurSceneFrameWorker3.getTextureBuffer();
    }

    public int onceRender(int sourceFrame, BlurAction blurAction) {
        checkResizeWindow();
        blurPostProcessingFrameWorker.bindFrameBuffer(DrawMode.RawGUI, window.getFrameBuilder().getRenderProperties());
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        blurAction.apply();

        blurPostProcessingFrameWorker.unbindCurrentFrameBuffer();

        return blurPostProcess(blurPostProcessingFrameWorker.getTextureBuffer(), sourceFrame);
    }

    public int onceRender(int sourceFrame, int blurAction) {
        return blurPostProcess(blurAction, sourceFrame);
    }

    private void checkResizeWindow() {
        if (!blurPostProcessingFrameWorker.getResolution().equals(window.getResolution())) {
            shader.update(window.getCamera());
            blurPostProcessingFrameWorker.setResolution(window.getResolution());
        }
    }

    @Override
    public int render(int sourceFrame) {
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        if (postProcessingActions.size() > 0) {
            checkResizeWindow();
            blurPostProcessingFrameWorker.bindFrameBuffer(DrawMode.RawGUI, window.getFrameBuilder().getRenderProperties());

            GL11.glClearColor(0, 0, 0, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            for (PostProcessingAction postProcessingAction : postProcessingActions) {
                postProcessingAction.apply();
            }
            postProcessingActions.clear();
            blurPostProcessingFrameWorker.unbindCurrentFrameBuffer();

            return blurPostProcess(blurPostProcessingFrameWorker.getTextureBuffer(), sourceFrame);
        }
        return sourceFrame;
    }
}
