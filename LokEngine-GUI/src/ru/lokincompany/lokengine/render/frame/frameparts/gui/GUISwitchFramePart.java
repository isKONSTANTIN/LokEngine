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

public class GUISwitchFramePart extends FramePart {
    public Color colorHead;
    public Color colorBackground;
    public Color colorFill;

    public Vector2i position;
    public Vector2i size;
    public Vector2i headPosition;
    public Vector2i headSize = new Vector2i();

    public float animationStatus;
    public boolean status;

    public GUISwitchFramePart(Vector2i position, Vector2i size, Color colorHead, Color colorBackground, Color colorFill) {
        super(FramePartType.GUI);
        this.position = position;
        headPosition = new Vector2i(position.x, position.y);

        this.size = size;
        headSize.x = (int)(size.x / 2.5f);
        headSize.y = size.y;

        this.colorHead = colorHead;
        this.colorBackground = colorBackground;
        this.colorFill = colorFill;
    }

    public GUISwitchFramePart(Vector2i position, Vector2i size) {
        this(position, size, Colors.engineBrightMainColor(), Colors.engineBackgroundColor(), Colors.engineMainColor());
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(colorBackground.red, colorBackground.green, colorBackground.blue, colorBackground.alpha);
        OpenGLFastTools.drawSquare(position, size);

        glColor4f(colorFill.red, colorFill.green, colorFill.blue, colorFill.alpha);
        OpenGLFastTools.drawSquare(position, new Vector2i((int)(size.x * animationStatus), size.y));

        glColor4f(colorHead.red, colorHead.green, colorHead.blue, colorHead.alpha);
        OpenGLFastTools.drawSquare(headPosition, headSize);
    }

}
