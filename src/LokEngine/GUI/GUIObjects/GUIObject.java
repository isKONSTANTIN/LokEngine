package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIObject {
    Vector2i position;
    Vector2i size;

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

    public GUIObject(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
        properties = new GUIObjectProperties(position, size);
    }

    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        properties.globalPosition.x = parentProperties.globalPosition.x + position.x;
        properties.globalPosition.y = parentProperties.globalPosition.y + position.y;
        properties.window = parentProperties.window;
    }

}
