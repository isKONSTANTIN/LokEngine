package ru.lokincompany.lokengine.sceneenvironment.components;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.Sprite;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.particlesystem.DefaultParticleHandler;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.particlesystem.Particle;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.particlesystem.ParticleHandler;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.ParticleSystemFramePart;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

import java.util.ArrayList;

public class ParticleSystemComponent extends Component {

    public boolean staticParticles;
    ParticleSystemFramePart framePart;
    ArrayList<Particle> particlesList = new ArrayList<>();
    ParticleHandler particleHandler;
    private Vector2f sourcePosition = new Vector2f();
    private boolean staticInited;

    public ParticleSystemComponent(Sprite spriteParticles, ParticleHandler particleHandler, Shader shader) {
        framePart = new ParticleSystemFramePart(spriteParticles, shader);
        this.particleHandler = particleHandler;
    }

    public ParticleSystemComponent(Sprite spriteParticles, ParticleHandler particleHandler) {
        this(spriteParticles, particleHandler, null);
    }

    public ParticleSystemComponent(Sprite spriteParticles) {
        this(spriteParticles, new DefaultParticleHandler(), null);
    }

    public Vector2f getSourcePosition() {
        return new Vector2f(sourcePosition.x, sourcePosition.y);
    }

    public void setParticleHandler(ParticleHandler handler) {
        this.particleHandler = handler;
    }

    public void setParticlesSprite(Sprite particlesSprite) {
        framePart.setSourceSprite(particlesSprite);
    }

    public void genNewParticle() {
        particlesList.add(particleHandler.createNewParticle(this));
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        sourcePosition = source.position;

        if (!staticParticles || !staticInited) {
            ArrayList<Float> positions = new ArrayList<>();
            ArrayList<Float> sizes = new ArrayList<>();

            for (Particle particle : particlesList) {
                Particle updatedParticle = particleHandler.processParticle(particle, applicationRuntime);
                if (updatedParticle != null) {
                    positions.add(updatedParticle.positionX);
                    positions.add(updatedParticle.positionY);
                    sizes.add(updatedParticle.size);
                }
            }

            if (sizes.size() == 0) {
                particlesList.clear();
            }

            framePart.update(positions, sizes);
            staticInited = true;
        }

        if (partsBuilder != null)
            partsBuilder.addPart(framePart);
    }

    @Override
    public String save() {
        return null;
    }

    @Override
    public Saveable load(String savedString) {
        return null;
    }

}
