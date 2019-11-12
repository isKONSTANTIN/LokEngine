package ru.lokinCompany.lokEngine.GUI.GUIObjects;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI.GUICheckBoxFramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Render.Texture;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

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

    public void setTexture(Texture texture) {
        framePart.imageFramePart.texture = texture;
    }

    public Texture getTexture() {
        return framePart.imageFramePart.texture;
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
