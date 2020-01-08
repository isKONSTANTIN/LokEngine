package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.loaders.FontLoader;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.Timer;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

public class GUITextFieldFramePart extends GUITextFramePart {

    public int pointer = 0;
    public boolean printSelecter;
    public Timer timer;
    public boolean active;
    public Vector2i size;
    public Color backgroundColor;
    public boolean centralizeText;

    public GUITextFieldFramePart(Vector2i GUIObjectSize, String text, String fontName, Color color, int fontStyle, int size, boolean antiAlias) {
        super(text, FontLoader.createFont(new Font(fontName, fontStyle, size), antiAlias), color);
        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
        this.size = GUIObjectSize;
        this.backgroundColor = Colors.engineBackgroundColor();
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        int fontHeight = font.getFontHeight();
        Vector2i textSize = font.getSize(text, maxSize);

        int xGap = (centralizeText ? (int) (size.x / 2f - textSize.x / 2f) : 0);
        int yGap = (int) (size.y / 2f - fontHeight / 2f) + 1;

        int fontXpos = position.x + xGap;
        int fontYpos = position.y + yGap;

        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glBegin(GL_QUADS);

        glColor4f(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x + position.x, position.y, 0);
        glVertex3f(size.x + position.x, size.y + position.y, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();

        Vector2i endPosText = new Vector2i(textSize.x + fontXpos, textSize.y + fontYpos);
        Vector2i maxPos = new Vector2i(size.x - 1 - xGap, size.y - yGap);

        if (color != null) {
            font.drawText(text, new Vector2i(fontXpos, fontYpos), maxPos, charPos -> color);
        } else {
            font.drawText(text, new Vector2i(fontXpos, fontYpos), maxPos, shader);
        }

        if (timer.checkTime()) {
            printSelecter = !printSelecter;
            timer.resetTimer();
        }

        if (printSelecter && active) {
            int xPos = endPosText.x;

            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4f(color.red, color.green, color.blue, color.alpha);

            GL11.glVertex2f(xPos + 1, endPosText.y);
            GL11.glVertex2f(xPos + 1, endPosText.y + fontHeight);

            GL11.glEnd();
        }
    }
}
