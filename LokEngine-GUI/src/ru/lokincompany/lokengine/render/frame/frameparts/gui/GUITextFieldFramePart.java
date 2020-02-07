package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
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

    public GUITextFieldFramePart(Vector2i GUIObjectSize, String text, FontPrefs fontPrefs) {
        super(text, new Font(fontPrefs), fontPrefs.getShader());
        this.size = GUIObjectSize;
        this.backgroundColor = Colors.engineBackgroundColor();

        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
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

        glColor4f(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        OpenGLFastTools.drawSquare(position, size);

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
            if (color == null) {
                Color shaderColor = shader.getColor(new Vector2i(text.length(), 1));
                GL11.glColor4f(shaderColor.red, shaderColor.green, shaderColor.blue, shaderColor.alpha);
            }else
                GL11.glColor4f(color.red, color.green, color.blue, color.alpha);
            GL11.glVertex2f(xPos + 1, endPosText.y - fontHeight);
            GL11.glVertex2f(xPos + 1, endPosText.y);

            GL11.glEnd();
        }
    }
}
