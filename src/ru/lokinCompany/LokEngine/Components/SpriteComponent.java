package ru.lokinCompany.LokEngine.Components;

import ru.lokinCompany.LokEngine.Components.AdditionalObjects.Sprite;
import ru.lokinCompany.LokEngine.Loaders.SpriteLoader;
import ru.lokinCompany.LokEngine.Render.Frame.FrameParts.SpriteFramePart;
import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.Render.Shader;
import ru.lokinCompany.LokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.LokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector4f;

public class SpriteComponent extends Component implements Saveable {

    private SpriteFramePart framePart;

    @Override
    public String getName() {
        return "Sprite Component";
    }

    public SpriteComponent() {
    }

    public SpriteComponent(String path) {
        this(path, null);
    }

    public Sprite getSprite() {
        return framePart.sprite;
    }

    public void setSprite(Sprite sprite) {
        framePart.sprite = sprite;
    }

    public void setSprite(String path) {
        framePart.sprite = SpriteLoader.loadSprite(path);
    }

    public SpriteComponent(String path, Shader shader) {
        this(SpriteLoader.loadSprite(path, 1), shader);
    }

    public SpriteComponent(Sprite sprite) {
        framePart = new SpriteFramePart(sprite, null);
    }

    public SpriteComponent(Sprite sprite, Shader shader) {
        framePart = new SpriteFramePart(sprite, shader);
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        framePart.position = new Vector4f(source.position.x, source.position.y, source.renderPriority, source.rollRotation);
        if (partsBuilder != null)
            partsBuilder.addPart(framePart);
    }

    @Override
    public String save() {
        return framePart.sprite.save();
    }

    @Override
    public Saveable load(String savedString) {
        SpriteComponent loadedSpriteComponent = new SpriteComponent((Sprite) new Sprite().load(savedString));
        this.framePart = loadedSpriteComponent.framePart;
        return this;
    }
}
