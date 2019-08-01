package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUIPanelFramePart;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIObject {
    Vector2i position;
    Vector2i size;
    Color color;
    BlurTuning blurTuning;

    public GUIObject(Vector2i position, Vector2i size, Color color, BlurTuning blur){
        this.position = position;
        this.size = size;
        this.color = color;
        this.blurTuning = blur;
    }

    public GUIObject(Vector2i position, Vector2i size, Color color){
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public void update(){
        RuntimeFields.frameBuilder.addPart(new GUIPanelFramePart(position,size));
    }

}
