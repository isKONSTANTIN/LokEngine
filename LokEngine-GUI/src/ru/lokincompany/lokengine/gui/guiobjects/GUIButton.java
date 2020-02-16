package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIButtonScript;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIButton extends GUIObject {
    protected Color pressedColor;
    protected Color calmStateColor;
    protected Color activeColor;

    protected GUIText text;
    protected GUIPanel panel;

    protected GUIButtonScript pressScript;
    protected GUIButtonScript unpressScript;

    protected boolean centralizeText = true;

    public GUIButton() {
        super(new Vector2i(), new Vector2i(60, 20));

        this.pressedColor = Colors.engineBrightBackgroundColor().setAlpha(0.8f);
        this.calmStateColor = Colors.engineBrightBackgroundColor().setAlpha(0.5f);
        this.activeColor = new Color(calmStateColor.red, calmStateColor.green, calmStateColor.blue, calmStateColor.alpha);

        setText(new GUIText());

        this.panel = new GUIPanel()
                .setColor(activeColor)
                .setPosition(object -> getPosition())
                .setSize(object -> getSize());

        this.touchable = true;
    }

    public GUIText getText() {
        return text;
    }

    public GUIButton setText(GUIText text){
        this.text = text;
        text.setPosition(object -> new Vector2i(
                getPosition().x + (centralizeText ? (int) (getSize().x / 2f - text.getSize().x / 2f) : 0),
                getPosition().y + (int) (getSize().y / 2f - text.getSize().y / 2f)
        ));
        size = this.text.getSize();
        size.x += size.x / 5;
        size.y += size.y / 5;
        return this;
    }

    public GUIPanel getPanel() {
        return panel;
    }

    public boolean isCentralizeText() {
        return centralizeText;
    }

    public boolean isPressed() {
        return active;
    }

    public GUIButton setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
        return this;
    }

    public GUIButton setCalmStateColor(Color calmStateColor) {
        this.calmStateColor = calmStateColor;
        return this;
    }

    public GUIButton setCentralizeText(boolean centralizeText) {
        this.centralizeText = centralizeText;
        return this;
    }

    public GUIButton setPressScript(GUIButtonScript script) {
        this.pressScript = script;
        return this;
    }

    public GUIButton setUnpressScript(GUIButtonScript script) {
        this.unpressScript = script;
        return this;
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
        mouseInField();

        if (unpressScript != null)
            unpressScript.execute(this);
    }

    @Override
    protected void mouseInField() {
        activeColor.red = (pressedColor.red + calmStateColor.red) / 2f;
        activeColor.green = (pressedColor.green + calmStateColor.green) / 2f;
        activeColor.blue = (pressedColor.blue + calmStateColor.blue) / 2f;
        activeColor.alpha = (pressedColor.alpha + calmStateColor.alpha) / 2f;
    }

    @Override
    protected void mouseOutField() {
        activeColor.red = calmStateColor.red;
        activeColor.green = calmStateColor.green;
        activeColor.blue = calmStateColor.blue;
        activeColor.alpha = calmStateColor.alpha;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        panel.update(partsBuilder, parentProperties);
        text.update(partsBuilder, parentProperties);
    }

}