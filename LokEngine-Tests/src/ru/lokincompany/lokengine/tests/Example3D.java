package ru.lokincompany.lokengine.tests;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.applications.ApplicationDefault;
import ru.lokincompany.lokengine.render.camera.CameraMode;
import ru.lokincompany.lokengine.render.model.Model;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurTuning;
import ru.lokincompany.lokengine.render.postprocessing.workers.bloom.BloomActionWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.bloom.BloomSettings;
import ru.lokincompany.lokengine.render.sceneenvironment.threed.components.ModelComponent;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.glfw.GLFW.*;

public class Example3D extends ApplicationDefault {

    public static void main(String[] args) {
        new Example3D().start(false,true, true, 32, new Vector2i(1024,512));
    }
    SceneObject sceneObject;

    @Override
    protected void updateEvent() {
        Keyboard keyboard = window.getKeyboard();
        Vector3f move = new Vector3f();

        if (keyboard.isKeyDown(GLFW.GLFW_KEY_Q))
            close();

        move.x += keyboard.isKeyDown(GLFW.GLFW_KEY_D) ? 0.5f : 0;
        move.x -= keyboard.isKeyDown(GLFW.GLFW_KEY_A) ? 0.5f : 0;

        move.z += keyboard.isKeyDown(GLFW.GLFW_KEY_S) ? 0.5f : 0;
        move.z -= keyboard.isKeyDown(GLFW.GLFW_KEY_W) ? 0.5f : 0;

        move.y += keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE) ? 0.5f : 0;
        move.y -= keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) ? 0.5f : 0;

        window.getActiveCamera().movePosition(move.x, move.y, move.z);

        window.getActiveCamera().getRotation().x += window.getMouse().getMouseDeltaPosition().y / 10f;
        window.getActiveCamera().getRotation().y += window.getMouse().getMouseDeltaPosition().x / 10f;

        sceneObject.position.x+= 0.1f;
    }

    @Override
    protected void initEvent() {
        window.getActiveCamera().cameraMode = CameraMode.Perspective;
        window.getActiveCamera().setFieldOfView(100);
        window.getActiveCamera().getPosition().set(0,0,0);

        sceneObject = new SceneObject();
        scene.addObject(sceneObject);
        sceneObject.components.add(new ModelComponent(new Model("resources/models/building.obj")));

        BloomSettings settings = new BloomSettings(new BlurTuning(0.6, 50, 0.15), 0.8f);
        window.getFrameBuilder().getPostProcessingActionWorker(BloomActionWorker.class).setBloomSettings(settings);
    }
}
