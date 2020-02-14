package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.gui.guiobjects.guislider.GUISliderHead;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUISliderFramePart extends FramePart {

    public Vector2i position;
    public Vector2i size;
    public Color colorBackground;
    public Color colorFill;
    public GUISliderHead head;
    public float filled;

    public GUISliderFramePart(Vector2i position, Vector2i size, Color colorBackground, Color colorFill, GUISliderHead head) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.colorBackground = colorBackground;
        this.colorFill = colorFill;
        this.head = head;
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        Color headColor = head.getColor();

        glColor4f(colorBackground.red, colorBackground.green, colorBackground.blue, colorBackground.alpha);
        OpenGLFastTools.drawSquare(position, size);

        glColor4f(colorFill.red, colorFill.green, colorFill.blue, colorFill.alpha);
        OpenGLFastTools.drawSquare(position, new Vector2i((int) (size.x * filled), size.y));

        if (head.getTexture() != null)
            glBindTexture(GL_TEXTURE_2D, head.getTexture().getBuffer());

        glColor4f(headColor.red, headColor.green, headColor.blue, headColor.alpha);

        OpenGLFastTools.drawSquare(head.getPosition(), head.getSize());

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
