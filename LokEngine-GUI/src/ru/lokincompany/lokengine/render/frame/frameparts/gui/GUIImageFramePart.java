package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUIImageFramePart extends FramePart {

    public Texture texture;
    public Vector2i position;
    public Vector2i size;
    public Color color = Colors.white();

    public GUIImageFramePart(Vector2i position, Vector2i size, String path) {
        super(FramePartType.GUI);
        this.texture = new Texture(path);
        this.position = position;
        if (size.x <= 0 || size.y <= 0) {
            size = new Vector2i(texture.getSizeX(), texture.getSizeY());
        }
        this.size = size;
    }

    public GUIImageFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        if (texture != null && texture.getBuffer() != -1) {
            glBindTexture(GL_TEXTURE_2D, texture.getBuffer());
            glBegin(GL_POLYGON);
            glColor4d(color.red, color.green, color.blue, color.alpha);

            glTexCoord2f(0, 0);
            glVertex3f(position.x, position.y, 0);
            glTexCoord2f(1, 0);
            glVertex3f(size.x + position.x, position.y, 0);
            glTexCoord2f(1, 1);
            glVertex3f(size.x + position.x, size.y + position.y, 0);
            glTexCoord2f(0, 1);
            glVertex3f(position.x, size.y + position.y, 0);

            glEnd();
            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }
}
