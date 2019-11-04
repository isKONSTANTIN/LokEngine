package ru.lokinCompany.LokEngine.Components;

import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.LokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.LokEngine.Tools.SaveWorker.ArraySaver;
import ru.lokinCompany.LokEngine.Tools.SaveWorker.Saveable;
import ru.lokinCompany.LokEngine.Tools.SaveWorker.SubclassSaver;

import java.util.ArrayList;

public class ComponentList implements Saveable {

    private ArrayList<Component> components = new ArrayList<>();

    public int add(Component component) {
        components.add(component);
        return components.size() - 1;
    }

    public int getSize() {
        return components.size();
    }

    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        for (Component component : components) {
            component.update(source, applicationRuntime, partsBuilder);
        }
    }

    public void remove(int id) {
        components.remove(id);
    }

    public void remove(String name) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getName().equals(name)) {
                remove(i);
                break;
            }
        }
    }

    public Component get(int id) {
        return components.get(id);
    }

    public Component get(String name) {
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

        for (Component component : components) {
            arraySaver.arrayList.add(new SubclassSaver(component));
        }

        return arraySaver.save();
    }

    @Override
    public Saveable load(String savedString) {
        ArraySaver arraySaver = (ArraySaver) new ArraySaver(SubclassSaver.class).load(savedString);

        for (Saveable loadedComponent : arraySaver.arrayList) {
            components.add((Component) ((SubclassSaver) loadedComponent).saveableObject);
        }

        return this;
    }
}
