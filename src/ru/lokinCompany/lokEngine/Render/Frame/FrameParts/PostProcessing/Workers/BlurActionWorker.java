package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Workers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import ru.lokinCompany.lokEngine.Loaders.ShaderLoader;
import ru.lokinCompany.lokEngine.Render.Enums.DrawMode;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Render.Frame.DisplayDrawer;
import ru.lokinCompany.lokEngine.Render.Frame.FrameBufferWorker;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Actions.BlurAction;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Actions.PostProcessingAction;
import ru.lokinCompany.lokEngine.Render.Shader;
import ru.lokinCompany.lokEngine.Render.Window.Window;

public class BlurActionWorker extends PostProcessingActionWorker {

    private Shader shader;
    private FrameBufferWorker blurPostProcessingFrameWorker;
    private FrameBufferWorker blurSceneFrameWorker1;
    private FrameBufferWorker blurSceneFrameWorker2;
    private FrameBufferWorker blurSceneFrameWorker3;
    private Window window;

    public String getName() {
        return "Blur Action Worker";
    }

    public BlurActionWorker(Window window) throws Exception {
        blurPostProcessingFrameWorker = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker1 = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker2 = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker3 = new FrameBufferWorker(window.getResolution());
        this.window = window;
        shader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl", "#/resources/shaders/BlurFragShader.glsl");

        window.getFrameBuilder().getBuilderProperties().useShader(shader);
        window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);
        window.getFrameBuilder().getBuilderProperties().unUseShader();
    }

    private int blurPostProcess(int postFrame, int originalFrame) {
        checkResizeWindow();
        BuilderProperties builderProperties = window.getFrameBuilder().getBuilderProperties();
        blurSceneFrameWorker1.bindFrameBuffer(DrawMode.Display, builderProperties);
        builderProperties.useShader(shader);
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, builderProperties);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUniform2f(GL20.glGetUniformLocation(builderProperties.getActiveShader().program, "direction"), 0, 1);
        DisplayDrawer.renderScreen(originalFrame, window);

        blurSceneFrameWorker1.unbindCurrentFrameBuffer();
        blurSceneFrameWorker2.bindFrameBuffer(DrawMode.Display, builderProperties);
        builderProperties.useShader(shader);
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, builderProperties);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUniform2f(GL20.glGetUniformLocation(builderProperties.getActiveShader().program, "direction"), 0.866f / ((float) window.getResolution().x / (float) window.getResolution().y), 0.5f);
        DisplayDrawer.renderScreen(blurSceneFrameWorker1.getTexture(), window);

        blurSceneFrameWorker2.unbindCurrentFrameBuffer();
        blurSceneFrameWorker3.bindFrameBuffer(DrawMode.Display, builderProperties);
        builderProperties.useShader(shader);
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, builderProperties);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUniform2f(GL20.glGetUniformLocation(builderProperties.getActiveShader().program, "direction"), 0.866f / ((float) window.getResolution().x / (float) window.getResolution().y), -0.5f);
        DisplayDrawer.renderScreen(blurSceneFrameWorker2.getTexture(), window);

        builderProperties.unUseShader();
        blurSceneFrameWorker3.unbindCurrentFrameBuffer();

        return blurSceneFrameWorker3.getTexture();
    }

    public int onceRender(int sourceFrame, BlurAction blurAction) {
        checkResizeWindow();
        blurPostProcessingFrameWorker.bindFrameBuffer(DrawMode.RawGUI, window.getFrameBuilder().getBuilderProperties());
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        blurAction.apply();

        blurPostProcessingFrameWorker.unbindCurrentFrameBuffer();

        return blurPostProcess(blurPostProcessingFrameWorker.getTexture(), sourceFrame);
    }

    public int onceRender(int sourceFrame, int blurAction) {
        return blurPostProcess(blurAction, sourceFrame);
    }

    private void checkResizeWindow(){
        if (!blurPostProcessingFrameWorker.getResolution().equals(window.getResolution())){
            window.getFrameBuilder().getBuilderProperties().useShader(shader);
            window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);
            blurPostProcessingFrameWorker.setResolution(window.getResolution());
        }
    }

    @Override
    public int render(int sourceFrame) {
        if (postProcessingActions.size() > 0){
            checkResizeWindow();
            blurPostProcessingFrameWorker.bindFrameBuffer(DrawMode.RawGUI, window.getFrameBuilder().getBuilderProperties());

            GL11.glClearColor(0, 0, 0, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            for (PostProcessingAction postProcessingAction : postProcessingActions) {
                postProcessingAction.apply();
            }
            postProcessingActions.clear();
            blurPostProcessingFrameWorker.unbindCurrentFrameBuffer();

            return blurPostProcess(blurPostProcessingFrameWorker.getTexture(), sourceFrame);
        }
        return sourceFrame;
    }
}
