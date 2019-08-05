package LokEngine.Loaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class BufferLoader {

    public static int load(float[] points){
        FloatBuffer pointsFB = BufferUtils.createFloatBuffer(points.length);
        pointsFB.put(points);
        pointsFB.flip();

        int buffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, pointsFB, GL15.GL_STATIC_DRAW);

        return buffer;
    }

    public static void unload(int buffer){
        GL15.glDeleteBuffers(buffer);
    }

}
