package ru.lokincompany.lokengine.sceneenvironment.components;

import org.lwjgl.util.vector.Vector4f;
import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.SpriteFramePart;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.Animation;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.Sprite;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.saveworker.ArraySaver;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

import java.util.HashMap;
import java.util.Map;

public class AnimationComponent extends Component implements Saveable {
    public float currentFrame;
    public float speedAnimation = 1;
    public boolean frameSkipping;
    private HashMap<String, Animation> animations = new HashMap<>();
    private Animation activeAnimation;
    private String activeAnimationName;
    private SpriteFramePart framePart;
    private Sprite sprite = new Sprite(null, 0, 1, 0);

    public AnimationComponent() {
        framePart = new SpriteFramePart(sprite, null, Colors.white());
    }

    public Sprite getSprite() {
        return sprite;
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

    public void setActiveAnimation(String name) {
        activeAnimation = animations.get(name);
        activeAnimationName = name;
        currentFrame = 0;
    }

    public String getActiveAnimationName() {
        return activeAnimationName;
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        if (activeAnimation != null) {
            sprite.texture = activeAnimation.altasTexture;
            sprite.vertexBuffer = activeAnimation.vertexBuffer;
            currentFrame += speedAnimation * (frameSkipping ? applicationRuntime.getDeltaTime() * applicationRuntime.getSpeedEngine() : 1);

            if ((int) currentFrame > activeAnimation.uvBuffers.size() - 1) {
                currentFrame = 0;
            }

            if ((int) currentFrame < 0) {
                currentFrame = activeAnimation.uvBuffers.size() - 1;
            }

            sprite.uvBuffer = activeAnimation.uvBuffers.get((int) currentFrame);

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
            stringBuilder.append(";").append(currentFrame);

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

            speedAnimation = Float.parseFloat(data[3]);
            currentFrame = Float.parseFloat(data[4]);
        }
        return this;
    }
}
