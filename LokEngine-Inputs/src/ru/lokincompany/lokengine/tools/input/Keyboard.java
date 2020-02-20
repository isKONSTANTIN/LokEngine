package ru.lokincompany.lokengine.tools.input;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import ru.lokincompany.lokengine.tools.input.additionalobjects.KeyInfo;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    private GLFWKeyCallback callbackKey;
    private GLFWCharCallback callbackChar;
    private ArrayList<KeyInfo> keysPressed = new ArrayList<>();
    private long windowID;

    public Keyboard(long windowID) {
        this.windowID = windowID;
        callbackKey = GLFWKeyCallback.create(this::keyCallback);
        callbackChar = GLFWCharCallback.create(this::charCallback);

        glfwSetKeyCallback(windowID, callbackKey);
        glfwSetCharCallback(windowID, callbackChar);
    }

    public boolean next() {
        return keysPressed.size() > 0;
    }

    public KeyInfo getPressedKey() {
        if (keysPressed.size() > 0) {
            KeyInfo key = keysPressed.get(0);
            keysPressed.remove(0);
            return key;
        }
        return null;
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        keysPressed.add(new KeyInfo((char) 0, key, action, mods));
    }

    private void charCallback(long window, int key) {
        keysPressed.add(new KeyInfo((char) key, 0, 0, 0));
    }

    public void close() {
        callbackKey.close();
        callbackChar.close();
    }

    public boolean isKeyDown(int glfwKey) {
        return glfwGetKey(windowID, glfwKey) == 1;
    }

}
