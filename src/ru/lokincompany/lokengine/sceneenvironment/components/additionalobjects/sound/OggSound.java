package ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.sound;

import ru.lokincompany.lokengine.loaders.SoundLoader;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;


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
