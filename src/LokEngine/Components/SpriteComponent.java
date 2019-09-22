package LokEngine.Components;

import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Frame.FrameParts.SpriteFramePart;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector4f;

public class SpriteComponent extends Component implements Saveable {

    private Sprite sprite;
    private SpriteFramePart framePart;

    @Override
    public String getName(){
        return "Sprite Component";
    }

    public SpriteComponent(){}

    public SpriteComponent(String path){
        sprite = SpriteLoader.loadSprite(path);
        framePart = new SpriteFramePart(sprite);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
        framePart.sprite = sprite;
    }

    public void setSprite(String path){
        sprite = SpriteLoader.loadSprite(path);

        if (sprite.texture.buffer == DefaultFields.unknownTexture.buffer){
            sprite.size = 100;
        }

        framePart.sprite = sprite;
    }

    public SpriteComponent(String path, Shader customShader){
        sprite = SpriteLoader.loadSprite(path,1, customShader);

        if (sprite.texture.buffer == DefaultFields.unknownTexture.buffer){
            sprite.size = 100;
        }

        framePart = new SpriteFramePart(sprite);
    }

    public SpriteComponent(Sprite sprite){
        if (sprite != null){
            this.sprite = sprite;

            if (sprite.texture.buffer == DefaultFields.unknownTexture.buffer){
                sprite.size = 100;
            }
        }
        framePart = new SpriteFramePart(sprite);
    }

    @Override
    public void update(SceneObject source){
        framePart.position = new Vector4f(source.position.x,source.position.y,source.renderPriority,source.rollRotation);
        RuntimeFields.getFrameBuilder().addPart(framePart);
    }

    @Override
    public String save() {
        return sprite.save();
    }

    @Override
    public Saveable load(String savedString) {
        SpriteComponent loadedSpriteComponent = new SpriteComponent((Sprite)new Sprite().load(savedString));

        this.framePart = loadedSpriteComponent.framePart;
        this.sprite = loadedSpriteComponent.sprite;
        if (sprite.texture.buffer == DefaultFields.unknownTexture.buffer){
            sprite.size = 100;
        }
        return this;
    }
}
