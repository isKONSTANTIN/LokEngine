package ru.lokinCompany.lokEngine.Components;

import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public abstract class Component implements Saveable {

    public abstract String getName();

    public abstract void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder);

    @Override
    public abstract String save();

    @Override
    public abstract Saveable load(String savedString);
}
