package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIButtonScript;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIButton extends GUIObject {

    public Color pressedColor;
    public Color calmStateColor;
    public GUIText text;
    public GUIPanel panel;

    protected Color activeColor;
    protected GUIButtonScript pressScript;
    protected GUIButtonScript unpressScript;

    public GUIButton(Color pressed, Color calmState, GUIText text, GUIPanel panel, boolean centralizeText) {
        this.text = text;
        if (size.x <= 0 || size.y <= 0) {
            size = this.text.getSize();
        }
        this.pressedColor = pressed;
        this.calmStateColor = calmState;
        this.panel = panel;
        this.activeColor = new Color(calmStateColor.red, calmStateColor.green, calmStateColor.blue, calmStateColor.alpha);
        this.touchable = true;

        text.setPosition(object -> new Vector2i(getPosition().x + (centralizeText ? (int) (getSize().x / 2f - text.getSize().x / 2f) : 0), getPosition().y + (int) (getSize().y / 2f - text.getSize().y / 2f)));
        panel.setSize(object -> getSize());
        panel.setPosition(object -> getPosition());

        setPosition(position);
        setSize(size);
    }

    public GUIButton(Color calmState, GUIText text, boolean centralizeText) {
        this.text = text;
        text.setPosition(position);
        if (size.x <= 0 || size.y <= 0) {
            size = this.text.getSize();
        }
        float colorChange = (calmState.red + calmState.green + calmState.blue) / 3 >= 0.5f ? -0.1f : 0.1f;
        this.pressedColor = new Color(calmState.red + colorChange, calmState.green + colorChange, calmState.blue + colorChange, calmState.alpha);
        this.calmStateColor = calmState;
        this.activeColor = new Color(calmStateColor.red, calmStateColor.green, calmStateColor.blue, calmStateColor.alpha);
        this.panel = new GUIPanel(position, size, activeColor);
        this.touchable = true;

        text.setPosition(object -> new Vector2i(getPosition().x + (centralizeText ? (int) (getSize().x / 2f - text.getSize().x / 2f) : 0), getPosition().y + (int) (getSize().y / 2f - text.getSize().y / 2f)));
        panel.setSize(object -> getSize());
        panel.setPosition(object -> getPosition());

        setPosition(position);
        setSize(size);
    }

    public GUIButton(Color pressed, Color calmState, GUIText text, GUIPanel panel) {
        this(pressed, calmState, text, panel, true);
    }

    public GUIButton(Color calmState, GUIText text) {
        this(calmState, text, true);
    }

    public GUIButton(Color calmState, String text) {
        this(calmState, new GUIText(text));
    }

    public GUIButton(Vector2i position, Vector2i size, Color pressed, Color calmState, GUIText text, GUIPanel panel, boolean centralizeText) {
        this(pressed, calmState, text, panel, centralizeText);

        setPosition(position);
        setSize(size);
    }

    public GUIButton(Vector2i position, Vector2i size, Color calmState, GUIText text, boolean centralizeText) {
        this(calmState, text, centralizeText);

        setPosition(position);
        setSize(size);
    }

    public GUIButton(Vector2i position, Vector2i size, Color pressed, Color calmState, GUIText text, GUIPanel panel) {
        this(position, size, pressed, calmState, text, panel, true);
    }

    public GUIButton(Vector2i position, Vector2i size, Color calmState, GUIText text) {
        this(position, size, calmState, text, true);
    }

    public GUIButton(Vector2i position, Vector2i size, Color calmState, String text) {
        this(position, size, calmState, new GUIText(position, text));
    }

    public boolean isPressed() {
        return active;
    }

    public void setPressScript(GUIButtonScript script) {
        this.pressScript = script;
    }

    public void setUnpressScript(GUIButtonScript script) {
        this.unpressScript = script;
    }

    @Override
    public void pressed() {
        activeColor.red = pressedColor.red;
        activeColor.green = pressedColor.green;
        activeColor.blue = pressedColor.blue;
        activeColor.alpha = pressedColor.alpha;

        if (pressScript != null)
            pressScript.execute(this);
    }

    @Override
    public void unpressed() {
        activeColor.red = calmStateColor.red;
        activeColor.green = calmStateColor.green;
        activeColor.blue = calmStateColor.blue;
        activeColor.alpha = calmStateColor.alpha;

        if (unpressScript != null)
            unpressScript.execute(this);
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        panel.update(partsBuilder, properties);
        text.update(partsBuilder, properties);
    }

}