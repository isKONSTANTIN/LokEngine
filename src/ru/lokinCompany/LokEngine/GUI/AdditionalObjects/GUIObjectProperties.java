package ru.lokinCompany.LokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.LokEngine.Render.Window.Window;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;

public class GUIObjectProperties {

    public Vector2i globalPosition;
    public Vector2i size;
    public Window window;
    public MouseRaycastStatus mouseRaycastStatus;

    public GUIObjectProperties(Vector2i globalPosition, Vector2i size, Window window, MouseRaycastStatus mouseRaycastStatus) {
        this.globalPosition = new Vector2i(globalPosition.x, globalPosition.y);
        this.size = new Vector2i(size.x, size.y);
        this.window = window;
        this.mouseRaycastStatus = mouseRaycastStatus;
    }

    public GUIObjectProperties(Vector2i globalPosition, Vector2i size, Window window) {
        this(globalPosition, size, window, null);
    }

    public GUIObjectProperties(Vector2i globalPosition, Vector2i size) {
        this(globalPosition, size, null);
    }

    public GUIObjectProperties() {
        this(new Vector2i(), new Vector2i());
    }
}
