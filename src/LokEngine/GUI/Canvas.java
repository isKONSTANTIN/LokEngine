package LokEngine.GUI;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Render.Window;

import java.util.HashMap;
import java.util.Vector;

public class Canvas {
    HashMap<String, GUIObject> objects = new HashMap<>();

    public void update(){
        for (GUIObject object : objects.values()) {
            object.update();
        }
    }

    public void putObject(GUIObject object, String name){
        objects.put(name, object);
    }

    public void replaceObject(GUIObject object, String name){
        objects.replace(name, object);
    }

    public void removeObject(String name){
        objects.remove(name);
    }

}
