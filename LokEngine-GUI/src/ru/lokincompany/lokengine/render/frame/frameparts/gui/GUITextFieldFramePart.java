package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

public class GUITextFieldFramePart extends GUITextFramePart {

    public int pointer = 0;
    public boolean active;
    public Vector2i size;
    public Color backgroundColor;
    public boolean centralizeText;
    public float pointerTime;

    public GUITextFieldFramePart(Vector2i GUIObjectSize, String text, FontPrefs fontPrefs) {
        super(text, new Font(fontPrefs), fontPrefs.getShader());
        this.size = GUIObjectSize;
        this.backgroundColor = Colors.engineBackgroundColor();
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        pointerTime += 0.1f;

        int fontHeight = font.getFontHeight();
        Vector2i textSize = font.getSize(text, maxSize);

        int xGap = (centralizeText ? (int) (size.x / 2f - textSize.x / 2f) : 0);
        int yGap = (int) (size.y / 2f - fontHeight / 2f) + 1;

        int fontXpos = position.x + xGap;
        int fontYpos = position.y + yGap;

        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        glColor4f(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        OpenGLFastTools.drawSquare(position, size);

        Vector2i endPosText = new Vector2i(textSize.x + fontXpos, textSize.y + fontYpos);
        Vector2i maxPos = new Vector2i(size.x - 1 - xGap, size.y - yGap);

        if (textSize.x >= size.x)
            font.inversionDrawText(text, new Vector2i(fontXpos, fontYpos), maxPos, color == null ? shader : charPos -> color);
        else
            font.drawText(text, new Vector2i(fontXpos, fontYpos), maxPos, color == null ? shader : charPos -> color);


        if (active) {
            int xPos = textSize.x >= size.x ? position.x + size.x - 1 : fontXpos + font.getSize(text.substring(0, Math.min(pointer, text.length())), maxSize).x;

            Color lineColor = color == null ? shader.getColor(new Vector2i(text.length(), 1)) : color;
            float s = (float) (Math.cos(pointerTime) + 1) / 2f;

            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4f(lineColor.red, lineColor.green, lineColor.blue, lineColor.alpha * s);

            GL11.glVertex2f(xPos + 1, endPosText.y - fontHeight);
            GL11.glVertex2f(xPos + 1, endPosText.y);

            GL11.glEnd();
        }
    }
}
