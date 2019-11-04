package ru.lokinCompany.lokEngine.GUI.GUIObjects;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class GUIObject {
    protected Vector2i position;
    protected Vector2i size;

    protected boolean touchable;
    protected boolean active;
    protected boolean focused;

    public boolean hidden;
    public GUIObjectProperties properties;

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public Vector2i getPosition() {
        return position;
    }

    public void setSize(Vector2i size) {
        this.size = size;
        properties.size.x = size.x;
        properties.size.y = size.y;
    }

    public Vector2i getSize() {
        return size;
    }

    protected void pressed(){}
    protected void unpressed(){}
    protected void focused(){}
    protected void unfocused(){}

    public GUIObject(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
        properties = new GUIObjectProperties(position, size);
    }

    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        properties.globalPosition.x = parentProperties.globalPosition.x + position.x;
        properties.globalPosition.y = parentProperties.globalPosition.y + position.y;
        properties.window = parentProperties.window;
        properties.mouseRaycastStatus = parentProperties.mouseRaycastStatus;

        boolean inField = properties.mouseRaycastStatus.mouse.inField(properties.globalPosition, size);
        boolean mousePressed = properties.mouseRaycastStatus.mouse.getPressedStatus();

        if (!properties.mouseRaycastStatus.touched && touchable){
            if (inField){
                properties.mouseRaycastStatus.touched = mousePressed;

                if (mousePressed && !active){
                    active = true;
                    focused = true;
                    pressed();
                    focused();
                }else if (!mousePressed && active){
                    active = false;
                    unpressed();
                }
            }else if (active) {
                active = false;
                focused = false;
                unpressed();
                unfocused();
            }
        }

        if (!inField && mousePressed || properties.mouseRaycastStatus.touched && !inField){
            if (focused){
                focused = false;
                unfocused();
            }
            if (active) {
                active = false;
                unpressed();
            }
        }
    }

}
