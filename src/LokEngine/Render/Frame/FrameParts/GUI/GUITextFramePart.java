package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;

public class GUITextFramePart extends FramePart {
    private TrueTypeFont font;
    private int buffer;

    public String text;
    public Vector2i position;
    public org.newdawn.slick.Color color;

    public GUITextFramePart(String text, String fontName , org.newdawn.slick.Color color, int fontStyle, int size, boolean antiAlias) {
        super(FramePartType.GUI);
        font = new TrueTypeFont(new Font(fontName,fontStyle,size), antiAlias, ("йцукенгшщзхъфывапролджэячсмитьбю".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбю").toCharArray());
        buffer = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
        this.text = text;
        this.color = color;
    }

    public int getHeight(){
        return font.getHeight();
    }
    public int getWidth(){
        return font.getWidth(text);
    }

    @Override
    public void partRender() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buffer);
        font.drawString(position.x, position.y + 10, text, color);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
