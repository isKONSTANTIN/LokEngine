package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;

public class GUIFreeTextDrawer extends GUIObject {

    private ArrayList<GUITextFramePart> frameParts = new ArrayList<>();
    private TrueTypeFont font;
    private int buffer;

    public GUIFreeTextDrawer(String fontName, int fontStyle, int size, boolean antiAlias) {
        super(new Vector2i(0,0), new Vector2i(0,0));
        font = new TrueTypeFont(new Font(fontName,fontStyle,size), antiAlias, ("йцукенгшщзхъфывапролджэячсмитьбю".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбю").toCharArray());
        buffer = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
    }

    public void draw(String text, Vector2i position, Color color){
        GUITextFramePart framePart = new GUITextFramePart(text, new org.newdawn.slick.Color(color.red, color.green, color.blue, color.alpha), font, buffer);
        framePart.position = position;
        frameParts.add(framePart);
    }

    public void draw(String text, Vector2i position){
        GUITextFramePart framePart = new GUITextFramePart(text, org.newdawn.slick.Color.white, font, buffer);
        framePart.position = position;
        frameParts.add(framePart);
    }

    @Override
    public void update(PartsBuilder partsBuilder){
        for (GUITextFramePart framePart : frameParts){
            partsBuilder.addPart(framePart);
        }
        frameParts.clear();
    }

}
