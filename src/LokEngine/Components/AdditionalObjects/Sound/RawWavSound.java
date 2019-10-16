package LokEngine.Components.AdditionalObjects.Sound;

import LokEngine.Loaders.SoundLoader;
import LokEngine.Tools.SaveWorker.Saveable;
import LokEngine.Tools.Utilities.SoundType;

public class RawWavSound extends Sound {

    public String path;

    public RawWavSound() {}

    public RawWavSound(String path) {
        this.path = path;
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        buffer = SoundLoader.loadSound(savedString, SoundType.WAV).buffer;
        return this;
    }

}
