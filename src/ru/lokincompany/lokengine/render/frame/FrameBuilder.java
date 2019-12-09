package ru.lokincompany.lokengine.render.frame;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.frame.frameparts.postprocessing.workers.PostProcessingActionWorker;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

import java.util.Vector;

public class FrameBuilder {
    public Color backgroundColor = new Color(0.6f, 0.6f, 0.6f, 1);

    private Vector<PostProcessingActionWorker> postProcessingActionWorkers = new Vector<>();
    private PartsBuilder scenePartsBuilder;
    private PartsBuilder GUIPartsBuilder;
    private BuilderProperties builderProperties;

    public PartsBuilder getScenePartsBuilder() {
        return scenePartsBuilder;
    }

    public PartsBuilder getGUIPartsBuilder() {
        return GUIPartsBuilder;
    }

    public BuilderProperties getBuilderProperties() {
        return builderProperties;
    }

    public FrameBuilder(Window correctWin) {
        builderProperties = new BuilderProperties(correctWin);

        scenePartsBuilder = new PartsBuilder(correctWin.getResolution());
        GUIPartsBuilder = new PartsBuilder(correctWin.getResolution());
    }

    public void addPostProcessingActionWorker(PostProcessingActionWorker worker) {
        postProcessingActionWorkers.add(worker);
    }

    public PostProcessingActionWorker getPostProcessingActionWorker(String name) {
        for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
            if (postProcessingActionWorker.getName().equals(name)) {
                return postProcessingActionWorker;
            }
        }
        return null;
    }

    public void build() {
        int sceneFrame = -1;
        int GUIBuild = -1;

        Window window = builderProperties.getBuilderWindow();

        if (scenePartsBuilder.frameParts.size() > 0) {
            builderProperties.useShader(builderProperties.getObjectShader());
            window.getCamera().updateView();

            sceneFrame = scenePartsBuilder.build(DrawMode.Scene, builderProperties);

            for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
                sceneFrame = postProcessingActionWorker.render(sceneFrame);
            }
        }

        window.getCanvas().update(GUIPartsBuilder, window.getCanvas().properties);
        if (GUIPartsBuilder.frameParts.size() > 0)
            GUIBuild = GUIPartsBuilder.build(DrawMode.RawGUI, builderProperties);

        builderProperties.useShader(builderProperties.getDisplayShader());

        GL11.glClearColor(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        if (sceneFrame != -1)
            DisplayDrawer.renderScreen(sceneFrame, window);
        if (GUIBuild != -1)
            DisplayDrawer.renderScreen(GUIBuild, window);
    }
}
