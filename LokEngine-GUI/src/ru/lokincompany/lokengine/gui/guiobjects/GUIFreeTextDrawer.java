package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFramePart;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.TextColorShader;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.ArrayList;

public class GUIFreeTextDrawer extends GUIObject {

    public Color color = new Color(1, 1, 1, 1);
    protected ArrayList<GUITextFramePart> frameParts = new ArrayList<>();
    protected Font font;

    public GUIFreeTextDrawer(Font font) {
        super(new Vector2i(0, 0), new Vector2i(0, 0));
        this.font = font;
    }

    public GUIFreeTextDrawer(FontPrefs fontPrefs) {
        super(new Vector2i(0, 0), new Vector2i(0, 0));
        font = new Font(fontPrefs);
    }

    public GUIFreeTextDrawer() {
        this(new FontPrefs());
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void draw(String text, Vector2i position, Vector2i maxSize, TextColorShader shader) {
        GUITextFramePart framePart = new GUITextFramePart(text, font, shader);
        framePart.position = position;
        framePart.maxSize = maxSize;
        frameParts.add(framePart);
    }

    public void draw(String text, Vector2i position, Vector2i maxSize, Color color) {
        draw(text, position, maxSize, charPos -> color);
    }

    public void draw(String text, Vector2i position, Vector2i maxSize) {
        draw(text, position, maxSize, color);
    }

    public void draw(String text, Vector2i position, TextColorShader shader) {
        draw(text, position, null, color);
    }

    public void draw(String text, Vector2i position, Color color) {
        draw(text, position, null, charPos -> color);
    }

    public void draw(String text, Vector2i position) {
        draw(text, position, null, color);
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
