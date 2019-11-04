package ru.lokinCompany.lokEngine.Components.AdditionalObjects.ParticleSystem;

import ru.lokinCompany.lokEngine.Components.ParticleSystemComponent;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;

public interface ParticleHandler {
    Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime);

    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
