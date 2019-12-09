package ru.lokincompany.lokengine.gui.guiobjects.guislider;

import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

public class GUISliderHead extends GUIObject {
    public Color color;
    public GUISliderColorShader colorShader;

    protected Texture texture;

    public GUISliderHead(Vector2i position, Vector2i size, Color color) {
        super(position, size);
        this.color = color;
        touchable = true;
    }

    public GUISliderHead(Vector2i position, Vector2i size, GUISliderColorShader colorShader) {
        super(position, size);
        this.colorShader = colorShader;
        touchable = true;
    }

    public GUISliderHead(Vector2i position, Vector2i size, Texture texture) {
        this(position, size, Colors.white());
        this.texture = texture;
    }

    public GUISliderHead(Vector2i position, Texture texture) {
        this(position, new Vector2i(), Colors.white());
        setTexture(texture);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        color = Colors.white();
        setSize(new Vector2i(texture.sizeX, texture.sizeY));
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean getActive() {
        return active;
    }

    protected boolean getRetention() {
        return retention;
    }

}
