package LokEngine.Components.AdditionalObjects.ParticleSystem;

import LokEngine.Components.ParticleSystemComponent;

public interface ParticleHandler{
    Particle processParticle(Particle particle);
    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
