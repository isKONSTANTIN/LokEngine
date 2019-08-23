package LokEngine.Loaders;

import LokEngine.Tools.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class BufferLoader {

    public static int load(float[] points) {
        int buffer = -1;
        try {

            FloatBuffer pointsFB = BufferUtils.createFloatBuffer(points.length);
            pointsFB.put(points);
            pointsFB.flip();

            buffer = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, pointsFB, GL15.GL_STATIC_DRAW);

        } catch (Exception e) {
            Logger.error("Fail generate buffer!", "LokEngine_BufferLoader");
            Logger.printException(e);
        }
        return buffer;
    }

    public static int load(ArrayList<Float> points) {
        int buffer = -1;
        try {

            FloatBuffer pointsFB = BufferUtils.createFloatBuffer(points.size());

            float[] arrayPoints = new float[points.size()];

            for (int i = 0; i < arrayPoints.length; i++){
                arrayPoints[i] = points.get(i);
            }

            pointsFB.put(arrayPoints);
            pointsFB.flip();

            buffer = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, pointsFB, GL15.GL_STATIC_DRAW);

        } catch (Exception e) {
            Logger.error("Fail generate buffer!", "LokEngine_BufferLoader");
            Logger.printException(e);
        }
        return buffer;
    }

    public static void unload(int buffer){
        GL15.glDeleteBuffers(buffer);
    }

}
