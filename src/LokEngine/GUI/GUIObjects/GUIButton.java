package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIButtonScript;
import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIButton extends GUIObject {

    public Color pressedColor;
    public Color calmStateColor;
    public GUIText text;
    public GUIPanel panel;

    private Color activeColor;
    private GUIButtonScript pressScript;
    private GUIButtonScript unpressScript;

    public boolean isPressed() {
        return active;
    }

    public GUIButton(Vector2i position, Vector2i size, Color pressed, Color calmState, GUIText text, GUIPanel panel) {
        super(position, size);
        this.text = text;
        if (size.x <= 0 || size.y <= 0) {
            size = this.text.getSize();
        }
        this.pressedColor = pressed;
        this.calmStateColor = calmState;
        this.panel = panel;
        this.activeColor = new Color(calmStateColor.red, calmStateColor.green, calmStateColor.blue, calmStateColor.alpha);
        this.touchable = true;

        setPosition(position);
        setSize(size);
    }

    public GUIButton(Vector2i position, Vector2i size, Color calmState, String text) {
        super(position, size);
        this.text = new GUIText(position, text);
        if (size.x <= 0 || size.y <= 0) {
            size = this.text.getSize();
        }
        float colorChange = (calmState.red + calmState.green + calmState.blue) / 3 >= 0.5f ? -0.1f : 0.1f;
        this.pressedColor = new Color(calmState.red + colorChange, calmState.green + colorChange, calmState.blue + colorChange, calmState.alpha);
        this.calmStateColor = calmState;
        this.activeColor = new Color(calmStateColor.red, calmStateColor.green, calmStateColor.blue, calmStateColor.alpha);
        this.panel = new GUIPanel(position, size, activeColor);
        this.touchable = true;
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
        this.position = position;

        text.setPosition(new Vector2i(position.x + size.x / 2 - text.getSize().x / 2, position.y + size.y / 2 - text.getSize().y / 2));
        panel.setPosition(position);
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);

        text.setPosition(new Vector2i(position.x + size.x / 2 - text.getSize().x / 2, position.y + size.y / 2 - text.getSize().y / 2));
        panel.setSize(size);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        panel.update(partsBuilder, properties);
        text.update(partsBuilder, properties);
    }

}