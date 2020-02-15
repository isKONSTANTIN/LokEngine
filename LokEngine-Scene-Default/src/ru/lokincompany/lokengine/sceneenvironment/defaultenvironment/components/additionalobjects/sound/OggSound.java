package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.sound;

import org.apache.commons.io.IOUtils;
import org.lwjgl.openal.AL10;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;


public class OggSound extends Sound {
    public OggSound() {
    }

    public OggSound(String path) {
        loadOGG(path);
    }

    public void update() {
    }

    public void loadOGG(String path) {
        this.path = path;

        if (loadedSounds.containsKey(path)) {
            OggSound loadedOGG = (OggSound) loadedSounds.get(path);
            buffer = loadedOGG.buffer;
            return;
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
            } catch (Throwable e) {
                Logger.warning("Failed load ogg file!", "LokEngine_OggSound");
                Logger.printThrowable(e);
                return;
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

        int format = channels == 1 ? AL_FORMAT_MONO16 : (channels == 2 ? AL_FORMAT_STEREO16 : -1);

        if (rawAudioBuffer == null) {
            Logger.warning("Failed decode ogg file: " + path + "!", "LokEngine_OggSound");
            return;
        }

        buffer = AL10.alGenBuffers();

        alBufferData(buffer, format, rawAudioBuffer, sampleRate);

        loadedSounds.put(path, this);
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        loadOGG(savedString);
        return this;
    }

}
