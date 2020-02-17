package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFramePart;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.TextColorShader;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIText extends GUIObject {
    protected GUITextFramePart framePart;

    public GUIText(FontPrefs fontPrefs) {
        framePart = new GUITextFramePart("", new Font(fontPrefs), fontPrefs.getShader());
        framePart.position = position;
    }

    public GUIText() {
        this(FontPrefs.defaultFontPrefs);
    }

    public String getText() {
        return framePart.text;
    }

    public GUIText setText(String text) {
        framePart.text = text;
        super.setSize(framePart.getSize());
        return this;
    }

    public GUIText setTextShader(TextColorShader textShader) {
        framePart.shader = textShader;
        return this;
    }

    public Vector2i getMaxSize() {
        return framePart.maxSize;
    }

    public GUIText setMaxSize(Vector2i maxSize) {
        framePart.maxSize = maxSize;
        return this;
    }

    @Override
    public GUIText setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
        return this;
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
