package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.SoundLoader;
import LokEngine.Tools.SaveWorker.Saveable;
import LokEngine.Tools.Utilities.SoundType;

public class Sound implements Saveable {

    public int buffer;

    @Override
    public String save() {
        return SoundLoader.getPath(this);
    }

    @Override
    public Saveable load(String savedString) {
        buffer = SoundLoader.loadSound(savedString, SoundType.WAV).buffer;
        return this;
    }
}
