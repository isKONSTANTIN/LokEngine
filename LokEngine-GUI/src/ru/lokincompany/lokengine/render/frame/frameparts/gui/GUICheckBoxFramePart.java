package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.ColorRGB;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUICheckBoxFramePart extends FramePart {
    public GUIImageFramePart imageFramePart;
    public Vector2i position;
    public Vector2i size;
    public Color color;
    public boolean status;
    public float colorStatus;

    public GUICheckBoxFramePart(Vector2i position, Vector2i size, String path, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.imageFramePart = new GUIImageFramePart(position, size, path);
        this.color = color;
    }

    public GUICheckBoxFramePart(Vector2i position, Vector2i size, Texture texture, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.imageFramePart = new GUIImageFramePart(position, size);
        this.imageFramePart.texture = texture;
        this.color = color;
    }

    public GUICheckBoxFramePart(Vector2i position, Vector2i size, Color color) {
        super(FramePartType.GUI);
        this.position = position;
        this.imageFramePart = new GUIImageFramePart(position, size);
        this.size = size;
        this.color = color;
    }

    public GUICheckBoxFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.position = position;
        this.imageFramePart = new GUIImageFramePart(position, size);
        this.size = size;
        this.color = new ColorRGB(219, 160, 37, 255);
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        imageFramePart.position = position;
        imageFramePart.size = size;
        imageFramePart.color.alpha = colorStatus;

        if (status && colorStatus < 1) {
            colorStatus += 0.1f;
        }else if (!status && colorStatus > 0)
            colorStatus -= 0.1f;

        if (colorStatus > 0){
            glColor4f(color.red, color.green, color.blue, color.alpha * colorStatus);
            OpenGLFastTools.drawSquare(position, size);
            imageFramePart.partRender(renderProperties);
        }

        glColor4f(color.red, color.green, color.blue, color.alpha);
        OpenGLFastTools.drawHollowSquare(position, size);
    }
}
