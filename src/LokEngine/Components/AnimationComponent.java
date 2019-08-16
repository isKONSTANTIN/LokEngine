package LokEngine.Components;

import LokEngine.Components.AdditionalObjects.Animation;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Frame.FrameParts.SpriteFramePart;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Logger;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.util.vector.Vector4f;

import java.util.HashMap;

public class AnimationComponent extends Component {

    private HashMap<String, Animation> animations = new HashMap<>();

    private Animation activeAnimation;
    private SpriteFramePart framePart;
    private Sprite sprite = new Sprite(null,0,0);

    public Sprite getSprite(){
        return sprite;
    }

    public AnimationComponent(){
        framePart = new SpriteFramePart(sprite);
    }

    @Override
    public String getName(){
        return "Animation Component";
    }

    public void addAnimation(Animation animation, String name){
        animations.put(name,animation);
        setActiveAnimation(name);
    }

    public Animation getAnimation(String name){
        return animations.get(name);
    }

    public Animation getActiveAnimation(){
        return activeAnimation;
    }

    public void setActiveAnimation(String name){
        activeAnimation = animations.get(name);
    }

    @Override
    public void update(SceneObject source){
        if (activeAnimation != null){
            sprite.texture = activeAnimation.altasTexture;
            sprite.uvBuffer = activeAnimation.uvBuffers.get((int)activeAnimation.currectFrame);
            sprite.vertexBuffer = activeAnimation.vertexBuffer;
            activeAnimation.currectFrame += activeAnimation.speedAnimation;

            if ((int)activeAnimation.currectFrame > activeAnimation.uvBuffers.size()-1){
                activeAnimation.currectFrame = 0;
            }

            framePart.position = new Vector4f(source.position.x,source.position.y,source.renderPriority,source.rollRotation);
            RuntimeFields.frameBuilder.addPart(framePart);
        }
    }

}
