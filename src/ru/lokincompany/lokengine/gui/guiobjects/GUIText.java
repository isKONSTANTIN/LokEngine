package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFramePart;
import ru.lokincompany.lokengine.render.text.TextColorShader;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.awt.*;

public class GUIText extends GUIObject {

    public boolean canResize;
    protected GUITextFramePart framePart;
    protected TextColorShader shader;

    public GUIText(Vector2i position, String fontName, String text, Color color, int fontStyle, int size, boolean canResize) {
        super(position, new Vector2i(0, 0));
        framePart = new GUITextFramePart(text, new ru.lokincompany.lokengine.render.text.Font(new Font(fontName, fontStyle, size)), color);
        this.canResize = canResize;
        framePart.position = getPosition();
    }

    public GUIText(Vector2i position, String fontName, String text, TextColorShader shader, int fontStyle, int size, boolean canResize) {
        super(position, new Vector2i(0, 0));
        framePart = new GUITextFramePart(text, new ru.lokincompany.lokengine.render.text.Font(new Font(fontName, fontStyle, size)), shader);

        super.setSize(framePart.getSize());

        this.canResize = canResize;
        framePart.position = getPosition();
    }

    public GUIText(Vector2i position, String text, TextColorShader shader, int fontStyle) {
        this(position, text, shader, fontStyle, 24);
    }

    public GUIText(Vector2i position, String text, TextColorShader shader, int fontStyle, int size) {
        this(position, "Arial", text, shader, fontStyle, size, false);
    }

    public GUIText(Vector2i position, String text, Color color, int fontStyle, int size) {
        this(position, "Arial", text, color, fontStyle, size, false);
    }

    public GUIText(Vector2i position, String text, Color color, int fontStyle) {
        this(position, text, color, fontStyle, 24);
    }

    public GUIText(Vector2i position, String text) {
        this(position, text, new Color(1, 1, 1, 1), 0);
    }

    public GUIText(Vector2i position) {
        this(position, "Text");
    }

    public GUIText(String fontName, String text, Color color, int fontStyle, int size, boolean canResize) {
        this(new Vector2i(), fontName, text, color, fontStyle, size, canResize);
    }

    public GUIText(String fontName, String text, TextColorShader shader, int fontStyle, int size, boolean canResize) {
        this(new Vector2i(), fontName, text, shader, fontStyle, size, canResize);
    }

    public GUIText(String text, TextColorShader shader, int fontStyle) {
        this(new Vector2i(), text, shader, fontStyle, 24);
    }

    public GUIText(String text, TextColorShader shader, int fontStyle, int size) {
        this(new Vector2i(), "Arial", text, shader, fontStyle, size, false);
    }

    public GUIText(String text, Color color, int fontStyle, int size) {
        this(new Vector2i(), "Arial", text, color, fontStyle, size, false);
    }

    public GUIText(String text, Color color, int fontStyle) {
        this(new Vector2i(), text, color, fontStyle, 24);
    }

    public GUIText(String text) {
        this(new Vector2i(), text, new Color(1, 1, 1, 1), 0);
    }

    public GUIText() {
        this(new Vector2i(), "Text");
    }

    public void setShader(TextColorShader shader) {
        this.shader = shader;
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
