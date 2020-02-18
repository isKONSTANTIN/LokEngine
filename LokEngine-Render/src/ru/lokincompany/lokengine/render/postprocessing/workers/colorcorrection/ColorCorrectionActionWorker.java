package ru.lokincompany.lokengine.render.postprocessing.workers.colorcorrection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.frame.DisplayDrawer;
import ru.lokincompany.lokengine.render.frame.FrameBufferWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.PostProcessingActionWorker;
import ru.lokincompany.lokengine.render.window.Window;

import static org.lwjgl.opengl.GL11.*;

public class ColorCorrectionActionWorker extends PostProcessingActionWorker {
    Shader shader;
    FrameBufferWorker frameBufferWorker;
    Window window;
    ColorCorrectionSettings settings;

    public ColorCorrectionActionWorker(Window window) {
        this.window = window;
        shader = new Shader("#/resources/shaders/colorCorrection/ColorCorrectionVertShader.glsl", "#/resources/shaders/colorCorrection/ColorCorrectionFragShader.glsl") {
            @Override
            public void update(Camera activeCamera) {
                Window window = activeCamera.getWindow();
                window.getFrameBuilder().getRenderProperties().useShader(this);
                setProjection(window.getResolution().x, window.getResolution().y, 1);
            }
        };
        shader.update(window.getCamera());
        frameBufferWorker = new FrameBufferWorker(window.getResolution());
    }

    public ColorCorrectionSettings getColorCorrectionSettings() {
        return settings;
    }

    public void setColorCorrectionSettings(ColorCorrectionSettings settings) {
        this.settings = settings;
    }

    @Override
    public int render(int sourceFrame) {
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        if (settings == null) return sourceFrame;

        checkResizeWindow();
        frameBufferWorker.bindFrameBuffer(DrawMode.RawGUI, window.getFrameBuilder().getRenderProperties());
        window.getFrameBuilder().getRenderProperties().useShader(shader);

        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        shader.setUniformData("Exposure", settings.exposure);
        shader.setUniformData("Gamma", settings.gamma);
        shader.setUniformData("ColorMultiplication", new Vector3f(settings.colorMultiplication.red, settings.colorMultiplication.green, settings.colorMultiplication.blue));
        shader.setUniformData("ColorAddition", new Vector3f(settings.colorAddition.red, settings.colorAddition.green, settings.colorAddition.blue));

        DisplayDrawer.renderScreen(sourceFrame, window);

        frameBufferWorker.unbindCurrentFrameBuffer();

        return frameBufferWorker.getTextureBuffer();
    }

    private void checkResizeWindow() {
        if (!frameBufferWorker.getResolution().equals(window.getResolution())) {
            shader.update(window.getCamera());
            frameBufferWorker.setResolution(window.getResolution());
        }
    }

}
