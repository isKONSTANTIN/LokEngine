package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUIPanelFramePart;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurAction;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurTuning;
import ru.lokincompany.lokengine.render.postprocessing.workers.blur.BlurActionWorker;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

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

    public GUIPanel(Color color, BlurTuning blur) {
        this(new Vector2i(), new Vector2i(), color, blur);
    }

    public GUIPanel(Color color) {
        this(new Vector2i(), new Vector2i(), color, null);
    }

    public GUIPanel() {
        this(new Vector2i(), new Vector2i(), null);
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
            parentProperties.window.getFrameBuilder().getPostProcessingActionWorker(BlurActionWorker.class).addPostProcessingAction(blurAction);
        }
    }

}

