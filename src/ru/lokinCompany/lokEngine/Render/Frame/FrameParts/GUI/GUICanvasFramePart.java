package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI;

import ru.lokinCompany.lokEngine.Render.Enums.DrawMode;
import ru.lokinCompany.lokEngine.Render.Enums.FramePartType;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Render.Frame.FramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUICanvasFramePart extends FramePart {
    public PartsBuilder partsBuilder;
    public Vector2i position;
    public Vector2i size;
    public Vector2i viewOffset = new Vector2i(0,0);
    public Color color = new Color(1, 1, 1, 1);

    public GUICanvasFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.partsBuilder = new PartsBuilder(size);
        this.position = position;
        this.size = size;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        int texture = partsBuilder.build(DrawMode.RawGUI, builderProperties, viewOffset);

        glBindTexture(GL_TEXTURE_2D, texture);
        glBegin(GL_POLYGON);
        glColor4d(color.red, color.green, color.blue, color.alpha);

        glTexCoord2f(0, 1);
        glVertex3f(position.x, position.y, 0);

        glTexCoord2f(1, 1);
        glVertex3f(size.x + position.x, position.y, 0);

        glTexCoord2f(1, 0);
        glVertex3f(size.x + position.x, size.y + position.y, 0);

        glTexCoord2f(0, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);

    }
}
