package LokEngine.Render.Frame;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;

import java.util.Vector;

public class PartsBuilder {

    FrameBufferWorker frameBufferWorker;
    public Color clearColor = new Color(0,0,0,0);
    Vector<FramePart> frameParts = new Vector<>();
    public Vector2i resolution;

    public PartsBuilder(Vector2i resolution){
        frameBufferWorker = new FrameBufferWorker(resolution);
        this.resolution = resolution;
    }

    public void addPart(FramePart fp) {
        frameParts.add(fp);
    }

    public void setResolution(Vector2i resolution){
        frameBufferWorker.cleanUp();
        frameBufferWorker = new FrameBufferWorker(resolution);
    }

    public int build(Vector<FramePart> frameParts, DrawMode drawMode){
        frameBufferWorker.bindFrameBuffer();
        RuntimeFields.getFrameBuilder().window.setDrawMode(drawMode);

        GL11.glClearColor(clearColor.red, clearColor.green, clearColor.blue, clearColor.alpha);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (FramePart framePart : frameParts){
            framePart.partRender();
        }

        frameBufferWorker.unbindCurrentFrameBuffer();

        return frameBufferWorker.getTexture();
    }

    public int build(DrawMode drawMode){
        int result = build(frameParts, drawMode);
        frameParts.clear();
        return result;
    }

}
