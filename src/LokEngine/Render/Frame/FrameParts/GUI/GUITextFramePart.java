package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import sun.font.TrueTypeFont;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;

public class GUITextFramePart extends FramePart {
    //protected TrueTypeFont font;
    protected int buffer;

    public String text;
    public Vector2i position;
    public Color color;

    public GUITextFramePart(String text, Color color, TrueTypeFont font, int fontBuffer) {
        super(FramePartType.GUI);
        //this.font = font;
        this.buffer = fontBuffer;
        this.text = text;
        this.color = color;
    }

    public GUITextFramePart(String text, String fontName, Color color, int fontStyle, int size, boolean antiAlias) {
        super(FramePartType.GUI);
        //font = new TrueTypeFont();

        buffer = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
        this.text = text;
        this.color = color;
    }

    public int getHeight(){
        return 0;//font.getHeight();
    }
    public int getWidth(){
        return 0;//font.getWidth(text);
    }

    @Override
    public void partRender() {
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, buffer);
        //font.drawString(position.x, position.y, text, color);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
