package ru.lokinCompany.LokEngine.GUI.GUIObjects;

import ru.lokinCompany.LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.LokEngine.Render.Frame.FrameParts.GUI.GUIImageFramePart;
import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.Render.Texture;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Color;

public class GUIImage extends GUIObject {

    protected GUIImageFramePart framePart;

    public GUIImage(Vector2i position, Vector2i size) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size);
    }

    public GUIImage(Vector2i position, Vector2i size, String path) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size, path);
    }

    public Texture getTexture() {
        return framePart.texture;
    }

    public void setTexture(Texture texture) {
        framePart.texture = texture;
    }

    public void setColor(Color color){
        framePart.color = color;
    }

    public Color getColor(){
        return framePart.color;
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
    }

}
