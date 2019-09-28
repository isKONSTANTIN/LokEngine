package LokEngine.Render;

import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector2f;

public class Camera {

    public Vector2f position = new Vector2f(0,0);
    public float rollRotation;
    public float fieldOfView;
    public float screenRatio = 1;

    public void updateProjection(float width, float height){
        float projectionFieldOfView = fieldOfView * 0.000520833f * 4;
        MatrixCreator.PutMatrixInShader(Shader.currentShader, "Projection", MatrixCreator.CreateOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
    }

    public void updateProjection(float width, float height, float projectionFieldOfView){
        MatrixCreator.PutMatrixInShader(Shader.currentShader, "Projection", MatrixCreator.CreateOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
    }

    public Vector2f screenPointToScene(Vector2i point){  // TODO: fix it
        Vector2f screenCenter = new Vector2f(RuntimeFields.getFrameBuilder().window.getResolution().x / 2f, RuntimeFields.getFrameBuilder().window.getResolution().y / 2f);
        return new Vector2f(
                0.520833f * fieldOfView * ((point.x - screenCenter.x) / screenCenter.x) * screenRatio + position.x,
                0.520833f * fieldOfView * ((point.y - screenCenter.y) / screenCenter.y) + position.y
        );
    }


    public Vector2i scenePointToScreen(Vector2f point){  // TODO: fix it
        Vector2f screenCenter = new Vector2f(RuntimeFields.getFrameBuilder().window.getResolution().x / 2f, RuntimeFields.getFrameBuilder().window.getResolution().y / 2f);

        return new Vector2i(
                Math.round((point.x - position.x) / 0.520833f / fieldOfView / screenRatio * screenCenter.x + screenCenter.x),
                Math.round((point.y - position.y) / 0.520833f / fieldOfView * screenCenter.y + screenCenter.y)
        );
    }

    public void setFieldOfView(float fieldOfView, Shader shader){
        this.fieldOfView = fieldOfView;
        screenRatio = (float) RuntimeFields.getFrameBuilder().window.getResolution().x / (float) RuntimeFields.getFrameBuilder().window.getResolution().y;
        Shader activeShader = Shader.currentShader;

        Shader.use(shader);
        updateProjection(screenRatio, 1);

        if (activeShader != null){
            Shader.use(activeShader);
        }else{
            Shader.unUse();
        }
    }

    public void setFieldOfView(float fieldOfView){
        this.fieldOfView = fieldOfView;
        screenRatio = (float) RuntimeFields.getFrameBuilder().window.getResolution().x / (float) RuntimeFields.getFrameBuilder().window.getResolution().y;
        Shader activeShader = Shader.currentShader;

        Shader.use(DefaultFields.defaultShader);
        updateProjection(screenRatio, 1);

        Shader.use(DefaultFields.particlesShader);
        updateProjection(screenRatio, 1);

        if (activeShader != null){
            Shader.use(activeShader);
        }else{
            Shader.unUse();
        }
    }

    public void updateView(){
        updateView(Shader.currentShader);
    }

    public void updateView(Shader shader){
        MatrixCreator.PutMatrixInShader(shader, "View", MatrixCreator.CreateViewMatrix(this));
        AL10.alListener3f(AL10.AL_POSITION,position.x,position.y,0);
        AL10.alListener3f(AL10.AL_VELOCITY,0,0,0);
    }
}
