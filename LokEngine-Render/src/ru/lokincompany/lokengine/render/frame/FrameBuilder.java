package ru.lokincompany.lokengine.render.frame;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.postprocessing.workers.PostProcessingActionWorker;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.color.Color;

import java.util.Vector;

public class FrameBuilder {
    public Color backgroundColor = new Color(0.6f, 0.6f, 0.6f, 1);

    private Vector<PostProcessingActionWorker> postProcessingActionWorkers = new Vector<>();
    private PartsBuilder scenePartsBuilder;
    private PartsBuilder GUIPartsBuilder;
    private RenderProperties renderProperties;

    public FrameBuilder(Window correctWin) {
        renderProperties = new RenderProperties(correctWin);

        scenePartsBuilder = new PartsBuilder(correctWin.getResolution());
        GUIPartsBuilder = new PartsBuilder(correctWin.getResolution());
    }

    public FrameBuilder(Window correctWin, int samples) {
        renderProperties = new RenderProperties(correctWin);

        scenePartsBuilder = new PartsBuilder(correctWin.getResolution(), samples);
        GUIPartsBuilder = new PartsBuilder(correctWin.getResolution());
    }

    public PartsBuilder getScenePartsBuilder() {
        return scenePartsBuilder;
    }

    public PartsBuilder getGUIPartsBuilder() {
        return GUIPartsBuilder;
    }

    public RenderProperties getRenderProperties() {
        return renderProperties;
    }

    public void addPostProcessingActionWorker(PostProcessingActionWorker worker) {
        postProcessingActionWorkers.add(worker);
    }

    public <T extends PostProcessingActionWorker> T getPostProcessingActionWorker(Class<T> actionWorker) {
        for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
            if (postProcessingActionWorker.getClass().getName().equals(actionWorker.getName())) {
                return (T) postProcessingActionWorker;
            }
        }
        return null;
    }

    public void build() {
        int sceneFrame = -1;
        int GUIBuild = -1;

        Window window = renderProperties.getBuilderWindow();

        if (scenePartsBuilder.frameParts.size() > 0) {
            renderProperties.getObjectShader().update(window.getCamera());

            sceneFrame = scenePartsBuilder.build(DrawMode.Scene, renderProperties);

            for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
                sceneFrame = postProcessingActionWorker.render(sceneFrame);
            }
        }

        if (GUIPartsBuilder.frameParts.size() > 0)
            GUIBuild = GUIPartsBuilder.build(DrawMode.RawGUI, renderProperties);

        renderProperties.useShader(renderProperties.getDisplayShader());

        GL11.glClearColor(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        if (sceneFrame != -1)
            DisplayDrawer.renderScreen(sceneFrame, window);
        if (GUIBuild != -1)
            DisplayDrawer.renderScreen(GUIBuild, window);
    }
}
