package ru.lokinCompany.LokEngine.Components.AdditionalObjects.ParticleSystem;

import ru.lokinCompany.LokEngine.Components.ParticleSystemComponent;
import ru.lokinCompany.LokEngine.Tools.ApplicationRuntime;

public interface ParticleHandler {
    Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime);

    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
