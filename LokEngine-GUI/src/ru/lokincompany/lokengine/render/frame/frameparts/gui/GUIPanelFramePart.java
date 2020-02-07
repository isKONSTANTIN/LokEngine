package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUIPanelFramePart extends FramePart {

    public Vector2i position;
    public Vector2i size;
    public Color color;

    public GUIPanelFramePart(Vector2i position, Vector2i size) {
        this(position, size, new Color(0.1f, 0.1f, 0.1f, 0.6f));
    }

    public GUIPanelFramePart(Vector2i position, Vector2i size, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.color = color;
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        glColor4f(color.red, color.green, color.blue, color.alpha);
        OpenGLFastTools.drawSquare(position, size);
    }
}
