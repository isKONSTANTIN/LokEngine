package LokEngine.GUI;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Render.Window;

import java.util.Vector;

public class Canvas {
    Window window;
    Vector<GUIObject> objects = new Vector<>();

    public void update(){
        for (int i = 0; i < objects.size(); i++){
            objects.get(i).update();
        }
    }
}
