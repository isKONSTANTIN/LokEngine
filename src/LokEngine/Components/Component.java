package LokEngine.Components;

import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.SaveWorker.Saveable;

public class Component implements Saveable {

    public String getName(){
        return "Component";
    }

    public void update(SceneObject source){}

    @Override
    public String save() {
        return "";
    }

    @Override
    public Saveable load(String savedString) {
        return this;
    }
}
