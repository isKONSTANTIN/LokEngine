package ru.lokincompany.lokengine.render.frame.frameparts.gui;

import ru.lokincompany.lokengine.gui.guiobjects.guislider.GUISliderHead;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

import static org.lwjgl.opengl.GL11.*;

public class GUISliderFramePart extends FramePart {

    public Vector2i position;
    public Vector2i size;
    public Color colorBackground;
    public Color colorFill;
    public GUISliderHead head;
    public float filled;

    public GUISliderFramePart(Vector2i position, Vector2i size, Color colorBackground, Color colorFill, GUISliderHead head) {
        super(FramePartType.GUI);
        this.position = position;
        this.size = size;
        this.colorBackground = colorBackground;
        this.colorFill = colorFill;
        this.head = head;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        Vector2i headPos = head.getPosition();
        Vector2i headSize = head.getSize();

        glBegin(GL_QUADS);

        glColor4f(colorBackground.red, colorBackground.green, colorBackground.blue, colorBackground.alpha);
        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x + position.x, position.y, 0);
        glVertex3f(size.x + position.x, size.y + position.y, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glColor4f(colorFill.red, colorFill.green, colorFill.blue, colorFill.alpha);
        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x * filled + position.x, position.y, 0);
        glVertex3f(size.x * filled + position.x, size.y + position.y, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glColor4f(head.color.red, head.color.green, head.color.blue, head.color.alpha);
        if (head.getTexture() != null) {
            glEnd();

            glBindTexture(GL_TEXTURE_2D, head.getTexture().buffer);

            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex3f(headPos.x, headPos.y, 0);
            glTexCoord2f(1, 0);
            glVertex3f(headSize.x + headPos.x, headPos.y, 0);
            glTexCoord2f(1, 1);
            glVertex3f(headSize.x + headPos.x, headSize.y + headPos.y, 0);
            glTexCoord2f(0, 1);
            glVertex3f(headPos.x, headSize.y + headPos.y, 0);

            glEnd();
            glBindTexture(GL_TEXTURE_2D, 0);
        } else {
            glVertex3f(headPos.x, headPos.y, 0);
            glVertex3f(headSize.x + headPos.x, headPos.y, 0);
            glVertex3f(headSize.x + headPos.x, headSize.y + headPos.y, 0);
            glVertex3f(headPos.x, headSize.y + headPos.y, 0);

            glEnd();
        }
    }
}
