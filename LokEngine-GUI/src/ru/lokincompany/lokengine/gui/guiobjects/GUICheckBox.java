package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUICheckBoxFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUICheckBox extends GUIObject {

    protected GUICheckBoxFramePart framePart;
    protected GUIText text;

    public GUICheckBox() {
        super(new Vector2i(), new Vector2i(10,10));
        this.framePart = new GUICheckBoxFramePart(position, size);
        this.touchable = true;
    }

    public Color getColor(){
        return framePart.color;
    }

    public GUIText getText() {
        return text;
    }

    public Texture getTexture() {
        return framePart.imageFramePart.texture;
    }

    public GUICheckBox setTexture(Texture texture) {
        framePart.imageFramePart.texture = texture;
        return this;
    }

    public GUICheckBox setText(GUIText text){
        this.text = text;
        text.setPosition(object -> new Vector2i(position.x + size.x + 2, position.y + 1));
        this.size.x = text.framePart.font.getFontHeight();
        this.size.y = this.size.x;
        return this;
    }

    @Override
    public GUICheckBox setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        return this;
    }

    @Override
    public GUICheckBox setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
        return this;
    }

    @Override
    protected void unpressed() {
        framePart.status = !framePart.status;
    }

    public boolean getStatus() {
        return framePart.status;
    }

    public GUICheckBox setStatus(boolean status) {
        framePart.status = status;
        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (text != null)
            text.update(partsBuilder, parentProperties);

        partsBuilder.addPart(framePart);
    }

}
