package ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound;

import ru.lokinCompany.lokEngine.Loaders.SoundLoader;
import ru.lokinCompany.lokEngine.Tools.Logger;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public class OggSound extends Sound {

    public OggSound() {
    }

    public OggSound(String path) {
        this.path = path;
    }

    public void update() {
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        try {
            buffer = SoundLoader.loadOGG(savedString).buffer;
        } catch (Exception e) {
            Logger.warning("Fail load raw wav!", "LokEngine_RawWavSound");
            e.printStackTrace();
        }
        return this;
    }

}
