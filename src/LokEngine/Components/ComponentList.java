package LokEngine.Components;

import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.SaveWorker.ArraySaver;
import LokEngine.Tools.SaveWorker.Saveable;
import LokEngine.Tools.SaveWorker.SubclassSaver;

import java.util.ArrayList;

public class ComponentList implements Saveable {

    private ArrayList<Component> components = new ArrayList<>();

    public int add(Component component){
        components.add(component);
        return components.size()-1;
    }

    public void update(SceneObject source){
        for (Component component : components) {
            component.update(source);
        }
    }

    public Component get(int id){
        return components.get(id);
    }

    public Component get(String name){
        for (Component component : components) {
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }

    @Override
    public String save() {
        ArraySaver arraySaver = new ArraySaver(SubclassSaver.class);

        for (Component component : components){
            arraySaver.arrayList.add(new SubclassSaver(component));
        }

        return arraySaver.save();
    }

    @Override
    public Saveable load(String savedString) {
        ArraySaver arraySaver = (ArraySaver)new ArraySaver(SubclassSaver.class).load(savedString);

        for (Saveable loadedComponent : arraySaver.arrayList){
            components.add((Component)((SubclassSaver)loadedComponent).saveableObject);
        }

        return this;
    }
}
