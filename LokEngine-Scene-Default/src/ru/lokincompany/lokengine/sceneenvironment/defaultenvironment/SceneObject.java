package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.ComponentsList;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class SceneObject implements Saveable {
    public Vector3f position = new Vector3f(0, 0,0);
    public Vector3f rotation = new Vector3f(0,0,0);
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
        return Base64.toBase64(
                position.x + "\n"
                        + position.y + "\n"
                        + position.z + "\n"
                        + rotation.x + "\n"
                        + rotation.y + "\n"
                        + rotation.z + "\n"
                        + components.save() + "\n"
                        + name);
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        position = new Vector3f(Float.parseFloat(lines[0]), Float.parseFloat(lines[1]), Float.parseFloat(lines[2]));
        rotation = new Vector3f(Float.parseFloat(lines[3]), Float.parseFloat(lines[4]), Float.parseFloat(lines[5]));

        components.load(lines[6]);
        name = lines[7];

        return this;
    }
}
