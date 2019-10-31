package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Loaders.FontLoader;
import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Text.Font;
import LokEngine.Tools.Utilities.Color.Color;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngine.Tools.Text.TextColorShader;

import java.util.ArrayList;

public class GUIFreeTextDrawer extends GUIObject {

    private ArrayList<GUITextFramePart> frameParts = new ArrayList<>();
    private Font font;
    public Color color = new Color(1, 1, 1, 1);

    public GUIFreeTextDrawer(Font font) {
        super(new Vector2i(0, 0), new Vector2i(0, 0));
        this.font = font;
    }

    public GUIFreeTextDrawer(String fontName, int fontStyle, int size, boolean antiAlias) {
        super(new Vector2i(0, 0), new Vector2i(0, 0));
        font = FontLoader.createFont(new java.awt.Font(fontName, fontStyle, size), antiAlias);
    }

    public void draw(String text, Vector2i position, TextColorShader shader) {
        GUITextFramePart framePart = new GUITextFramePart(text, font, shader);
        framePart.position = position;
        frameParts.add(framePart);
    }

    public void draw(String text, Vector2i position, Color color) {
        GUITextFramePart framePart = new GUITextFramePart(text, font, color);
        framePart.position = position;
        frameParts.add(framePart);
    }

    public void draw(String text, Vector2i position) {
        draw(text, position, color);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        for (GUITextFramePart framePart : frameParts) {
            partsBuilder.addPart(framePart);
        }
        frameParts.clear();
    }

}
