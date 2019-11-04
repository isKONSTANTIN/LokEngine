package ru.lokinCompany.LokEngine.Components;

import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.LokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.LokEngine.Tools.SaveWorker.Saveable;

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
