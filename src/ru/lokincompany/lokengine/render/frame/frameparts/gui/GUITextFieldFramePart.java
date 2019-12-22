package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.loaders.FontLoader;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.tools.Timer;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

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
    public void partRender(BuilderProperties builderProperties) {
        int fontHeight = font.getFontHeight();
        int fontYpos = position.y + (int)(size.y / 2f - fontHeight / 2f) + 1;
        int fontXpos = position.x + (centralizeText ? (int) (size.x / 2f - font.getWidth(text) / 2f) : 0);

        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glBegin(GL_QUADS);

        glColor4f(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x + position.x, position.y, 0);
        glVertex3f(size.x + position.x, size.y + position.y, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();

        if (color != null) {
            font.drawText(text, fontXpos, fontYpos, color);
        } else {
            font.drawText(text, fontXpos, fontYpos, shader);
        }

        if (timer.checkTime()) {
            printSelecter = !printSelecter;
            timer.resetTimer();
        }

        if (printSelecter && active) {
            int xPos = font.getWidth(text.substring(0, pointer)) + fontXpos;

            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4f(color.red, color.green, color.blue, color.alpha);

            GL11.glVertex2f(xPos + 1,fontYpos);
            GL11.glVertex2f(xPos + 1, fontYpos + fontHeight);

            GL11.glEnd();
        }
    }
}
