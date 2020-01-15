package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.particlesystem;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.ParticleSystemComponent;

public interface ParticleHandler {
    Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime);

    Particle createNewParticle(ParticleSystemComponent particleSystemComponent);
}
