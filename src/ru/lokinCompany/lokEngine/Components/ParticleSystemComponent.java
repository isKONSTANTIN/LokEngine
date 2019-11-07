package ru.lokinCompany.lokEngine.Components;

import ru.lokinCompany.lokEngine.Components.AdditionalObjects.ParticleSystem.DefaultParticleHandler;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.ParticleSystem.Particle;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.ParticleSystem.ParticleHandler;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sprite;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.ParticleSystemFramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Render.Shader;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;

public class ParticleSystemComponent extends Component {

    ParticleSystemFramePart framePart;
    ArrayList<Particle> particlesList = new ArrayList<>();
    ParticleHandler particleHandler;

    private Vector2f sourcePosition = new Vector2f();

    public boolean staticParticles;
    private boolean staticInited;

    public Vector2f getSourcePosition() {
        return new Vector2f(sourcePosition.x, sourcePosition.y);
    }

    @Override
    public String getName() {
        return "Particle System Component";
    }

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

        if (!staticParticles || !staticInited){
            ArrayList<Float> positions = new ArrayList<>();
            ArrayList<Float> sizes = new ArrayList<>();

            for (Iterator<Particle> iter = particlesList.iterator(); iter.hasNext(); ) {
                Particle particle = iter.next();

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

}
