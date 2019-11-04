package ru.lokinCompany.lokEngine.Loaders;

import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound.OggSound;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound.RawWavSound;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound.Sound;
import ru.lokinCompany.lokEngine.Tools.Logger;
import ru.lokinCompany.lokEngine.Tools.Utilities.WaveData;
import org.apache.commons.io.IOUtils;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;

public class SoundLoader {

    private static HashMap<String, Sound> loadedSounds = new HashMap<>();

    public static OggSound loadOGG(String path) throws IOException {
        if (loadedSounds.containsKey(path)) {
            return (OggSound) loadedSounds.get(path);
        }

        String endPath = path;
        File tempFile = null;
        if (path.charAt(0) == '#') {
            try {
                tempFile = File.createTempFile("LokEngine_SoundTemp_" + loadedSounds.size(), ".tmp");
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(IOUtils.resourceToByteArray(path.substring(1)));
                fos.close();
                endPath = tempFile.getAbsolutePath();
            } catch (Exception e) {
                Logger.warning("Failed load ogg file!", "LokEngine_SoundLoader");
                Logger.printException(e);
                return null;
            }
        }
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);
        ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(endPath, channelsBuffer, sampleRateBuffer);
        if (tempFile != null)
            tempFile.delete();
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        stackPop();
        stackPop();

        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        if (rawAudioBuffer != null) {
            OggSound sound = new OggSound();
            sound.buffer = AL10.alGenBuffers();

            alBufferData(sound.buffer, format, rawAudioBuffer, sampleRate);

            loadedSounds.put(path, sound);
            return sound;
        } else {
            throw new IOException("Fail decode ogg file!");
        }
    }

    public static RawWavSound loadWAV(String path) throws IOException, UnsupportedAudioFileException {
        if (loadedSounds.containsKey(path)) {
            return (RawWavSound)loadedSounds.get(path);
        }

        WaveData waveData;

        if (path.charAt(0) == '#') {
            waveData = WaveData.create(SoundLoader.class.getResourceAsStream(path.substring(1)));
        } else {
            waveData = WaveData.create(new FileInputStream(path));
        }
        RawWavSound sound = new RawWavSound();
        sound.buffer = AL10.alGenBuffers();

        alBufferData(sound.buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();

        loadedSounds.put(path, sound);
        return sound;
    }

    public void unloadSound(Sound sound) {
        AL10.alDeleteBuffers(sound.buffer);
    }

}