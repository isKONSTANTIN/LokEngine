package LokEngine.SceneEnvironment;

import LokEngine.Tools.RuntimeFields;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.Vector;

public class Scene {

    private Vector<SceneObject> objects = new Vector<>();
    private Vector<PostUpdateEvent> postUpdateEvents = new Vector<>();
    public World b2World;

    public Scene(){
        b2World = new World(new Vec2(0,-5));
    }

    public int addObject(SceneObject newObject){
        objects.add(newObject);
        newObject.init(this);
        return objects.size()-1;
    }

    public void addPostUpdateEvent(PostUpdateEvent event){
        postUpdateEvents.add(event);
    }

    public void update(){
        for (int i = 0; i < objects.size(); i++){
            objects.get(i).update();
        }
        b2World.step(1.0f / 60.0f * RuntimeFields.getDeltaTime(), 12, 4);

        for (int i = 0; i < postUpdateEvents.size(); i++){
            postUpdateEvents.get(i).postUpdate();
        }
        postUpdateEvents.clear();
    }

    public SceneObject getObjectByID(int id){
        return objects.get(id);
    }

}
