package LokEngine.Components;

import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Frame.FrameParts.SpriteFramePart;
import LokEngine.Render.Shader;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.util.vector.Vector4f;

import java.io.IOException;

public class SpriteComponent extends Component {

    private Sprite sprite;
    private SpriteFramePart framePart;

    @Override
    public String getName(){
        return "Sprite Component";
    }

    public SpriteComponent(String path){
        try {
            sprite = SpriteLoader.loadSprite(path);
        } catch (Exception e) {
            sprite = DefaultFields.unknownSprite;
        }
        framePart = new SpriteFramePart(sprite);
    }

    public SpriteComponent(String path, Shader customShader){
        try {
            sprite = SpriteLoader.loadSprite(path, customShader);
        } catch (IOException e) {
            sprite = DefaultFields.unknownSprite;
        }
        framePart = new SpriteFramePart(sprite);
    }

    @Override
    public void update(SceneObject source){
        framePart.position = new Vector4f(source.position.x,source.position.y,source.renderPriority,source.rollRotation);
        RuntimeFields.frameBuilder.addPart(framePart);
    }

}
