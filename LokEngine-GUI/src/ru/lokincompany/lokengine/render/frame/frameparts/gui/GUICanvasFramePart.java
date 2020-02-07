package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.OpenGLFastTools;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class GUICanvasFramePart extends FramePart {
    public PartsBuilder partsBuilder;
    public Vector2i position;
    public Vector2i size;
    public Vector2i viewOffset = new Vector2i(0, 0);
    public Color color = new Color(1, 1, 1, 1);

    static float[] texCoords = new float[]{
            0, 1,
            1, 1,
            1, 0,
            0, 0
    };

    public GUICanvasFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.partsBuilder = new PartsBuilder(size);
        this.position = position;
        this.size = size;
    }

    @Override
    public void init(RenderProperties renderProperties) {
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        int texture = partsBuilder.build(DrawMode.RawGUI, renderProperties, viewOffset);

        glBindTexture(GL_TEXTURE_2D, texture);
        glColor4d(color.red, color.green, color.blue, color.alpha);

        OpenGLFastTools.drawSquare(position, size, texCoords);

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
