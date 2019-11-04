package ru.lokinCompany.LokEngine.GUI.GUIObjects;

import ru.lokinCompany.LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.LokEngine.Render.Frame.FrameParts.GUI.GUIPanelFramePart;
import ru.lokinCompany.LokEngine.Render.Frame.FrameParts.PostProcessing.Actions.BlurAction;
import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.Tools.Utilities.BlurTuning;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;

public class GUIPanel extends GUIObject {
    protected GUIPanelFramePart framePart;
    protected BlurAction blurAction;

    public GUIPanel(Vector2i position, Vector2i size, Color color, BlurTuning blur) {
        super(position, size);
        if (color != null) {
            this.framePart = new GUIPanelFramePart(position, size, color);
        } else {
            this.framePart = new GUIPanelFramePart(position, size);
        }

        if (blur != null)
            this.blurAction = new BlurAction(position, size, blur);
    }

    public GUIPanel(Vector2i position, Vector2i size, Color color) {
        this(position, size, color, null);
    }

    public GUIPanel(Vector2i position, Vector2i size) {
        this(position, size, null);
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
        if (blurAction != null)
            blurAction.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        if (blurAction != null)
            blurAction.size = size;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
        if (blurAction != null) {
            blurAction.position = new Vector2i(parentProperties.globalPosition.x + position.x, parentProperties.globalPosition.y + position.y);
            parentProperties.window.getFrameBuilder().getPostProcessingActionWorker("Blur Action Worker").addPostProcessingAction(blurAction);
        }
    }

}

