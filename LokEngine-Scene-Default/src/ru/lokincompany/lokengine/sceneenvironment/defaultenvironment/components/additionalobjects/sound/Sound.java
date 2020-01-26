package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.sound;

import org.lwjgl.openal.AL10;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

import java.util.HashMap;

public abstract class Sound implements Saveable {
    protected static HashMap<String, Sound> loadedSounds = new HashMap<>();

    public String path;
    public int buffer;

    public Sound() {
    }

    public abstract void update();

    public void unload() {
        AL10.alDeleteBuffers(buffer);
    }

    @Override
    public abstract String save();

    @Override
    public abstract Saveable load(String savedString);
}
