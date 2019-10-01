package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Text.Font;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUITextFramePart extends FramePart {

    protected Font font;
    public String text;
    public Vector2i position;
    public Color color;

    public GUITextFramePart(String text, Font font, Color color) {
        super(FramePartType.GUI);
        this.color = color;
        this.text = text;
        this.font = font;
    }

    public int getHeight() {
        return font.getHeight(text);
    }

    public int getWidth() {
        return font.getWidth(text);
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        font.drawText(text, position.x, position.y, color);
    }
}
