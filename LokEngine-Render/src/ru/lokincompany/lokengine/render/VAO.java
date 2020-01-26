package ru.lokincompany.lokengine.render;

import org.lwjgl.opengl.GL30;
import ru.lokincompany.lokengine.render.exceptions.GLFWNotInitializedError;

import static org.lwjgl.opengl.GL30.*;

public class VAO {
    int vaoID;

    public VAO() {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        vaoID = GL30.glGenVertexArrays();
    }

    public void bind() {
        GL30.glBindVertexArray(vaoID);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public int getID() {
        return vaoID;
    }

    public void unload() throws GLFWNotInitializedError {
        if (!GLFW.isInited()) throw new GLFWNotInitializedError();
        GL30.glDeleteVertexArrays(vaoID);
        vaoID = -1;
    }

}
