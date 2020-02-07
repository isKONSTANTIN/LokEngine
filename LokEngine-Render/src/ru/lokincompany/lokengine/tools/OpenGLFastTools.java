package ru.lokincompany.lokengine.tools;

import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class OpenGLFastTools {

    private static float[] defaultTexCoords = new float[]{
            0, 0,
            1, 0,
            1, 1,
            0, 1
    };

    public static void drawSquare(Vector2i position, Vector2i size, float[] texCoords){
        glBegin(GL_POLYGON);

        glTexCoord2f(texCoords[0], texCoords[1]);
        glVertex3f(position.x, position.y, 0);

        glTexCoord2f(texCoords[2], texCoords[3]);
        glVertex3f(size.x + position.x, position.y, 0);

        glTexCoord2f(texCoords[4], texCoords[5]);
        glVertex3f(size.x + position.x, size.y + position.y, 0);

        glTexCoord2f(texCoords[6], texCoords[7]);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();
    }

    public static void drawSquare(Vector2i position, Vector2i size){
        drawSquare(position, size, defaultTexCoords);
    }

    public static void drawHollowSquare(Vector2i position, Vector2i size){
        glBegin(GL_LINE_STRIP);

        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x + position.x, position.y, 0);
        glVertex3f(size.x + position.x, size.y + position.y - 1, 0);
        glVertex3f(position.x + 1, size.y + position.y, 0);
        glVertex3f(position.x, position.y, 0);

        glEnd();
    }
}
