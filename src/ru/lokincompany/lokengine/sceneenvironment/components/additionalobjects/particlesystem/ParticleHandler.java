package ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.particlesystem;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.sceneenvironment.components.ParticleSystemComponent;

public interface ParticleHandler {
    Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime);

    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
