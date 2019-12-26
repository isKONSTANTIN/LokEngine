package ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.particlesystem;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.sceneenvironment.components.ParticleSystemComponent;

public class DefaultParticleHandler implements ParticleHandler {
    @Override
    public Particle processParticle(Particle particle, ApplicationRuntime applicationRuntime) {

        particle.lifeTime -= applicationRuntime.getDeltaTime();
        particle.positionX += particle.speedX * applicationRuntime.getDeltaTime();
        particle.positionY += particle.speedY * applicationRuntime.getDeltaTime();
        particle.size = particle.lifeTime / 600f;

        return particle.lifeTime <= 0 ? null : particle;
    }

    @Override
    public Particle createNewParticle(ParticleSystemComponent particleSystemComponent) {
        Particle particle = new Particle(particleSystemComponent.getSourcePosition(), 1, 60 * 10);

        particle.speedX = (float) (Math.random() - 0.5f) / 100f;
        particle.speedY = (float) (Math.random() - 0.5f) / 100f;
        return particle;
    }
}
