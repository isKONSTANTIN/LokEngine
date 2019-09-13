package LokEngine.Components.AdditionalObjects;

import org.jbox2d.dynamics.Body;

public class Rigidbody {

    public float density = 1.0f;
    public float friction = 0.3f;
    public boolean isStatic = false;
    public Body b2body;

}
