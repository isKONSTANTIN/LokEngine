package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI;

import org.lwjgl.opengl.GL11;
import ru.lokinCompany.lokEngine.Loaders.FontLoader;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Tools.Timer;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector4i;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class GUITextFieldFramePart extends GUITextFramePart {

    public int pointer = 0;
    public boolean printSelecter;
    public Timer timer;
    public boolean active;
    public Vector2i size;
    public Color backgroundColor;

    public GUITextFieldFramePart(Vector2i GUIObjectSize, String text, String fontName, Color color, int fontStyle, int size, boolean antiAlias) {
        super(text, FontLoader.createFont(new Font(fontName, fontStyle, size), antiAlias), color);
        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
        this.size = GUIObjectSize;
        this.backgroundColor = Colors.engineBackgroundColor();
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        glBegin(GL_QUADS);

        glColor4f(backgroundColor.red, backgroundColor.green, backgroundColor.blue, backgroundColor.alpha);
        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x + position.x, position.y, 0);
        glVertex3f(size.x + position.x, size.y + position.y, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();

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
            GL11.glVertex2f(xPos + 1,position.y + size.y);

            GL11.glEnd();
        }
    }
}
