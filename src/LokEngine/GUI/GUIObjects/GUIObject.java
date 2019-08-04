package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUIPanelFramePart;
import LokEngine.Render.Frame.FrameParts.PostProcessingActions.BlurAction;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIObject {
    Vector2i position;
    Vector2i size;

    public GUIObject(Vector2i position, Vector2i size){
        this.position = position;
        this.size = size;
    }

    public void update(){ }

}
