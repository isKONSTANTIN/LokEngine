package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.sound;

import org.lwjgl.openal.AL10;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.WaveData;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileInputStream;

import static org.lwjgl.openal.AL10.alBufferData;

public class RawWavSound extends Sound {
    public RawWavSound() {
    }

    public RawWavSound(String path) {
        loadWAV(path);
    }

    public void loadWAV(String path) {
        try {
            this.path = path;
            if (loadedSounds.containsKey(path)) {
                RawWavSound loadedSound = (RawWavSound) loadedSounds.get(path);
                buffer = loadedSound.buffer;
            }

            WaveData waveData;

            if (path.charAt(0) == '#') {
                waveData = WaveData.create(RawWavSound.class.getResourceAsStream(path.substring(1)));
            } else {
                waveData = WaveData.create(new FileInputStream(path));
            }

            buffer = AL10.alGenBuffers();

            alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
            waveData.dispose();

            loadedSounds.put(path, this);
        } catch (UnsupportedAudioFileException e) {
            Logger.warning("Unsupported audio file (not raw wav): " + path + "!", "LokEngine_RawWavSound");
            Logger.printThrowable(e);
        } catch (Throwable e) {
            Logger.warning("Fail load raw wav: " + path + "!", "LokEngine_RawWavSound");
            Logger.printThrowable(e);
        }
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
        loadWAV(savedString);
        return this;
    }

}
