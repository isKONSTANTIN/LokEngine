package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI;

import ru.lokinCompany.lokEngine.Render.Enums.FramePartType;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Render.Frame.FramePart;
import ru.lokinCompany.lokEngine.Tools.Text.Font;
import ru.lokinCompany.lokEngine.Tools.Text.TextColorShader;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

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
