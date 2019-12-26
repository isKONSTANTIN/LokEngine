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

    public GUISwitch(Vector2i position, Vector2i size, Color colorHead, Color colorBackground, Color colorFill) {
        super(position, size);
        framePart = new GUISwitchFramePart(position, size, colorHead, colorBackground, colorFill);
        updateHeadPosition();
        updateHeadSize();
    }

    public GUISwitch(Vector2i position, Vector2i size) {
        super(position, size);
        framePart = new GUISwitchFramePart(position, size);
        updateHeadPosition();
        updateHeadSize();
    }

    public GUISwitch(Color colorHead, Color colorBackground, Color colorFill) {
        this(new Vector2i(), new Vector2i(), colorHead, colorBackground, colorFill);
    }

    public GUISwitch() {
        this(new Vector2i(), new Vector2i());
    }

    protected void updateHeadPosition() {
        framePart.headPosition = new Vector2i(framePart.status ? (position.x + size.x - framePart.headSize.x) : position.x, position.y);
    }

    protected void updateHeadSize() {
        framePart.headSize = switchSizeAlgorithm != null ? switchSizeAlgorithm.calculate(this) : new Vector2i(size.x / 3, size.y);
    }

    public void switchStatus() {
        setStatus(!framePart.status);
    }

    public boolean getStatus() {
        return framePart.status;
    }

    public void setStatus(boolean status) {
        framePart.status = status;
        updateHeadPosition();
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
        updateHeadPosition();
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        updateHeadSize();
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (properties.mouseRaycastStatus.mouse.inField(new Vector2i(parentProperties.globalPosition.x + framePart.headPosition.x, parentProperties.globalPosition.y + framePart.headPosition.y), framePart.headSize) && properties.mouseRaycastStatus.mouse.getPressedStatus() && !properties.mouseRaycastStatus.lastFramePressed)
            switchStatus();

        partsBuilder.addPart(framePart);
    }
}
