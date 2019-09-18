package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUICanvasFramePart extends FramePart {
    PartsBuilder partsBuilder;
    Vector2i position;

    public GUICanvasFramePart(PartsBuilder partsBuilder, Vector2i position) {
        super(FramePartType.GUI);
        this.partsBuilder = partsBuilder;
        this.position = position;
    }

    @Override
    public void partRender(){
        int texture = partsBuilder.build(DrawMode.RawGUI);

        glBindTexture(GL_TEXTURE_2D, texture);
        glBegin(GL_POLYGON);
        glColor4d(1,1,1,1);

        glTexCoord2f(0,1);
        glVertex3f(position.x, position.y,0);
        glTexCoord2f(1,1);
        glVertex3f(partsBuilder.resolution.x + position.x,position.y,0);
        glTexCoord2f(1,0);
        glVertex3f(partsBuilder.resolution.x + position.x,partsBuilder.resolution.y + position.y,0);
        glTexCoord2f(0,0);
        glVertex3f(position.x,partsBuilder.resolution.y + position.y,0);

        glEnd();
        glBindTexture(GL_TEXTURE_2D,0);
    }
}
