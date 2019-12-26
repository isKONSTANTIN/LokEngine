package ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.sound;

import ru.lokincompany.lokengine.loaders.SoundLoader;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class RawWavSound extends Sound {
    public RawWavSound() {
    }

    public RawWavSound(String path) {
        this.path = path;
    }

    @Override
    public void update() {
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        try {
            buffer = SoundLoader.loadWAV(savedString).buffer;
        } catch (Exception e) {
            Logger.warning("Fail load raw wav!", "LokEngine_RawWavSound");
            e.printStackTrace();
        }
        return this;
    }

}
