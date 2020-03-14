package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.SpriteComponent;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.compression.GZIPCompression;
import ru.lokincompany.lokengine.tools.saveworker.ArraySaver;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

import java.io.IOException;
import java.util.Comparator;
import java.util.Vector;

public class Scene implements Saveable {
    public World b2World;
    public int physicsVelocityIterations = 12;
    public int physicsPositionsIterations = 4;
    private Vector<SceneObject> objects = new Vector<>();
    private Vector<PostUpdateEvent> postUpdateEvents = new Vector<>();

    public Scene() {
        b2World = new World(new Vec2(0, -5));
    }

    public int getCountObjects() {
        return objects.size();
    }

    public int addObject(SceneObject newObject) {
        objects.add(newObject);
        newObject.init(this);
        resortObjectsByRenderPriority();
        return objects.size() - 1;
    }

    public void removeObject(int id) {
        objects.remove(id);
    }

    public void removeAll() {
        objects.clear();
    }

    public void addPostUpdateEvent(PostUpdateEvent event) {
        postUpdateEvents.add(event);
    }

    public void resortObjectsByRenderPriority(){
        objects.sort((o1, o2) -> {
            float priority = o1.renderPriority - o2.renderPriority;
            return priority > 0 ? 1 : priority < 0 ? -1 : 0;
        });
    }

    public void update(ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        for (SceneObject object : objects) {
            object.update(applicationRuntime, partsBuilder);
        }
        b2World.step(1 / 60f * applicationRuntime.getSpeedEngine() * Math.min(12, applicationRuntime.getDeltaTime()), physicsVelocityIterations, physicsPositionsIterations);

        for (PostUpdateEvent postUpdateEvent : postUpdateEvents) {
            postUpdateEvent.postUpdate();
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
            Logger.printThrowable(e);
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
            Logger.printThrowable(e);
            return null;
        }
        String[] lines = data[0].split("\n");

        physicsVelocityIterations = Integer.parseInt(lines[0]);
        physicsPositionsIterations = Integer.parseInt(lines[1]);

        arraySaver.load(data[1]);

        for (Saveable savedSceneObject : arraySaver.arrayList) {
            SceneObject sceneObject = (SceneObject) savedSceneObject;
            objects.add(sceneObject);
            sceneObject.init(this);
        }

        return this;
    }
}
