package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIObject {
    Vector2i position;
    Vector2i size;

    public boolean hidden;

    public void setPosition(Vector2i position){ this.position = position; }
    public Vector2i getPosition(){ return position; }

    public void setSize(Vector2i size){ this.size = size; }
    public Vector2i getSize(){ return size; }

    public GUIObject(Vector2i position, Vector2i size){
        this.position = position;
        this.size = size;
    }

    public void update(PartsBuilder partsBuilder, Vector2i globalSourcePos){ }

}
