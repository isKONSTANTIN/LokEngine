package ru.lokincompany.lokengine.gui.guiobjects.guislider;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUISliderFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUISlider extends GUIObject {

    public GUISliderColorShader colorFillShader;
    public GUISliderColorShader colorBackgroundShader;
    protected Vector2f range = new Vector2f(0, 100);
    protected GUISliderFramePart framePart;
    protected GUISliderHead head;

    public GUISlider() {
        super(new Vector2i(), new Vector2i(100, 10));
        framePart = new GUISliderFramePart(position, size, Colors.engineBackgroundColor(), Colors.engineMainColor(), head);
        this.head = new GUISliderHead().setSize(new Vector2i(11, 11));
        touchable = true;

        head.setPosition(object -> {
            Vector2i headPos = new Vector2i((int) (this.getSize().x * framePart.filled) - object.getSize().x / 2, this.getSize().y / 2 - object.getSize().y / 2);

            headPos.x = Math.min(Math.max(headPos.x, 0), this.getSize().x - object.getSize().x);

            headPos.x += this.getPosition().x;
            headPos.y += this.getPosition().y;
            return headPos;
        });

        if (head.getSize().x == -1 || head.getSize().y == -1)
            head.setSize(object -> new Vector2i(getSize().y + 2, this.getSize().y + 2));
    }

    public GUISlider setFilledBackgroundColor(Color filledColor) {
        framePart.colorFill = filledColor;
        return this;
    }

    public GUISlider setBackgroundColor(Color backgroundColor) {
        framePart.colorBackground = backgroundColor;
        return this;
    }

    public Vector2f getRange() {
        return range;
    }

    public GUISlider setRange(Vector2f range) {
        this.range = range;
        return this;
    }

    public float getValue() {
        return (range.y - range.x) * framePart.filled + range.x;
    }

    public GUISlider setValue(float value) {
        framePart.filled = value;
        return this;
    }

    public GUISliderHead getHead() {
        return head;
    }

    public GUISlider setHead(GUISliderHead head) {
        this.head = head;
        return this;
    }

    @Override
    public GUISlider setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
        return this;
    }

    @Override
    public GUISlider setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        return this;
    }

    protected void calculateFilled() {
        framePart.filled = Math.min(Math.max((properties.mouseRaycastStatus.mouse.getMousePosition().x - properties.globalPosition.x) / (float) size.x, 0), 1);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        if (active || head.getActive() || retention || head.getRetention()) {
            calculateFilled();
        }
        head.update(partsBuilder, parentProperties);

        if (head.colorShader != null)
            head.color = head.colorShader.getColor(framePart.filled);

        if (colorFillShader != null)
            this.framePart.colorFill = colorFillShader.getColor(framePart.filled);

        if (colorBackgroundShader != null)
            this.framePart.colorBackground = colorBackgroundShader.getColor(framePart.filled);

        partsBuilder.addPart(framePart);
    }

}
