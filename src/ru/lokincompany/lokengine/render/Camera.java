package ru.lokincompany.lokengine.render;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.loaders.MatrixLoader;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class Camera {

    public Vector2f position = new Vector2f(0, 0);
    public float rollRotation;
    public float fieldOfView = 1;
    public float screenRatio = 1;
    public Window window;

    public Camera(Window window) {
        this.window = window;
    }

    public void updateProjection(float width, float height) {
        float projectionFieldOfView = fieldOfView * 0.000520833f * 4;
        window.getFrameBuilder().getBuilderProperties().getActiveShader().setUniformData("Projection", MatrixLoader.createOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
    }

    public void updateProjection(float width, float height, float projectionFieldOfView) {
        window.getFrameBuilder().getBuilderProperties().getActiveShader().setUniformData("Projection", MatrixLoader.createOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
    }

    public Vector2f screenPointToScene(Vector2i point) {
        Vector2f screenCenter = new Vector2f(window.getResolution().x / 2f, window.getResolution().y / 2f);
        return new Vector2f(
                0.520833f * fieldOfView * ((point.x - screenCenter.x) / screenCenter.x) * screenRatio + position.x,
                0.520833f * fieldOfView * ((window.getResolution().y - point.y - screenCenter.y) / screenCenter.y) + position.y
        );
    }

    public Vector2i scenePointToScreen(Vector2f point) {
        Vector2f screenCenter = new Vector2f(window.getResolution().x / 2f, window.getResolution().y / 2f);

        return new Vector2i(
                Math.round((point.x - position.x) / 0.520833f / fieldOfView / screenRatio * screenCenter.x + screenCenter.x),
                Math.round(window.getResolution().y - ((point.y - position.y) / 0.520833f / fieldOfView * screenCenter.y + screenCenter.y))
        );
    }

    public void setFieldOfView(float fieldOfView, Shader shader) {
        this.fieldOfView = fieldOfView;
        screenRatio = (float) window.getResolution().x / (float) window.getResolution().y;
        Shader activeShader = window.getFrameBuilder().getBuilderProperties().getActiveShader();

        window.getFrameBuilder().getBuilderProperties().useShader(shader);
        updateProjection(screenRatio, 1);

        if (activeShader != null) {
            window.getFrameBuilder().getBuilderProperties().useShader(activeShader);
        } else {
            window.getFrameBuilder().getBuilderProperties().unUseShader();
        }
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
        BuilderProperties builderProperties = window.getFrameBuilder().getBuilderProperties();
        screenRatio = (float) window.getResolution().x / (float) window.getResolution().y;
        Shader activeShader = builderProperties.getActiveShader();

        builderProperties.useShader(builderProperties.getObjectShader());
        updateProjection(screenRatio, 1);

        builderProperties.useShader(builderProperties.getParticlesShader());
        updateProjection(screenRatio, 1);

        if (activeShader != null) {
            builderProperties.useShader(activeShader);
        } else {
            builderProperties.unUseShader();
        }
    }

    public void updateView() {
        updateView(window.getFrameBuilder().getBuilderProperties().getActiveShader());
    }

    public void updateView(Shader shader) {
        shader.setUniformData("View", MatrixLoader.createViewMatrix(this));
    }

    public void updateAudioListener() {
        AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

}
