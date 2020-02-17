package ru.lokincompany.lokengine.gui.guiobjects.guislider;

import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUISliderHead extends GUIObject {
    protected Color color = Colors.white();
    protected GUISliderColorShader colorShader;
    protected Texture texture;

    public GUISliderHead() {
        touchable = true;
    }

    public Texture getTexture() {
        return texture;
    }

    public GUISliderHead setTexture(Texture texture) {
        this.texture = texture;
        color = Colors.white();
        setSize(new Vector2i(texture.getSizeX(), texture.getSizeY()));
        return this;
    }

    public Color getColor() {
        return color;
    }

    public GUISliderHead setColor(Color color) {
        this.color = color;
        return this;
    }

    public GUISliderColorShader getColorShader() {
        return colorShader;
    }

    public GUISliderHead setColorShader(GUISliderColorShader colorShader) {
        this.colorShader = colorShader;
        return this;
    }

    public boolean getActive() {
        return active;
    }

    protected boolean getRetention() {
        return retention;
    }

}
