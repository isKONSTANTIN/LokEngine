package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.tools.text.Font;
import ru.lokincompany.lokengine.tools.text.TextColorShader;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

public class GUITextFramePart extends FramePart {

    public Font font;
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
        if (color != null) {
            font.drawText(text, position.x, position.y, color);
        } else {
            font.drawText(text, position.x, position.y, shader);
        }
    }
}