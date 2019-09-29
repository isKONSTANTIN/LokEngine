package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUITextFramePart extends FramePart {
    protected int buffer;

    public String text;
    public Vector2i position;
    public Color color;

    public GUITextFramePart(String text, Color color) {
        super(FramePartType.GUI);
        this.color = color;
        this.text = text;
    }

    public int getHeight(){
        return 10;
    }
    public int getWidth(){
        return 0;
    }

    @Override
    public void partRender() {

    }
}
