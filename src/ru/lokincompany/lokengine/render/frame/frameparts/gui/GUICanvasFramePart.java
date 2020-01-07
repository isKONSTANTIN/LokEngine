package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUICanvasFramePart extends FramePart {
    public PartsBuilder partsBuilder;
    public Vector2i position;
    public Vector2i size;
    public Vector2i viewOffset = new Vector2i(0, 0);
    public Color color = new Color(1, 1, 1, 1);

    public GUICanvasFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.partsBuilder = new PartsBuilder(size);
        this.position = position;
        this.size = size;
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        int texture = partsBuilder.build(DrawMode.RawGUI, renderProperties, viewOffset);

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
