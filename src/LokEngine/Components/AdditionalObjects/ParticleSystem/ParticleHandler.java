package LokEngine.Components.AdditionalObjects.ParticleSystem;

import LokEngine.Components.ParticleSystemComponent;
import LokEngine.Tools.ApplicationRuntime;

public interface ParticleHandler{
    Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime);
    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
