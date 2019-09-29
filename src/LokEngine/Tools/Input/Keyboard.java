package LokEngine.Tools.Input;

import LokEngine.Render.Window;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    private GLFWKeyCallback callbackKey;
    private GLFWCharCallback callbackChar;
    private ArrayList <Integer>keysPressed = new ArrayList();
    private ArrayList <Character>charsPressed = new ArrayList();
    private Window window;

    public Keyboard(Window window){
        this.window = window;
        callbackKey = GLFWKeyCallback.create(this::keyCallback);
        callbackChar = GLFWCharCallback.create(this::charCallback);
        glfwSetKeyCallback(window.getId(), callbackKey);
        glfwSetCharCallback(window.getId(),callbackChar);
    }

    public boolean next(){
        return keysPressed.size() > 0;
    }

    public int getPressedKey(){
        if (keysPressed.size() > 0){
            int key = keysPressed.get(0);
            keysPressed.remove(0);
            return key;
        }
        return 0;
    }

    public char getPressedChar(){
        if (charsPressed.size() > 0){
            char key = charsPressed.get(0);
            charsPressed.remove(0);
            return key;
        }
        return 0;
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods){
        keysPressed.add(key);
    }

    private void charCallback(long window, int key){
        charsPressed.add((char)key);
    }

    public void close(){
        callbackKey.close();
        callbackChar.close();
    }

    public boolean isKeyDown(int glfwKey){
        return glfwGetKey(window.getId(), glfwKey) == 1;
    }

}
