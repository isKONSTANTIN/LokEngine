package ru.lokinCompany.lokEngine.Components;

import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Animation;
import ru.lokinCompany.lokEngine.Components.AdditionalObjects.Sprite;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.SpriteFramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.ArraySaver;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector4f;

import java.util.HashMap;
import java.util.Map;

public class AnimationComponent extends Component implements Saveable {
    private HashMap<String, Animation> animations = new HashMap<>();
    private Animation activeAnimation;
    private String activeAnimationName;
    private SpriteFramePart framePart;

    public float currectFrame;
    public float speedAnimation = 1;
    public boolean frameSkipping;

    private Sprite sprite = new Sprite(null, 0, 1, 0);

    public Sprite getSprite() {
        return sprite;
    }

    public AnimationComponent() {
        framePart = new SpriteFramePart(sprite, null);
    }

    @Override
    public String getName() {
        return "Animation Component";
    }

    public void addAnimation(Animation animation, String name) {
        animations.put(name, animation);
        setActiveAnimation(name);
    }

    public Animation getAnimation(String name) {
        return animations.get(name);
    }

    public Animation getActiveAnimation() {
        return activeAnimation;
    }

    public String getActiveAnimationName(){ return activeAnimationName; }

    public void setActiveAnimation(String name) {
        activeAnimation = animations.get(name);
        activeAnimationName = name;
        currectFrame = 0;
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        if (activeAnimation != null) {
            sprite.texture = activeAnimation.altasTexture;
            sprite.vertexBuffer = activeAnimation.vertexBuffer;
            currectFrame += speedAnimation * (frameSkipping ? applicationRuntime.getDeltaTime() * applicationRuntime.getSpeedEngine() : 1);

            if ((int) currectFrame > activeAnimation.uvBuffers.size() - 1) {
                currectFrame = 0;
            }

            if ((int) currectFrame < 0) {
                currectFrame = activeAnimation.uvBuffers.size() - 1;
            }

            sprite.uvBuffer = activeAnimation.uvBuffers.get((int) currectFrame);

            framePart.position = new Vector4f(source.position.x, source.position.y, source.renderPriority, source.rollRotation);
            if (partsBuilder != null)
                partsBuilder.addPart(framePart);
        }
    }


    @Override
    public String save() {
        if (animations.size() > 0) {
            ArraySaver arraySaver = new ArraySaver(Animation.class);
            StringBuilder stringBuilder = new StringBuilder();

            for (Map.Entry<String, Animation> entry : animations.entrySet()) {
                String key = entry.getKey();
                Animation value = entry.getValue();

                arraySaver.arrayList.add(value);
                stringBuilder.append(key).append(",");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(";").append(activeAnimationName);
            stringBuilder.append(";").append(arraySaver.save());
            stringBuilder.append(";").append(speedAnimation);
            stringBuilder.append(";").append(currectFrame);

            return stringBuilder.toString();
        }
        return "";
    }

    @Override
    public Saveable load(String savedString) {
        if (!savedString.equals("")) {
            String[] data = savedString.split(";");
            ArraySaver arraySaver = (ArraySaver) new ArraySaver(Animation.class).load(data[2]);
            String[] animationNames = data[0].split(",");

            for (int i = 0; i < animationNames.length; i++) {
                animations.put(animationNames[i], (Animation) arraySaver.arrayList.get(i));
            }

            setActiveAnimation(data[1]);

            speedAnimation = Float.valueOf(data[3]);
            currectFrame = Float.valueOf(data[4]);
        }
        return this;
    }
}
