package ru.lokincompany.lokengine.sceneenvironment.components;

import org.lwjgl.openal.AL10;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.sound.Sound;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.saveworker.SubclassSaver;

public class SoundComponent extends Component implements Saveable {

    public Sound sound;
    private int source;

    public SoundComponent() {
    }

    public SoundComponent(Sound sound) {
        source = AL10.alGenSources();

        AL10.alSourcef(this.source, AL10.AL_GAIN, 1);
        AL10.alSourcef(this.source, AL10.AL_PITCH, 1);
        AL10.alSource3f(this.source, AL10.AL_POSITION, 0, 0, 0);

        this.sound = sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setPitch(float pitch) {
        AL10.alSourcef(this.source, AL10.AL_PITCH, pitch);
    }

    public void play() {
        AL10.alSourcei(source, AL10.AL_BUFFER, sound.buffer);
        AL10.alSourcePlay(source);
    }

    public void setLooping(boolean loop) {
        AL10.alSourcei(source, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void setVolume(float volume) {
        AL10.alSourcef(source, AL10.AL_GAIN, volume);
    }

    public void pause() {
        AL10.alSourcePause(source);
    }

    public void stop() {
        AL10.alSourceStop(source);
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        if (sound != null)
            sound.update();
        AL10.alSource3f(this.source, AL10.AL_POSITION, source.position.x, source.position.y, 0);
    }

    @Override
    public String save() {
        if (sound == null) return "";

        SubclassSaver subclassSaver = new SubclassSaver(sound);
        return subclassSaver.save();
    }

    @Override
    public Saveable load(String savedString) {
        if (savedString.equals("")) return this;

        SubclassSaver subclassSaver = new SubclassSaver();
        SoundComponent loadedSoundComponent = new SoundComponent((Sound) ((SubclassSaver) subclassSaver.load(savedString)).saveableObject);

        this.sound = loadedSoundComponent.sound;
        this.source = loadedSoundComponent.source;

        return this;
    }
}
