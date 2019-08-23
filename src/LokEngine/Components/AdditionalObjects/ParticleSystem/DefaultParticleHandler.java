package LokEngine.Components.AdditionalObjects.ParticleSystem;

import LokEngine.Components.ParticleSystemComponent;
import LokEngine.Tools.RuntimeFields;

public class DefaultParticleHandler implements ParticleHandler{
    @Override
    public Particle processParticle(Particle particle) {

        particle.lifeTime -= RuntimeFields.getDeltaTime();
        particle.positionX += particle.speedX;
        particle.positionY += particle.speedY;
        particle.size = particle.lifeTime / 600f;

        return particle.lifeTime <= 0 ? null : particle;
    }

    @Override
    public Particle createNewParticle(ParticleSystemComponent particleSystemComponent) {
        Particle particle = new Particle(particleSystemComponent.sourcePosition,1, 60 * 10);

        particle.speedX = (float)(Math.random() - 0.5f) / 100f;
        particle.speedY = (float)(Math.random() - 0.5f) / 100f;
        return particle;
    }
}
