package LokEngine.Render.Frame.FrameParts.PostProcessingActions;

import LokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class BlurAction extends PostProcessingAction {
    public float intensity = 1f;
    int samples = 10;
    float bokeh = 0.3f;

    public BlurAction(Vector2i position, Vector2i size) {
        super(position, size);
    }

    public BlurAction(Vector2i position, Vector2i size, float intensity) {
        super(position, size);
        this.intensity = intensity;
    }

    @Override
    public void apply(){
        glBegin(GL_QUADS);

        glColor4f(intensity,samples / 1000f, bokeh,1);
        glVertex3f(0 + position.x,0 + position.y,0);
        glVertex3f(size.x + position.x,0 + position.y,0);
        glVertex3f(size.x + position.x,size.y + position.y,0);
        glVertex3f(0 + position.x,size.y + position.y,0);

        glEnd();
    }
}
