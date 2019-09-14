package LokEngine.Components.AdditionalObjects.Rigidbody;

import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.SaveWorker.Saveable;
import org.jbox2d.dynamics.Body;

public class Rigidbody implements Saveable {

    public float density = 1.0f;
    public float friction = 0.3f;
    public boolean isStatic = false;
    public Body b2body;

    @Override
    public String save() {
        String stringBuilder = density + "\n" + friction + "\n" + isStatic;

        return Base64.toBase64(stringBuilder);
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        this.density = Float.valueOf(lines[0]);
        this.friction = Float.valueOf(lines[1]);
        this.isStatic = Boolean.valueOf(lines[2]);

        return this;
    }
}
