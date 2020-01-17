package ru.lokincompany.lokengine.render;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
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
        float projectionFieldOfView = fieldOfView * 0.0005f * 4;
        window.getFrameBuilder().getRenderProperties().getActiveShader().setUniformData("Projection", MatrixTools.createOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
    }

    public void updateProjection(float width, float height, float projectionFieldOfView) {
        window.getFrameBuilder().getRenderProperties().getActiveShader().setUniformData("Projection", MatrixTools.createOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
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

    public void setFieldOfView(float fieldOfView, Shader shader) {
        this.fieldOfView = fieldOfView;
        screenRatio = (float) window.getResolution().x / (float) window.getResolution().y;
        Shader activeShader = window.getFrameBuilder().getRenderProperties().getActiveShader();

        window.getFrameBuilder().getRenderProperties().useShader(shader);
        updateProjection(screenRatio, 1);

        if (activeShader != null) {
            window.getFrameBuilder().getRenderProperties().useShader(activeShader);
        } else {
            window.getFrameBuilder().getRenderProperties().unUseShader();
        }
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
        RenderProperties renderProperties = window.getFrameBuilder().getRenderProperties();
        screenRatio = (float) window.getResolution().x / (float) window.getResolution().y;
        Shader activeShader = renderProperties.getActiveShader();

        renderProperties.useShader(renderProperties.getObjectShader());
        updateProjection(screenRatio, 1);

        renderProperties.useShader(renderProperties.getParticlesShader());
        updateProjection(screenRatio, 1);

        if (activeShader != null) {
            renderProperties.useShader(activeShader);
        } else {
            renderProperties.unUseShader();
        }
    }

    public void updateView() {
        updateView(window.getFrameBuilder().getRenderProperties().getActiveShader());
    }

    public void updateView(Shader shader) {
        shader.setUniformData("View", MatrixTools.createViewMatrix(this));
    }

    public void updateAudioListener() {
        AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

}
