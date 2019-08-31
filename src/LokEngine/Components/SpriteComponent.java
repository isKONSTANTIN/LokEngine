package LokEngine.Components;

import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Frame.FrameParts.SpriteFramePart;
import LokEngine.Render.Shader;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector4f;

import java.io.IOException;

public class SpriteComponent extends Component implements Saveable {

    private Sprite sprite;
    private SpriteFramePart framePart;

    @Override
    public String getName(){
        return "Sprite Component";
    }

    public SpriteComponent(){}

    public SpriteComponent(String path){
        try {
            sprite = SpriteLoader.loadSprite(path);
        } catch (Exception e) {
            sprite = DefaultFields.unknownSprite;
        }
        framePart = new SpriteFramePart(sprite);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public SpriteComponent(String path, Shader customShader){
        try {
            sprite = SpriteLoader.loadSprite(path,1, customShader);
        } catch (IOException e) {
            sprite = DefaultFields.unknownSprite;
        }
        framePart = new SpriteFramePart(sprite);
    }

    public SpriteComponent(Sprite sprite){
        if (sprite != null){
            this.sprite = sprite;
        }else {
            this.sprite = DefaultFields.unknownSprite;
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

        return this;
    }
}
