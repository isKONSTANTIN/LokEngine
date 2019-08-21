package LokEngine.Loaders;

import LokEngine.Tools.Utilities.Sound;
import LokEngine.Tools.Utilities.SoundType;
import LokEngine.Tools.Utilities.WaveData;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class SoundLoader {

    private static HashMap<String, Sound> loadedSounds = new HashMap<>();

    public static Sound loadSound(String path, SoundType soundType){

        if (loadedSounds.containsKey(path)){
            return loadedSounds.get(path);
        }

        Sound sound = new Sound();
        sound.buffer = AL10.alGenBuffers();

        try {
            if (soundType == SoundType.WAV){
                WaveData waveData;
                if (path.charAt(0) == '#'){
                    waveData = WaveData.create(SoundLoader.class.getResourceAsStream(path.substring(1)));
                }else{
                    waveData = WaveData.create(new FileInputStream(path));
                }
                AL10.alBufferData(sound.buffer,waveData.format,waveData.data,waveData.samplerate);
                waveData.dispose();
            }
        }catch (Exception e) {

        }

        loadedSounds.put(path, sound);
        return sound;
    }

    public void unloadSound(Sound sound){
        AL10.alDeleteBuffers(sound.buffer);
    }

}