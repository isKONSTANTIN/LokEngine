package ru.lokinCompany.LokEngine.Render.Frame.FrameParts.GUI;

import ru.lokinCompany.LokEngine.Render.Enums.FramePartType;
import ru.lokinCompany.LokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.LokEngine.Render.Frame.FramePart;
import ru.lokinCompany.LokEngine.Tools.Text.Font;
import ru.lokinCompany.LokEngine.Tools.Text.TextColorShader;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;

public class GUITextFramePart extends FramePart {

    protected Font font;
    public String text;
    public Vector2i position;
    public Color color;
    public TextColorShader shader;

    public GUITextFramePart(String text, Font font, Color color) {
        super(FramePartType.GUI);
        this.color = color;
        this.text = text;
        this.font = font;
    }

    public GUITextFramePart(String text, Font font, TextColorShader shader) {
        super(FramePartType.GUI);
        this.text = text;
        this.font = font;
        this.shader = shader;
    }

    public int getHeight() {
        return font.getHeight(text);
    }

    public int getWidth() {
        return font.getWidth(text);
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        if (color != null){
            font.drawText(text, position.x, position.y, color);
        }else {
            font.drawText(text, position.x, position.y, shader);
        }
    }
}
