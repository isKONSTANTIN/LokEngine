package ru.lokincompany.lokengine.tools.input;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    public int buttonID = GLFW_MOUSE_BUTTON_LEFT;
    private Vector2i mousePosition = new Vector2i();
    private Vector2i lastMousePosition = new Vector2i(-1, 0);
    private Vector2i mouseDeltaPosition = new Vector2i();
    private Vector2f mouseScroll = new Vector2f();
    private Vector2f lastMouseScroll = new Vector2f();
    private boolean mousePressed = false;
    private long windowID;

    public Mouse(long windowID) {
        this.windowID = windowID;

        glfwSetMouseButtonCallback(windowID, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                mousePressed = button == buttonID && action == GLFW_PRESS;
            }
        });

        glfwSetScrollCallback(windowID, new GLFWScrollCallback() {
            @Override
            public void invoke(long l, double xoffset, double yoffset) {
                mouseScroll.x = (float) xoffset;
                mouseScroll.y = (float) yoffset;
            }
        });
    }

    public boolean getPressedStatus() {
        return mousePressed;
    }

    public Vector2i getMousePosition() {
        return new Vector2i(mousePosition.x, mousePosition.y);
    }

    public Vector2i getMouseDeltaPosition() {
        return new Vector2i(mouseDeltaPosition.x, mouseDeltaPosition.y);
    }

    public Vector2f getMouseScroll() {
        return new Vector2f(mouseScroll.x, mouseScroll.y);
    }

    public void update() {
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(windowID, xBuffer, yBuffer);

        mousePosition.x = (int) xBuffer.get(0);
        mousePosition.y = (int) yBuffer.get(0);

        if (lastMousePosition.x != -1){
            mouseDeltaPosition.x = mousePosition.x - lastMousePosition.x;
            mouseDeltaPosition.y = mousePosition.y - lastMousePosition.y;
        }

        if (lastMouseScroll.equals(mouseScroll) && (mouseScroll.x != 0 || mouseScroll.y != 0)) {
            mouseScroll.x = 0;
            mouseScroll.y = 0;
        }
        lastMouseScroll = getMouseScroll();
        lastMousePosition = getMousePosition();
    }

    public boolean inField(Vector2i position, Vector2i size) {
        return (mousePosition.x >= position.x && mousePosition.x <= size.x + position.x) &&
                (mousePosition.y >= position.y && mousePosition.y <= size.y + position.y);
    }

}
