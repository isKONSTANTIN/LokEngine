package ru.lokinCompany.lokEngine.Components;

import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public class Component implements Saveable {

    public String getName() {
        return "Component";
    }

    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
    }

    @Override
    public String save() {
        return "";
    }

    @Override
    public Saveable load(String savedString) {
        return this;
    }
}
