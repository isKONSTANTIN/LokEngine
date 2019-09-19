package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUIPanelFramePart;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Actions.BlurAction;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIPanel extends GUIObject {
    GUIPanelFramePart framePart;
    BlurAction blurAction;

    public GUIPanel(Vector2i position, Vector2i size, Color color, BlurTuning blur){
        super(position, size);
        if (color != null){
            this.framePart = new GUIPanelFramePart(position,size,color);
        }else{
            this.framePart = new GUIPanelFramePart(position,size);
        }

        if (blur != null)
            this.blurAction = new BlurAction(position,size, blur);
    }

    public GUIPanel(Vector2i position, Vector2i size, Color color){
        this(position,size,color,null);
    }

    public GUIPanel(Vector2i position, Vector2i size){
        this(position,size,null);
    }

    @Override
    public void setPosition(Vector2i position){
        this.position = position;
        framePart.position = position;
        if (blurAction != null)
            blurAction.position = position;
    }

    @Override
    public void setSize(Vector2i size){
        this.size = size;
        framePart.size = size;
        if (blurAction != null)
            blurAction.size = size;
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalSourcePos){
        partsBuilder.addPart(framePart);
        if (blurAction != null){
            blurAction.position = new Vector2i(globalSourcePos.x + position.x, globalSourcePos.y + position.y);
            RuntimeFields.getFrameBuilder().getPostProcessingActionWorker("Blur Action Worker").addPostProcessingAction(blurAction);
        }
    }

}

