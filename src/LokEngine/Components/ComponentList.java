package LokEngine.Components;

import LokEngine.SceneEnvironment.SceneObject;

import java.util.Vector;

public class ComponentList {

    public Vector<Component> components;

    public void updateComponents(SceneObject source){
        for (int i = 0; i < components.size(); i++){
            components.get(i).update(source);
        }
    }

}
