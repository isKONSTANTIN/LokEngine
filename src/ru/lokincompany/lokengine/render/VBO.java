package ru.lokincompany.lokengine.render;

import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.render.exceptions.GLFWNotInitializedError;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.glBindBuffer;

public class VBO {
    int vboID = -1;
    int size;

    public VBO() {
        createNew();
    }

    public VBO(float[] points) {
        this();
        putData(points);
    }

    public VBO(ArrayList<Float> points) {
        this();
        putData(points);
    }

    public VBO(Vector2f[] points) {
        this();
        putData(points);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
    }

    public int getID() {
        return vboID;
    }

    public int getSize() {
        return size;
    }

    public void createNew(){
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();

        if (vboID != -1)
            unload();

        vboID = GL15.glGenBuffers();
    }

    public void putData(float[] points) throws GLFWNotInitializedError {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();

        bind();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, points, GL15.GL_DYNAMIC_DRAW);
        unbind();

        size = points.length;
    }

    public void putData(ArrayList<Float> points) throws GLFWNotInitializedError {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        float[] pointsArray = new float[points.size()];

        for (int i = 0; i < pointsArray.length; i++) {
            pointsArray[i] = points.get(i);
        }

        bind();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, pointsArray, GL15.GL_DYNAMIC_DRAW);
        unbind();

        size = points.size();
    }

    public void putData(Vector2f[] points) throws GLFWNotInitializedError {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        float[] floatPoints = new float[points.length * 2];

        for (int i = 0; i < points.length; i++) {
            floatPoints[i * 2] = points[i].x;
            floatPoints[i * 2 + 1] = points[i].x;
        }

        putData(floatPoints);
    }

    public void unload() throws GLFWNotInitializedError {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        GL15.glDeleteBuffers(vboID);
        vboID = -1;
        size = 0;
    }
}
