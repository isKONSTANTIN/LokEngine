package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
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

    public GUIPanel() {
        super(new Vector2i(), new Vector2i(100,100));
        this.framePart = new GUIPanelFramePart(position, size);
        setBlurTuning(new BlurTuning());
    }

    public GUIPanel setColor(Color color){
        framePart.color = color;
        return this;
    }

    public Color getColor(){
        return framePart.color;
    }

    public GUIPanel setBlurTuning(BlurTuning blur){
        if (blur == null){
            blurAction = null;
            return this;
        }

        this.blurAction = new BlurAction(position, size, blur);
        return this;
    }

    public BlurTuning getBlurTuning(){
        return blurAction.blurTuning;
    }

    @Override
    public GUIPanel setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
        if (blurAction != null)
            blurAction.position = position;
        return this;
    }

    @Override
    public GUIPanel setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        if (blurAction != null)
            blurAction.size = size;
        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);

        BlurActionWorker blurActionWorker = parentProperties.window.getFrameBuilder().getPostProcessingActionWorker(BlurActionWorker.class);

        if (blurAction == null || blurActionWorker == null) return;

        blurAction.position = new Vector2i(parentProperties.globalPosition.x + position.x, parentProperties.globalPosition.y + position.y);
        blurActionWorker.addPostProcessingAction(blurAction);
    }
}

