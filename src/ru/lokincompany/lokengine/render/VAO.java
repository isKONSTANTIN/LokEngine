package ru.lokincompany.lokengine.render;

import ru.lokincompany.lokengine.render.exceptions.GLFWNotInitializedError;

import static org.lwjgl.opengl.GL30.*;

public class VAO {
    int vaoID;

    public VAO() {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        vaoID = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(vaoID);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public int getID() {
        return vaoID;
    }

    public void unload() throws GLFWNotInitializedError {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        glDeleteVertexArrays(vaoID);
        vaoID = -1;
    }

}
