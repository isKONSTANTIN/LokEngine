package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.SoundLoader;
import LokEngine.Tools.SaveWorker.Saveable;
import LokEngine.Tools.Utilities.SoundType;

public class Sound implements Saveable {

    public int buffer;
    public String path;

    public Sound() {
    }

    public Sound(String path, int buffer) {
        this.path = path;
        this.buffer = buffer;
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
