package ru.lokinCompany.lokEngine.Render;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class GLFW {

    private static boolean inited;

    public static synchronized boolean init() {
        if (!inited) {
            inited = glfwInit();
        }
        return inited;
    }

}
