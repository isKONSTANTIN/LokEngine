package ru.lokincompany.lokengine.render.exceptions;

public class GLFWNotInitializedError extends GLFWError {
    public GLFWNotInitializedError() {
        super("GLFW not initialized!");
    }
}
