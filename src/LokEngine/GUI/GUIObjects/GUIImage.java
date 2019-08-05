package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUIImageFramePart;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIImage extends GUIObject {

    GUIImageFramePart framePart;

    public GUIImage(Vector2i position, Vector2i size, String path) {
        super(position, size);
        framePart = new GUIImageFramePart(position, size, path);
    }

    @Override
    public void update(){
        RuntimeFields.frameBuilder.addPart(framePart);
    }

}
