package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUISwitchFramePart;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUISwitch extends GUIObject {
    public GUILocationAlgorithm switchSizeAlgorithm;
    protected GUISwitchFramePart framePart;
    protected GUIText text;

    public GUISwitch() {
        super(new Vector2i(), new Vector2i(25, 15));
        framePart = new GUISwitchFramePart(position, size);
    }

    public GUISwitch setColorHead(Color colorHead) {
        framePart.colorHead = colorHead;
        return this;
    }

    public GUISwitch setColorBackground(Color colorBackground) {
        framePart.colorBackground = colorBackground;
        return this;
    }

    public GUISwitch setColorFill(Color colorFill) {
        framePart.colorFill = colorFill;
        return this;
    }

    public GUISwitch setText(GUIText text) {
        this.text = text;
        text.setPosition(object -> new Vector2i(position.x + size.x + 2, position.y));
        setSize(new Vector2i(
                (int) (text.framePart.font.getFontHeight() * (5f / 3f)),
                text.framePart.font.getFontHeight()
        ));
        return this;
    }

    public void switchStatus() {
        setStatus(!framePart.status);
    }

    public boolean getStatus() {
        return framePart.status;
    }

    public GUISwitch setStatus(boolean status) {
        framePart.status = status;
        return this;
    }

    @Override
    public GUISwitch setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
        return this;
    }

    @Override
    public GUISwitch setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;

        framePart.headSize.x = (int) (size.x / 2.5f);
        framePart.headSize.y = size.y;

        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (text != null)
            text.update(partsBuilder, parentProperties);

        if (properties.mouseRaycastStatus.mouse.inField(new Vector2i(parentProperties.globalPosition.x + framePart.headPosition.x, parentProperties.globalPosition.y + framePart.headPosition.y), framePart.headSize) && properties.mouseRaycastStatus.mouse.getPressedStatus() && !properties.mouseRaycastStatus.lastFramePressed)
            switchStatus();

        if (framePart.status && framePart.animationStatus < 1) {
            framePart.animationStatus += 0.1f;
        } else if (!framePart.status && framePart.animationStatus > 0)
            framePart.animationStatus -= 0.1f;

        framePart.headPosition.x = (int) (position.x + (size.x - framePart.headSize.x) * framePart.animationStatus);
        framePart.headPosition.y = position.y;

        if (switchSizeAlgorithm != null)
            framePart.headSize = switchSizeAlgorithm.calculate(this);

        partsBuilder.addPart(framePart);
    }
}
