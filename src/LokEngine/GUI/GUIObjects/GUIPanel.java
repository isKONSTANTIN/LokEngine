package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUIPanelFramePart;
import LokEngine.Render.Frame.FrameParts.PostProcessingActions.BlurAction;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIPanel extends GUIObject {
    Color color;
    GUIPanelFramePart framePart;
    BlurAction blurAction;

    public GUIPanel(Vector2i position, Vector2i size, Color color, BlurTuning blur){
        super(position, size);
        this.color = color;
        this.framePart = new GUIPanelFramePart(position,size);
        if (blur != null)
            this.blurAction = new BlurAction(position,size, blur);
    }

    public GUIPanel(Vector2i position, Vector2i size, Color color){
        this(position,size,color,null);
    }

    @Override
    public void update(){
        RuntimeFields.frameBuilder.addPart(framePart);
        if (blurAction != null)
            RuntimeFields.frameBuilder.addPostProcessingAction(blurAction);
    }

}

