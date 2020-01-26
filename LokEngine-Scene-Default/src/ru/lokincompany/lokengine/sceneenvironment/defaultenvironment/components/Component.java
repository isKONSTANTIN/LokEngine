package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public abstract class Component implements Saveable {
    public abstract void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder);

    @Override
    public abstract String save();

    @Override
    public abstract Saveable load(String savedString);
}
