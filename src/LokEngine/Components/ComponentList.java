package LokEngine.Components;

import LokEngine.SceneEnvironment.SceneObject;

import java.util.Vector;

public class ComponentList {

    public Vector<Component> components;

    public void updateComponents(SceneObject source){
        for (Component component : components) {
            component.update(source);
        }
    }

    public Component getComponent(String name){
        for (Component component : components) {
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }

}
