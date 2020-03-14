package ru.lokincompany.lokengine.render.camera;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class Camera {

    protected Vector3f position = new Vector3f(0, 0, 500);
    protected Vector3f rotation = new Vector3f(0, 0, 0);

    protected float fieldOfView = 1;
    protected float screenRatio = 1;
    protected Window window;

    public CameraMode cameraMode = CameraMode.Orthographic;

    public Camera(Window window) {
        this.window = window;
    }

    public Vector2f screenPointToScene(Vector2i point) {
        Vector2f screenCenter = new Vector2f(window.getResolution().x / 2f, window.getResolution().y / 2f);
        return new Vector2f(
                0.5f * fieldOfView * ((point.x - screenCenter.x) / screenCenter.x) * screenRatio + position.x,
                0.5f * fieldOfView * ((window.getResolution().y - point.y - screenCenter.y) / screenCenter.y) + position.y
        );
    }

    public Vector2i scenePointToScreen(Vector2f point) {
        Vector2f screenCenter = new Vector2f(window.getResolution().x / 2f, window.getResolution().y / 2f);

        return new Vector2i(
                Math.round((point.x - position.x) / 0.5f / fieldOfView / screenRatio * screenCenter.x + screenCenter.x),
                Math.round(window.getResolution().y - ((point.y - position.y) / 0.5f / fieldOfView * screenCenter.y + screenCenter.y))
        );
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }

    public float getScreenRatio() {
        return screenRatio;
    }

    public Window getWindow() {
        return window;
    }

    public void update(){
        screenRatio = (float) window.getResolution().x / (float) window.getResolution().y;

        AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }
}
