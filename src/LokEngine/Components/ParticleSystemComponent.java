package LokEngine.Components;

import LokEngine.Components.AdditionalObjects.ParticleSystem.DefaultParticleHandler;
import LokEngine.Components.AdditionalObjects.ParticleSystem.Particle;
import LokEngine.Components.AdditionalObjects.ParticleSystem.ParticleHandler;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Frame.FrameParts.ParticleSystemFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.ApplicationRuntime;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;

public class ParticleSystemComponent extends Component {

    ParticleSystemFramePart framePart;
    ArrayList<Particle> particlesList = new ArrayList<>();
    ParticleHandler particleHandler;

    private Vector2f sourcePosition = new Vector2f();

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
        if (partsBuilder != null)
            partsBuilder.addPart(framePart);
    }

}
