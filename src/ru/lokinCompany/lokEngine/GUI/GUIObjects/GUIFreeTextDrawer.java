package ru.lokinCompany.lokEngine.GUI.GUIObjects;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.Loaders.FontLoader;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Text.Font;
import ru.lokinCompany.lokEngine.Tools.Text.TextColorShader;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

public class GUIFreeTextDrawer extends GUIObject {

    protected ArrayList<GUITextFramePart> frameParts = new ArrayList<>();
    protected Font font;
    public Color color = new Color(1, 1, 1, 1);

    public GUIFreeTextDrawer(Font font) {
        super(new Vector2i(0, 0), new Vector2i(0, 0));
        this.font = font;
    }

    public GUIFreeTextDrawer(String fontName, int fontStyle, int size, boolean antiAlias) {
        super(new Vector2i(0, 0), new Vector2i(0, 0));
        font = FontLoader.createFont(new java.awt.Font(fontName, fontStyle, size), antiAlias);
    }

    public GUIFreeTextDrawer(int fontStyle, int size, boolean antiAlias) {
        this("Arial", fontStyle, size, antiAlias);
    }

    public GUIFreeTextDrawer() { this(0, 12, true); }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
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
