package LokEngine.GUI.AdditionalObjects;

import LokEngine.Render.Window;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIObjectProperties {

    public Vector2i globalPosition;
    public Vector2i size;
    public Window window;

    public GUIObjectProperties(Vector2i globalPosition, Vector2i size, Window window){
        this.globalPosition = new Vector2i(globalPosition.x, globalPosition.y);
        this.size = new Vector2i(size.x,size.y);
        this.window = window;
    }

    public GUIObjectProperties(Vector2i globalPosition, Vector2i size){
        this(globalPosition, size,null);
    }
    public GUIObjectProperties(){
        this(new Vector2i(), new Vector2i(),null);
    }
}
