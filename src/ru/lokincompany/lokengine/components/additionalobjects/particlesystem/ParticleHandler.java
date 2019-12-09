package ru.lokincompany.lokengine.components.additionalobjects.particlesystem;

import ru.lokincompany.lokengine.components.ParticleSystemComponent;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;

public interface ParticleHandler {
    Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime);

    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
