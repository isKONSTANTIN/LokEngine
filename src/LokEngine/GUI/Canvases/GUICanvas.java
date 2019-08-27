package LokEngine.GUI.Canvases;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.Vector;

public class GUICanvas extends GUIObject {

    Vector<GUIObject> objects = new Vector<>();

    public GUICanvas(Vector2i position, Vector2i size) {
        super(position, size);
    }

    public int addObject(GUIObject object){
        objects.add(object);
        return objects.size()-1;
    }

    public void removeObject(int id){
        objects.remove(id);
    }
    public GUIObject getObject(int id){
        return objects.get(id);
    }

    @Override
    public void update(){
        for (GUIObject object : objects) {
            if (!object.hidden)
                object.update();
        }
    }
}
