package LokEngine.GUI;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Render.Window;
import java.util.HashMap;
import java.util.Vector;

public class Canvas {

    Vector<GUIObject> objects = new Vector<>();

    public void update(){
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update();
        }
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
}
