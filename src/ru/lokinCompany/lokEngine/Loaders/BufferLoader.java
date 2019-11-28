package ru.lokinCompany.lokEngine.Loaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import ru.lokinCompany.lokEngine.Render.GLFW;
import ru.lokinCompany.lokEngine.Tools.Logger;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class BufferLoader {

    public static int load(float[] points) {
        if (!GLFW.isInited()) return -1;
        int buffer = -1;
        try {
            buffer = GL15.glGenBuffers();

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, points, GL15.GL_DYNAMIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        } catch (Exception e) {
            Logger.error("Fail generate buffer!", "LokEngine_BufferLoader");
            Logger.printException(e);
        }

        return buffer;
    }

    public static int load(ArrayList<Float> points) {
        if (!GLFW.isInited()) return -1;
        int buffer = -1;
        try {
            FloatBuffer pointsFB = BufferUtils.createFloatBuffer(points.size());
            float[] arrayPoints = new float[points.size()];

            for (int i = 0; i < arrayPoints.length; i++) {
                arrayPoints[i] = points.get(i);
            }

            pointsFB.put(arrayPoints);
            pointsFB.flip();

            buffer = GL15.glGenBuffers();

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, pointsFB, GL15.GL_DYNAMIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        } catch (Exception e) {
            Logger.error("Fail generate buffer!", "LokEngine_BufferLoader");
            Logger.printException(e);
        }
        return buffer;
    }

    public static void unload(int buffer) {
        if (!GLFW.isInited()) return;
        GL15.glDeleteBuffers(buffer);
    }

}
