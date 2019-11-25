package ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound;

import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public class Sound implements Saveable {

    public String path;
    public int buffer;

    public Sound() {}

    @Override
    public String save() {
        return "null";
    }

    @Override
    public Saveable load(String savedString) {
        return this;
    }
}
