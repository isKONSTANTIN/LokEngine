package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUILocationAlgorithm;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUISlider.GUISliderHead;
import ru.lokinCompany.lokEngine.Render.Enums.FramePartType;
import ru.lokinCompany.lokEngine.Render.Frame.BuilderProperties;
import ru.lokinCompany.lokEngine.Render.Frame.FramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

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
            glVertex3f(headSize.x + headPos.x,headSize.y + headPos.y, 0);
            glTexCoord2f(0, 1);
            glVertex3f(headPos.x, headSize.y + headPos.y, 0);

            glEnd();
            glBindTexture(GL_TEXTURE_2D, 0);
        }else{
            glVertex3f(headPos.x, headPos.y, 0);
            glVertex3f(headSize.x + headPos.x, headPos.y, 0);
            glVertex3f(headSize.x + headPos.x,headSize.y + headPos.y, 0);
            glVertex3f(headPos.x, headSize.y + headPos.y, 0);

            glEnd();
        }
    }
}
