package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUICheckBoxFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUICheckBox extends GUIObject {

    protected GUICheckBoxFramePart framePart;
    public GUIText text;

    public GUICheckBox(Vector2i position, Vector2i size, Texture texture, Color color) {
        super(position, size);
        this.framePart = new GUICheckBoxFramePart(position, size, texture, color);
        this.touchable = true;
    }

    public GUICheckBox(Vector2i position, Vector2i size, String path, Color color) {
        super(position, size);
        this.framePart = new GUICheckBoxFramePart(position, size, path, color);
        this.touchable = true;
    }

    public GUICheckBox(Vector2i position, Vector2i size, Color color) {
        super(position, size);
        this.framePart = new GUICheckBoxFramePart(position, size, color);
        this.touchable = true;
    }

    public GUICheckBox(Vector2i position, Vector2i size) {
        super(position, size);
        this.framePart = new GUICheckBoxFramePart(position, size);
        this.touchable = true;
    }

    public GUICheckBox(Texture texture, Color color) {
        this(new Vector2i(), new Vector2i(), texture, color);
    }

    public GUICheckBox(String path, Color color) {
        this(new Vector2i(), new Vector2i(), path, color);
    }

    public GUICheckBox(Color color, GUIText text) {
        this(new Vector2i(), new Vector2i(), color);
        setText(text);
    }

    public GUICheckBox(Color color) {
        this(new Vector2i(), new Vector2i(), color);
    }

    public GUICheckBox(GUIText text) {
        this(new Vector2i(), new Vector2i(10, 10));
        setText(text);
    }

    public GUICheckBox() {
        this(new Vector2i(), new Vector2i(10, 10));
    }

    public Texture getTexture() {
        return framePart.imageFramePart.texture;
    }

    public void setTexture(Texture texture) {
        framePart.imageFramePart.texture = texture;
    }

    public GUICheckBox setText(GUIText text){
        this.text = text;
        text.setPosition(object -> new Vector2i(position.x + size.x + 2, position.y + 1));
        this.size.x = text.framePart.font.getFontHeight();
        this.size.y = this.size.x;
        return this;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
    }

    @Override
    protected void unpressed() {
        framePart.status = !framePart.status;
    }

    public boolean getStatus() {
        return framePart.status;
    }

    public void setStatus(boolean status) {
        framePart.status = status;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (text != null)
            text.update(partsBuilder, parentProperties);

        partsBuilder.addPart(framePart);
    }

}
