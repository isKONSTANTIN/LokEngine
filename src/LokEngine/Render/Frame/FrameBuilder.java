package LokEngine.Render.Frame;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FrameParts.PostProcessingActions.PostProcessingAction;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.Tools.DefaultFields;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;

import java.util.Vector;

public class FrameBuilder {

    private Vector<Vector<FramePart>> frameParts;
    private Vector<PostProcessingAction> postProcessingActions;
    private Window win;
    private FrameBufferWorker sceneFrameWorker;
    private FrameBufferWorker blurPostProcessingFrameWorker;
    private FrameBufferWorker blurSceneFrameWorker1;
    private FrameBufferWorker blurSceneFrameWorker2;
    private FrameBufferWorker blurSceneFrameWorker3;

    public Vector3f glSceneClearColor = new Vector3f(0.6f,0.6f,0.6f);

    public FrameBuilder(Window currectWin) {
        this.win = currectWin;
        frameParts = new Vector<>();
        postProcessingActions = new Vector<>();
        for (int i = 0; i < FramePartType.values().length; i++) {
            frameParts.add(i, new Vector<FramePart>());
        }

        sceneFrameWorker = new FrameBufferWorker(win.getResolution());
        blurPostProcessingFrameWorker = new FrameBufferWorker(win.getResolution());
        blurSceneFrameWorker1 = new FrameBufferWorker(win.getResolution());
        blurSceneFrameWorker2 = new FrameBufferWorker(win.getResolution());
        blurSceneFrameWorker3 = new FrameBufferWorker(win.getResolution());
    }

    public void addPart(FramePart fp) {
        frameParts.get(fp.frameType.index()).add(fp);
    }

    public void addPostProcessingAction(PostProcessingAction action){ postProcessingActions.add(action); }

    public void build() {
        sceneFrameWorker.bindFrameBuffer();
        win.setDrawMode(DrawMode.Scene);

        GL11.glClearColor(glSceneClearColor.x,glSceneClearColor.y,glSceneClearColor.z,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (int fp = 0; fp < frameParts.get(FramePartType.Scene.index()).size(); fp++) {
            frameParts.get(FramePartType.Scene.index()).get(fp).partRender();
        }
        frameParts.get(FramePartType.Scene.index()).clear();

        sceneFrameWorker.unbindCurrentFrameBuffer();
        blurPostProcessingFrameWorker.bindFrameBuffer();

        win.setDrawMode(DrawMode.RawGUI);

        GL11.glClearColor(0,0,0,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < postProcessingActions.size(); i++){
            postProcessingActions.get(i).apply();
        }
        postProcessingActions.clear();
        blurPostProcessingFrameWorker.unbindCurrentFrameBuffer();

        int preDisplayFrame = DisplayDrawer.blurPostProcess(win,blurPostProcessingFrameWorker.getTexture(),sceneFrameWorker.getTexture(),blurSceneFrameWorker1,blurSceneFrameWorker2,blurSceneFrameWorker3);

        Shader.use(DefaultFields.DisplayShader);
        DisplayDrawer.renderScreen(preDisplayFrame);

        win.setDrawMode(DrawMode.RawGUI);
        for (int fp = 0; fp < frameParts.get(FramePartType.GUI.index()).size(); fp++) {
            frameParts.get(FramePartType.GUI.index()).get(fp).partRender();
        }
        frameParts.get(FramePartType.GUI.index()).clear();
    }
}
