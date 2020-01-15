package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.sound;

import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public abstract class Sound implements Saveable {

    public String path;
    public int buffer;

    public Sound() {
    }

    public abstract void update();

    @Override
    public abstract String save();

    @Override
    public abstract Saveable load(String savedString);
}
