package LokEngine.Render.Frame.FrameParts;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIPanelFramePart extends FramePart {

    public GUIPanelFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.RawGUI);
    }
    @Override
    public void partRender() {

    }
}
