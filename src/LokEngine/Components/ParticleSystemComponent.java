package LokEngine.Components;

import LokEngine.Components.AdditionalObjects.ParticleSystem.DefaultParticleHandler;
import LokEngine.Components.AdditionalObjects.ParticleSystem.Particle;
import LokEngine.Components.AdditionalObjects.ParticleSystem.ParticleHandler;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Frame.FrameParts.ParticleSystemFramePart;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;

public class ParticleSystemComponent extends Component {

    ParticleSystemFramePart framePart;
    ArrayList<Particle> particlesList = new ArrayList<>();
    ParticleHandler particleHandler;
    public Vector2f sourcePosition = new Vector2f();

    ArrayList<Float> positions = new ArrayList<>();
    ArrayList<Float> sizes = new ArrayList<>();

    public ParticleSystemComponent(Sprite spriteParticles, ParticleHandler particleHandler, Shader shader) {
        framePart = new ParticleSystemFramePart(spriteParticles, shader);
        this.particleHandler = particleHandler;
    }

    public ParticleSystemComponent(Sprite spriteParticles, ParticleHandler particleHandler) {
        this(spriteParticles, particleHandler, DefaultFields.particlesShader);
    }

    public ParticleSystemComponent(Sprite spriteParticles) {
        this(spriteParticles, new DefaultParticleHandler(), DefaultFields.particlesShader);
    }

    public void setParticleHandler(ParticleHandler handler){
        this.particleHandler = handler;
    }

    public void setParticlesSprite(Sprite particlesSprite){ framePart.setSourceSprite(particlesSprite); }

    public void genNewParticle(){
        particlesList.add(particleHandler.createNewParticle(this));
    }

    @Override
    public void update(SceneObject source){
        sourcePosition = source.position;

        positions.clear();
        sizes.clear();

        for (Iterator<Particle> iter = particlesList.iterator(); iter.hasNext();) {
            Particle particle = iter.next();

            Particle updatedParticle = particleHandler.processParticle(particle);
            if (updatedParticle != null){
                positions.add(updatedParticle.positionX);
                positions.add(updatedParticle.positionY);
                sizes.add(updatedParticle.size);
            }
        }

        if (sizes.size() == 0){
            particlesList.clear();
        }

        framePart.update(positions, sizes);
        RuntimeFields.getFrameBuilder().addPart(framePart);
    }

}
