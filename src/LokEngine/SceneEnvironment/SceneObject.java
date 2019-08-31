package LokEngine.SceneEnvironment;

import LokEngine.Components.ComponentList;
import LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector2f;

public class SceneObject implements Saveable {
    public Vector2f position = new Vector2f(0,0);
    public float rollRotation = 0;
    public float renderPriority = 0;
    public ComponentList components;
    public Scene scene;

    public SceneObject(){ components = new ComponentList();}

    public void init(Scene scene){
        this.scene = scene;
    }

    public void update(){
        components.update(this);
    }

    @Override
    public String save() {
        return position.x + "\n" + position.y + "\n" + rollRotation + "\n" + renderPriority + "\n" + components.save();
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = savedString.split(System.getProperty("line.separator"));

        position = new Vector2f(Float.valueOf(lines[0]),Float.valueOf(lines[1]));
        rollRotation = Float.valueOf(lines[2]);
        renderPriority = Float.valueOf(lines[3]);
        components.load(lines[4]);

        return this;
    }
}
