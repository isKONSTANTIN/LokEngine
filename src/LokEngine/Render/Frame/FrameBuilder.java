package LokEngine.Render.Frame;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Actions.PostProcessingAction;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.PostProcessingActionWorker;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.Tools.DefaultFields;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;

import java.util.Vector;

public class FrameBuilder {

    private Vector<Vector<FramePart>> frameParts;
    private Vector<PostProcessingActionWorker> postProcessingActionWorkers = new Vector<PostProcessingActionWorker>();
    public Window window;
    private FrameBufferWorker sceneFrameWorker;

    public Vector3f glSceneClearColor = new Vector3f(0.6f,0.6f,0.6f);

    public FrameBuilder(Window currectWin) {
        this.window = currectWin;
        frameParts = new Vector<>();
        for (int i = 0; i < FramePartType.values().length; i++) {
            frameParts.add(i, new Vector<FramePart>());
        }

        sceneFrameWorker = new FrameBufferWorker(window.getResolution());
    }

    public void addPart(FramePart fp) {
        frameParts.get(fp.frameType.index()).add(fp);
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

    public void build() {
        sceneFrameWorker.bindFrameBuffer();
        window.setDrawMode(DrawMode.Scene);

        GL11.glClearColor(glSceneClearColor.x,glSceneClearColor.y,glSceneClearColor.z,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (int fp = 0; fp < frameParts.get(FramePartType.Scene.index()).size(); fp++) {
            frameParts.get(FramePartType.Scene.index()).get(fp).partRender();
        }
        frameParts.get(FramePartType.Scene.index()).clear();

        sceneFrameWorker.unbindCurrentFrameBuffer();

        int preDisplayFrame = sceneFrameWorker.getTexture();

        for (int i = 0; i < postProcessingActionWorkers.size(); i++){
            preDisplayFrame = postProcessingActionWorkers.get(i).render(preDisplayFrame);
        }

        Shader.use(DefaultFields.DisplayShader);
        DisplayDrawer.renderScreen(preDisplayFrame);

        window.setDrawMode(DrawMode.RawGUI);
        for (int fp = 0; fp < frameParts.get(FramePartType.GUI.index()).size(); fp++) {
            frameParts.get(FramePartType.GUI.index()).get(fp).partRender();
        }
        frameParts.get(FramePartType.GUI.index()).clear();
    }
}
