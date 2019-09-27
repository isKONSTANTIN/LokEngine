package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Tools.Timer;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import static org.lwjgl.opengl.GL11.glColor4f;

public class GUITextFieldFramePart extends GUITextFramePart {

    public int pointer = 0;
    boolean printSelecter;
    Timer timer;
    public boolean active;

    public GUITextFieldFramePart(String text, org.newdawn.slick.Color color, TrueTypeFont font, int fontBuffer) {
        super(text, color, font, fontBuffer);
        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
    }

    public GUITextFieldFramePart(String text, String fontName, org.newdawn.slick.Color color, int fontStyle, int size, boolean antiAlias) {
        super(text, fontName, color, fontStyle, size, antiAlias);
        timer = new Timer();
        timer.setDurationInSeconds(0.5f);
    }

    public void partRender() {
        super.partRender();
        if (timer.checkTime()){
            printSelecter = !printSelecter;
            timer.resetTimer();
        }

        if (printSelecter && active){
            int xPos = font.getWidth(text.substring(0,pointer)) + position.x;

            GL11.glBegin(GL11.GL_LINES);
            glColor4f(color.r, color.g, color.b, color.a);

            GL11.glVertex2f(xPos + 1,position.y);
            GL11.glVertex2f(xPos + 1,position.y + font.getHeight());

            GL11.glEnd();
        }
    }
}
