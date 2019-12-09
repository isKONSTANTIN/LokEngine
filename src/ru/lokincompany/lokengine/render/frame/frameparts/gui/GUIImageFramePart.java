package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

import static org.lwjgl.opengl.GL11.*;

public class GUIImageFramePart extends FramePart {

    public Texture texture;
    public Vector2i position;
    public Vector2i size;
    public Color color = Colors.white();

    public GUIImageFramePart(Vector2i position, Vector2i size, String path) {
        super(FramePartType.GUI);
        this.texture = TextureLoader.loadTexture(path);
        this.position = position;
        if (size.x <= 0 || size.y <= 0) {
            size = new Vector2i(texture.sizeX, texture.sizeY);
        }
        this.size = size;
    }

    public GUIImageFramePart(Vector2i position, Vector2i size) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        if (texture != null && texture.buffer != -1) {
            glBindTexture(GL_TEXTURE_2D, texture.buffer);
            glBegin(GL_POLYGON);
            glColor4d(color.red, color.green, color.blue, color.alpha);

            glTexCoord2f(0, 0);
            glVertex3f(position.x, position.y, 0);
            glTexCoord2f(1, 0);
            glVertex3f(size.x + position.x, position.y, 0);
            glTexCoord2f(1, 1);
            glVertex3f(size.x + position.x, size.y + position.y, 0);
            glTexCoord2f(0, 1);
            glVertex3f(position.x, size.y + position.y, 0);

            glEnd();
            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }
}
