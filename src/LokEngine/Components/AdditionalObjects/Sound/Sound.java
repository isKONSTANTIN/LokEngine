package LokEngine.Components.AdditionalObjects.Sound;

import LokEngine.Tools.SaveWorker.Saveable;

public class Sound implements Saveable {

    public String path;
    public int buffer;

    public Sound() {}

    public void update(){}

    @Override
    public String save(){
        return "null";
    }

    @Override
    public Saveable load(String savedString) {
        return this;
    }
}
