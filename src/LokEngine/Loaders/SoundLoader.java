package LokEngine.Loaders;

import LokEngine.Components.AdditionalObjects.Sound.RawWavSound;
import LokEngine.Components.AdditionalObjects.Sound.Sound;
import LokEngine.Tools.Utilities.WaveData;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class SoundLoader {

    private static HashMap<String, Sound> loadedSounds = new HashMap<>();

    public static RawWavSound loadWAV(String path) throws IOException, UnsupportedAudioFileException {
        if (loadedSounds.containsKey(path)) {
            return (RawWavSound)loadedSounds.get(path);
        }

        RawWavSound sound = new RawWavSound();
        sound.buffer = AL10.alGenBuffers();
        
        WaveData waveData;

        if (path.charAt(0) == '#') {
            waveData = WaveData.create(SoundLoader.class.getResourceAsStream(path.substring(1)));
        } else {
            waveData = WaveData.create(new FileInputStream(path));
        }
        AL10.alBufferData(sound.buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();

        loadedSounds.put(path, sound);
        return sound;
    }

    public void unloadSound(Sound sound) {
        AL10.alDeleteBuffers(sound.buffer);
    }

}