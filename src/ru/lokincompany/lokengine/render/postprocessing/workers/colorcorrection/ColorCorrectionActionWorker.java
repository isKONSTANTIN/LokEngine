package ru.lokincompany.lokengine.render.postprocessing.workers.colorcorrection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.loaders.ShaderLoader;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.frame.DisplayDrawer;
import ru.lokincompany.lokengine.render.frame.FrameBufferWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.PostProcessingActionWorker;
import ru.lokincompany.lokengine.render.window.Window;

public class ColorCorrectionActionWorker extends PostProcessingActionWorker {
    Shader shader;
    FrameBufferWorker frameBufferWorker;
    Window window;
    ColorCorrectionSettings settings;

    public ColorCorrectionActionWorker(Window window) throws Exception {
        this.window = window;
        shader = ShaderLoader.loadShader("#/resources/shaders/colorCorrection/ColorCorrectionVertShader.glsl", "#/resources/shaders/colorCorrection/ColorCorrectionFragShader.glsl");
        frameBufferWorker = new FrameBufferWorker(window.getResolution());

        window.getFrameBuilder().getRenderProperties().useShader(shader);
        window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);
        window.getFrameBuilder().getRenderProperties().unUseShader();
    }

    public ColorCorrectionSettings getColorCorrectionSettings() {
        return settings;
    }

    public void setColorCorrectionSettings(ColorCorrectionSettings settings) {
        this.settings = settings;
    }

    @Override
    public int render(int sourceFrame) {
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
            window.getFrameBuilder().getRenderProperties().useShader(shader);
            window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);
            frameBufferWorker.setResolution(window.getResolution());
        }
    }

}
