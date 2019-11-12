package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI;

import org.lwjgl.opengl.GL11;
import ru.lokinCompany.lokEngine.Loaders.FontLoader;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Tools.Timer;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;

import java.awt.*;

public class GUITextFieldFramePart extends GUITextFramePart {

    public int pointer = 0;
    public boolean printSelecter;
    public Timer timer;
    public boolean active;

    public GUITextFieldFramePart(String text, String fontName, Color color, int fontStyle, int size, boolean antiAlias) {
        super(text, FontLoader.createFont(new Font(fontName, fontStyle, size), antiAlias), color);
        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        super.partRender(builderProperties);
        if (timer.checkTime()) {
            printSelecter = !printSelecter;
            timer.resetTimer();
        }

        if (printSelecter && active) {
            int xPos = font.getWidth(text.substring(0, pointer)) + position.x;

            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4f(color.red, color.green, color.blue, color.alpha);

            GL11.glVertex2f(xPos + 1, position.y);
            GL11.glVertex2f(xPos + 1, position.y + getHeight());

            GL11.glEnd();
        }
    }
}
