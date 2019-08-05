package LokEngine.GUI.GUIObjects;

import LokEngine.Tools.Utilities.Vector2i;

public class GUIObject {
    public Vector2i position;
    public Vector2i size;
    public String name;

    public GUIObject(Vector2i position, Vector2i size){
        this.position = position;
        this.size = size;
    }

    public void update(){ }

}
