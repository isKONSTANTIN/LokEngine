package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components;

import org.lwjgl.util.vector.Vector4f;
import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.SpriteFramePart;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.render.Sprite;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class SpriteComponent extends Component implements Saveable {

    private SpriteFramePart framePart;

    public SpriteComponent() {
    }

    public SpriteComponent(String path) {
        this(path, null);
    }

    public SpriteComponent(String path, Shader shader) {
        this(new Sprite(path), shader);
    }

    public SpriteComponent(Sprite sprite) {
        this(sprite, null, Colors.white());
    }

    public SpriteComponent(Sprite sprite, Shader shader) {
        this(sprite, shader, Colors.white());
    }

    public SpriteComponent(Sprite sprite, Shader shader, Color color) {
        framePart = new SpriteFramePart(sprite, shader, color);
    }

    public Sprite getSprite() {
        return framePart.sprite;
    }

    public void setSprite(Sprite sprite) {
        framePart.sprite = sprite;
    }

    public void setSprite(String path) {
        framePart.sprite = new Sprite(path);
    }

    public Color getColor() {
        return framePart.color;
    }

    public void setColor(Color color) {
        framePart.color = color;
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
