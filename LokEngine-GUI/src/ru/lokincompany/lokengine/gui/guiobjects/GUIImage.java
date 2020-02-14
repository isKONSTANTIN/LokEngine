package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUIImageFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIImage extends GUIObject {
    protected GUIImageFramePart framePart;

    public GUIImage() {
        framePart = new GUIImageFramePart(position, size);
    }

    public Texture getTexture() {
        return framePart.texture;
    }

    public GUIImage setTexture(Texture texture, boolean resetSize) {
        framePart.texture = texture;
        if (resetSize)
            setSize(new Vector2i(texture.getSizeX(), texture.getSizeY()));
        return this;
    }

    public GUIImage setTexture(Texture texture) {
        return setTexture(texture, true);
    }

    public Color getColor() {
        return framePart.color;
    }

    public GUIImage setColor(Color color) {
        framePart.color = color;
        return this;
    }

    @Override
    public GUIImage setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
        return this;
    }

    @Override
    public GUIImage setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
    }

}
