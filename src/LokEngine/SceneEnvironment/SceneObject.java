package LokEngine.SceneEnvironment;

import LokEngine.Components.ComponentList;
import org.lwjgl.util.vector.Vector2f;

import java.util.Vector;

public class SceneObject extends ComponentList {
    public Vector2f position = new Vector2f(0,0);
    public float rollRotation = 0;
    public float renderPriority = 0;
    public Scene scene;

    public SceneObject(){
        components = new Vector<>();
    }

    public void init(Scene scene){
        this.scene = scene;
    }

    public void update(){
        updateComponents(this);
    }
}
