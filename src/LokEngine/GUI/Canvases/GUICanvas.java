package LokEngine.GUI.Canvases;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.Vector;

public class GUICanvas extends GUIObject {

    Vector<GUIObject> objects = new Vector<>();

    public GUICanvas(Vector2i position, Vector2i size) {
        super(position, size);
    }

    public int putObject(GUIObject object){
        objects.add(object);
        return objects.size()-1;
    }

    public void removeObject(String name){
        objects.remove(name);
    }
    public GUIObject getObject(int id){
        return objects.get(id);
    }

    @Override
    public void update(){
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update();
        }
    }
}
