package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUISwitchFramePart extends FramePart {

    public Color colorHead;
    public Color colorBackground;
    public Color colorFill;

    public Vector2i position;
    public Vector2i size;
    public Vector2i headPosition = new Vector2i();
    public Vector2i headSize = new Vector2i();

    public boolean status;

    public GUISwitchFramePart(Vector2i position, Vector2i size, Color colorHead, Color colorBackground, Color colorFill) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
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
        if (status)
            glColor4f(colorFill.red, colorFill.green, colorFill.blue, colorFill.alpha);
        else
            glColor4f(colorBackground.red, colorBackground.green, colorBackground.blue, colorBackground.alpha);

        OpenGLFastTools.drawSquare(position, size);

        glColor4f(colorHead.red, colorHead.green, colorHead.blue, colorHead.alpha);
        OpenGLFastTools.drawSquare(headPosition, headSize);
    }

}
