package ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound;

import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public abstract class Sound implements Saveable {

    public String path;
    public int buffer;

    public Sound() {}

    public abstract void update();

    @Override
    public abstract String save();

    @Override
    public abstract Saveable load(String savedString);
}
