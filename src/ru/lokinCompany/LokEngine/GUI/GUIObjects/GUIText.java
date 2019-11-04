package ru.lokinCompany.LokEngine.GUI.GUIObjects;

import ru.lokinCompany.LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.LokEngine.Loaders.FontLoader;
import ru.lokinCompany.LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.Tools.Text.TextColorShader;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;

import java.awt.*;

public class GUIText extends GUIObject {

    protected GUITextFramePart framePart;
    protected int maxTextLength;
    protected TextColorShader shader;
    public boolean canResize;

    public GUIText(Vector2i position, String fontName, String text, Color color, int fontStyle, int size, boolean antiAlias, boolean canResize) {
        super(position, new Vector2i(0, 0));
        framePart = new GUITextFramePart(text, FontLoader.createFont(new Font(fontName, fontStyle, size), antiAlias), color);

        super.setSize(new Vector2i(framePart.getWidth(), framePart.getHeight()));

        this.canResize = canResize;
        framePart.position = getPosition();
    }

    public GUIText(Vector2i position, String fontName, String text, TextColorShader shader, int fontStyle, int size, boolean antiAlias, boolean canResize) {
        super(position, new Vector2i(0, 0));
        framePart = new GUITextFramePart(text, FontLoader.createFont(new Font(fontName, fontStyle, size), antiAlias), shader);

        super.setSize(new Vector2i(framePart.getWidth(), framePart.getHeight()));

        this.canResize = canResize;
        framePart.position = getPosition();
    }

    public GUIText(Vector2i position, String text, TextColorShader shader, int fontStyle) {
        this(position, text, shader, fontStyle, 24);
    }

    public GUIText(Vector2i position, String text, TextColorShader shader, int fontStyle, int size) {
        this(position, "Times New Roman", text, shader, fontStyle, size, true, false);
    }

    public GUIText(Vector2i position, String text, Color color, int fontStyle, int size) {
        this(position, "Times New Roman", text, color, fontStyle, size, true, false);
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

    public void setShader(TextColorShader shader){
        this.shader = shader;
    }

    public String getText() {
        return framePart.text;
    }

    public void updateText(String text) {
        framePart.text = text;
        if (canResize) {
            super.setSize(new Vector2i(framePart.getWidth(), framePart.getHeight()));
        } else {
            updateMaxSize();
            framePart.text = text.length() > maxTextLength ? framePart.text.substring(0, maxTextLength) : framePart.text;
        }
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        updateMaxSize();
        framePart.text = getText().length() > maxTextLength ? framePart.text.substring(0, maxTextLength) : framePart.text;
    }

    private void updateMaxSize() {
        int textLength = framePart.text.length();
        if (textLength > 0 && framePart.getWidth() > 0)
            this.maxTextLength = getSize().x / (framePart.getWidth() / framePart.text.length());
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
    }

}
