package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUICheckBoxFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUICheckBox extends GUIObject {

    protected GUICheckBoxFramePart framePart;

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

    public GUICheckBox(Color color) {
        this(new Vector2i(), new Vector2i(), color);
    }

    public GUICheckBox() {
        this(new Vector2i(), new Vector2i());
    }

    public Texture getTexture() {
        return framePart.imageFramePart.texture;
    }

    public void setTexture(Texture texture) {
        framePart.imageFramePart.texture = texture;
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
        partsBuilder.addPart(framePart);
    }

}
