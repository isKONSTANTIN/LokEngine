package LokEngine.Tools.Input;

import LokEngine.Render.Window;
import LokEngine.Tools.Input.AdditionalObjects.MouseScrollScript;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private Vector2i mousePosition = new Vector2i();
    private boolean mousePressed = false;
    private MouseScrollScript mouseScrollScript;

    public int buttonID = GLFW_MOUSE_BUTTON_LEFT;
    private Window window;

    public Mouse(Window window) {
        this.window = window;
        mouseScrollScript = (xoffset, yoffset) -> {
        };

        glfwSetMouseButtonCallback(window.getId(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                mousePressed = button == buttonID && action == GLFW_PRESS;
            }
        });

        glfwSetScrollCallback(window.getId(), new GLFWScrollCallback() {
            @Override
            public void invoke(long l, double xoffset, double yoffset) {
                mouseScrollScript.execute(xoffset, yoffset);
            }
        });
    }

    public void setMouseScrollScript(MouseScrollScript script) {
        mouseScrollScript = script;
    }

    public boolean getPressedStatus() {
        return mousePressed;
    }

    public Vector2i getMousePosition() {
        return new Vector2i(mousePosition.x, mousePosition.y);
    }

    public void update() {
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window.getId(), xBuffer, yBuffer);

        mousePosition.x = (int) xBuffer.get(0);
        mousePosition.y = (int) yBuffer.get(0);
    }

    public boolean inField(Vector2i position, Vector2i size) {
        return (mousePosition.x >= position.x && mousePosition.x <= size.x + position.x) &&
                (mousePosition.y >= position.y && mousePosition.y <= size.y + position.y);
    }

}
