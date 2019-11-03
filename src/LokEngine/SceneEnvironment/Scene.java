package LokEngine.SceneEnvironment;

import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.ApplicationRuntime;
import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.Compression.GZIPCompression;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.ArraySaver;
import LokEngine.Tools.SaveWorker.Saveable;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.io.IOException;
import java.util.Vector;

public class Scene implements Saveable {
    private Vector<SceneObject> objects = new Vector<>();
    private Vector<PostUpdateEvent> postUpdateEvents = new Vector<>();
    public World b2World;

    public int physicsVelocityIterations = 12;
    public int physicsPositionsIterations = 4;

    public Scene() {
        b2World = new World(new Vec2(0, -5));
    }

    public int getCountObjects() {
        return objects.size();
    }

    public int addObject(SceneObject newObject) {
        objects.add(newObject);
        newObject.init(this);
        return objects.size() - 1;
    }

    public void removeObject(int id) {
        objects.remove(id);
    }

    public void removeAll(){ objects.clear(); }

    public void addPostUpdateEvent(PostUpdateEvent event) {
        postUpdateEvents.add(event);
    }

    public void update(ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update(applicationRuntime, partsBuilder);
        }
        b2World.step(1 / 60f * applicationRuntime.getSpeedEngine() * Math.min(12, applicationRuntime.getDeltaTime()), physicsVelocityIterations, physicsPositionsIterations);

        for (int i = 0; i < postUpdateEvents.size(); i++) {
            postUpdateEvents.get(i).postUpdate();
        }
        postUpdateEvents.clear();
    }

    public SceneObject getObjectByID(int id) {
        return objects.get(id);
    }

    public SceneObject getObjectByName(String name) {
        for (SceneObject sceneObject : objects) {
            if (sceneObject.name.equals(name))
                return sceneObject;
        }
        return null;
    }

    @Override
    public String save() {
        ArraySaver arraySaver = new ArraySaver(SceneObject.class);
        arraySaver.arrayList.addAll(objects);

        String sceneData = physicsVelocityIterations + "\n" + physicsPositionsIterations;

        try {
            return Base64.bytesToBase64(GZIPCompression.compress(sceneData + ",\n" + arraySaver.save()));
        } catch (IOException e) {
            Logger.warning("Fail save scene!", "LokEngine_Scene");
            Logger.printException(e);
        }

        return null;
    }

    @Override
    public Saveable load(String savedString) {
        ArraySaver arraySaver = new ArraySaver(SceneObject.class);
        String[] data;
        try {
            data = GZIPCompression.decompress(Base64.bytesFromBase64(savedString)).split(",\n");
        } catch (IOException e) {
            Logger.warning("Fail load scene!", "LokEngine_Scene");
            Logger.printException(e);
            return null;
        }
        String[] lines = data[0].split("\n");

        physicsVelocityIterations = Integer.valueOf(lines[0]);
        physicsPositionsIterations = Integer.valueOf(lines[1]);

        arraySaver.load(data[1]);

        for (Saveable savedSceneObject : arraySaver.arrayList) {
            SceneObject sceneObject = (SceneObject) savedSceneObject;
            objects.add(sceneObject);
            sceneObject.init(this);
        }

        return this;
    }
}
