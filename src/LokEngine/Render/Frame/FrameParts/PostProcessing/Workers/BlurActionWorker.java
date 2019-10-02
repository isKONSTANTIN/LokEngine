package LokEngine.Render.Frame.FrameParts.PostProcessing.Workers;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Frame.DisplayDrawer;
import LokEngine.Render.Frame.FrameBufferWorker;
import LokEngine.Render.Window.Window;
import org.lwjgl.opengl.GL11;

public class BlurActionWorker extends PostProcessingActionWorker {

    private FrameBufferWorker blurPostProcessingFrameWorker;
    private FrameBufferWorker blurSceneFrameWorker1;
    private FrameBufferWorker blurSceneFrameWorker2;
    private FrameBufferWorker blurSceneFrameWorker3;
    private Window window;

    public String getName() {
        return "Blur Action Worker";
    }

    public BlurActionWorker(Window window) {
        blurPostProcessingFrameWorker = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker1 = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker2 = new FrameBufferWorker(window.getResolution());
        blurSceneFrameWorker3 = new FrameBufferWorker(window.getResolution());
        this.window = window;
    }

    @Override
    public int render(int sourceFrame) {
        blurPostProcessingFrameWorker.bindFrameBuffer();

        window.setDrawMode(DrawMode.RawGUI);

        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < postProcessingActions.size(); i++) {
            postProcessingActions.get(i).apply();
        }
        postProcessingActions.clear();
        blurPostProcessingFrameWorker.unbindCurrentFrameBuffer();

        return DisplayDrawer.blurPostProcess(window, blurPostProcessingFrameWorker.getTexture(), sourceFrame, blurSceneFrameWorker1, blurSceneFrameWorker2, blurSceneFrameWorker3);
    }
}
