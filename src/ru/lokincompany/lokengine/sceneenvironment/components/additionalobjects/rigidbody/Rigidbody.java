package ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.rigidbody;

import org.jbox2d.dynamics.Body;
import ru.lokincompany.lokengine.tools.base64.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class Rigidbody implements Saveable {

    public float density;
    public float friction;
    public boolean isStatic;
    public Body b2body;
    public Object objectData;
    public boolean isSensor;

    public Rigidbody(boolean isStatic, float friction, float density, boolean isSensor, Object objectData) {
        this.isStatic = isStatic;
        this.friction = friction;
        this.density = density;
        this.objectData = objectData;
        this.isSensor = isSensor;
    }

    public Rigidbody(boolean isStatic, float friction, float density) {
        this(isStatic, friction, density, false, null);
    }

    public Rigidbody(boolean isStatic, float friction) {
        this(isStatic, friction, 1);
    }


    public Rigidbody(boolean isStatic) {
        this(isStatic, 0.3f);
    }

    public Rigidbody() {
        this(false);
    }

    @Override
    public String save() {
        String stringBuilder = density + "\n" + friction + "\n" + isStatic;

        return Base64.toBase64(stringBuilder);
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        this.density = Float.parseFloat(lines[0]);
        this.friction = Float.parseFloat(lines[1]);
        this.isStatic = Boolean.parseBoolean(lines[2]);

        return this;
    }
}
