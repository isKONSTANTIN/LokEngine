package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Tools.Timer;
import LokEngine.Tools.Utilities.Color;
import org.lwjgl.opengl.GL11;

public class GUITextFieldFramePart extends GUITextFramePart {

    public int pointer = 0;
    boolean printSelecter;
    Timer timer;
    public boolean active;

    public GUITextFieldFramePart(String text, Color color) {
        super(text, color);
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
            int xPos = 0;//font.getWidth(text.substring(0,pointer)) + position.x;

            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4f(color.red, color.green, color.blue, color.alpha);

            GL11.glVertex2f(xPos + 1,position.y);
            GL11.glVertex2f(xPos + 1,position.y + getHeight());

            GL11.glEnd();
        }
    }
}
