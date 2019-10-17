package LokEngine.Components.AdditionalObjects.Sound;

import LokEngine.Loaders.SoundLoader;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Saveable;

public class OggSound extends Sound {

    public OggSound() {}

    public OggSound(String path) {
        this.path = path;
    }

    public void update(){}

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
