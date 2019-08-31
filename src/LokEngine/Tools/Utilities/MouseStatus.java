package LokEngine.Tools.Utilities;

import org.lwjgl.input.Mouse;

public class MouseStatus {
    private Vector2i mousePosition = new Vector2i();
    private boolean mousePressed = false;
    public int buttonID;

    public boolean getPressedStatus(){
        return mousePressed;
    }

    public Vector2i getMousePosition(){
        return new Vector2i(mousePosition.x, mousePosition.y);
    }

    public void update(){
        mousePosition.x = Mouse.getX();
        mousePosition.y = Mouse.getY();
        mousePressed = Mouse.isButtonDown(buttonID);
    }

}
