package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUIPanelFramePart extends FramePart {

    public Vector2i position;
    public Vector2i size;
    public Color color;

    public GUIPanelFramePart(Vector2i position, Vector2i size) {
        this(position,size,new Color(0.1f,0.1f,0.1f,0.6f));
    }

    public GUIPanelFramePart(Vector2i position, Vector2i size, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.color = color;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        glBegin(GL_QUADS);

        glColor4f(color.red, color.green, color.blue, color.alpha);
        glVertex3f(position.x,position.y,0);
        glVertex3f(size.x + position.x,position.y,0);
        glVertex3f(size.x + position.x,size.y + position.y,0);
        glVertex3f(position.x,size.y + position.y,0);

        glEnd();
    }
}
