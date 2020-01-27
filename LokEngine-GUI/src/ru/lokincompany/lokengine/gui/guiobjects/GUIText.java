package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFramePart;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.TextColorShader;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIText extends GUIObject {

    public boolean canResize;
    protected GUITextFramePart framePart;

    public GUIText(Vector2i position, String text, FontPrefs fontPrefs, boolean canResize) {
        super(position, new Vector2i());
        this.canResize = canResize;

        framePart = new GUITextFramePart(text, new Font(fontPrefs), fontPrefs.getShader());
        framePart.position = getPosition();
    }

    public GUIText(Vector2i position, String text, FontPrefs fontPrefs) {
        this(position, text, fontPrefs, false);
    }

    public GUIText(Vector2i position, String text) {
        this(position, text, new FontPrefs());
    }

    public GUIText(Vector2i position) {
        this(position, "Text");
    }

    public GUIText(String text, FontPrefs fontPrefs, boolean canResize) {
        this(new Vector2i(), text, fontPrefs, canResize);
    }

    public GUIText(String text, FontPrefs fontPrefs) {
        this(text, fontPrefs, false);
    }

    public GUIText(String text) {
        this(text, new FontPrefs());
    }

    public GUIText() {
        this("");
    }

    public String getText() {
        return framePart.text;
    }

    public void updateText(String text) {
        framePart.text = text;
        super.setSize(framePart.getSize());
    }

    public void setMaxSize(Vector2i maxSize) {
        framePart.maxSize = maxSize;
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
    }

    @Override
    public Vector2i getSize() {
        return framePart.getSize();
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
    }

}
