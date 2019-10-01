package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUICanvasFramePart extends FramePart {
    PartsBuilder partsBuilder;
    public Vector2i position;
    public Vector2i size;
    public Color color = new Color(1, 1, 1, 1);

    public GUICanvasFramePart(PartsBuilder partsBuilder, Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.partsBuilder = partsBuilder;
        this.position = position;
        this.size = size;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        int texture = partsBuilder.build(DrawMode.RawGUI, builderProperties);

        glBindTexture(GL_TEXTURE_2D, texture);
        glBegin(GL_POLYGON);
        glColor4d(color.red, color.green, color.blue, color.alpha);

        glTexCoord2f(0, 1);
        glVertex3f(position.x, position.y, 0);

        glTexCoord2f(size.x / (float) partsBuilder.resolution.x, 1);
        glVertex3f(size.x + position.x, position.y, 0);

        glTexCoord2f(size.x / (float) partsBuilder.resolution.x, 1 - size.y / (float) partsBuilder.resolution.y);
        glVertex3f(size.x + position.x, size.y + position.y, 0);

        glTexCoord2f(0, 1 - size.y / (float) partsBuilder.resolution.y);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);

    }
}
