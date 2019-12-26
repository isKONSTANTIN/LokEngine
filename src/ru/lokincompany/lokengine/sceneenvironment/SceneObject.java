package ru.lokincompany.lokengine.sceneenvironment;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.sceneenvironment.components.ComponentsList;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.base64.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class SceneObject implements Saveable {
    public Vector2f position = new Vector2f(0, 0);
    public float rollRotation = 0;
    public float renderPriority = 0;
    public ComponentsList components;
    public Scene scene;
    public String name = "Unnamed object";

    public SceneObject() {
        components = new ComponentsList();
    }

    public void init(Scene scene) {
        this.scene = scene;
    }

    public void update(ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        components.update(this, applicationRuntime, partsBuilder);
    }

    @Override
    public String save() {
        return Base64.toBase64(position.x + "\n" + position.y + "\n" + rollRotation + "\n" + renderPriority + "\n" + components.save() + "\n" + name);
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        position = new Vector2f(Float.parseFloat(lines[0]), Float.parseFloat(lines[1]));
        rollRotation = Float.parseFloat(lines[2]);
        renderPriority = Float.parseFloat(lines[3]);
        components.load(lines[4]);
        name = lines[5];

        return this;
    }
}
