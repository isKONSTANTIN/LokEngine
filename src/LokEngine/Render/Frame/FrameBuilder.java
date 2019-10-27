package LokEngine.Render.Frame;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.PostProcessingActionWorker;
import LokEngine.Render.Window.Window;
import LokEngine.Tools.Utilities.Color.Color;

import java.util.Vector;

public class FrameBuilder {
    public Color glSceneClearColor = new Color(0.6f, 0.6f, 0.6f, 1);

    private Vector<PostProcessingActionWorker> postProcessingActionWorkers = new Vector<PostProcessingActionWorker>();
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

    public FrameBuilder(Window currectWin) {
        builderProperties = new BuilderProperties(currectWin);

        scenePartsBuilder = new PartsBuilder();
        GUIPartsBuilder = new PartsBuilder();
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
        builderProperties.useShader(builderProperties.getObjectShader());
        builderProperties.getBuilderWindow().getCamera().updateView();

        scenePartsBuilder.clearColor = glSceneClearColor;

        int preDisplayFrame = scenePartsBuilder.build(DrawMode.Scene, builderProperties);

        builderProperties.getBuilderWindow().getCanvas().update(GUIPartsBuilder, builderProperties.getBuilderWindow().getCanvas().properties);
        int GUIBuild = GUIPartsBuilder.build(DrawMode.RawGUI, builderProperties);

        for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
            preDisplayFrame = postProcessingActionWorker.render(preDisplayFrame);
        }

        builderProperties.useShader(builderProperties.getDisplayShader());
        DisplayDrawer.renderScreen(preDisplayFrame, builderProperties.getBuilderWindow());
        DisplayDrawer.renderScreen(GUIBuild, builderProperties.getBuilderWindow());
    }
}
