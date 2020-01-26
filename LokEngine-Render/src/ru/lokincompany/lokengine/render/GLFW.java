package ru.lokincompany.lokengine.render;

public class GLFW {

    private static boolean inited;

    public static synchronized boolean init() {
        if (!inited) {
            inited = org.lwjgl.glfw.GLFW.glfwInit();
        }
        return inited;
    }

    public static boolean isInited() {
        return inited;
    }

}
