package LokEngine.Render.Frame;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.PostProcessingActionWorker;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Utilities.Color;

import java.util.Vector;

public class FrameBuilder {

    public Window window;
    public Color glSceneClearColor = new Color(0.6f,0.6f,0.6f, 1);

    private Vector<PostProcessingActionWorker> postProcessingActionWorkers = new Vector<PostProcessingActionWorker>();
    private PartsBuilder scenePartsBuilder;
    private PartsBuilder GUIPartsBuilder;

    public FrameBuilder(Window currectWin) {
        this.window = currectWin;

        scenePartsBuilder = new PartsBuilder(window.getResolution());
        GUIPartsBuilder = new PartsBuilder(window.getResolution());
    }

    public void addPart(FramePart fp) {
        scenePartsBuilder.addPart(fp);
    }

    public void addPostProcessingActionWorker(PostProcessingActionWorker worker){ postProcessingActionWorkers.add(worker); }

    public PostProcessingActionWorker getPostProcessingActionWorker(String name){
        for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
            if (postProcessingActionWorker.getName().equals(name)) {
                return postProcessingActionWorker;
            }
        }
        return null;
    }

    public void build(GUICanvas rootCanvas) {
        scenePartsBuilder.clearColor = glSceneClearColor;

        int preDisplayFrame = scenePartsBuilder.build(DrawMode.Scene);

        rootCanvas.update(GUIPartsBuilder, rootCanvas.getPosition());
        int GUIBuild = GUIPartsBuilder.build(DrawMode.RawGUI);

        for (PostProcessingActionWorker postProcessingActionWorker : postProcessingActionWorkers) {
            preDisplayFrame = postProcessingActionWorker.render(preDisplayFrame);
        }

        Shader.use(DefaultFields.displayShader);
        DisplayDrawer.renderScreen(preDisplayFrame);
        DisplayDrawer.renderScreen(GUIBuild);
    }
}
