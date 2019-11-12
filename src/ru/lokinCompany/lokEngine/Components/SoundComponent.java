package ru.lokinCompany.lokEngine.Components;

import org.lwjgl.openal.AL10;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sound.Sound;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

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
    public String getName() {
        return "Sound Component";
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        sound.update();
        AL10.alSource3f(this.source, AL10.AL_POSITION, source.position.x, source.position.y, 0);
    }

    @Override
    public String save() {
        return sound.save();
    }

    @Override
    public Saveable load(String savedString) {
        SoundComponent loadedSoundComponent = new SoundComponent((Sound) new Sound().load(savedString));

        this.sound = loadedSoundComponent.sound;
        this.source = loadedSoundComponent.source;

        return this;
    }
}
