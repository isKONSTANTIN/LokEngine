package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

public class GUIPanelFramePart extends FramePart {

    public Vector2i position;
    public Vector2i size;
    public Color color;

    public GUIPanelFramePart(Vector2i position, Vector2i size) {
        this(position, size, Colors.engineBackgroundColor().setAlpha(0.5f));
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
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(color.red, color.green, color.blue, color.alpha);
        OpenGLFastTools.drawSquare(position, size);
    }
}
