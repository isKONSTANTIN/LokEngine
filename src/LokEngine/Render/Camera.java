package LokEngine.Render;

import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    public Vector2f position = new Vector2f(0,0);
    public float rollRotation;

    public static void updateProjection(float width, float height, float fieldOfView){
        fieldOfView *= 0.000520833f * 4;
        MatrixCreator.PutMatrixInShader(Shader.currentShader, "Projection", MatrixCreator.CreateOrthoMatrix(width * fieldOfView, height * fieldOfView));
    }

    public void setFieldOfView(float fieldOfView){
        Shader activeShader = Shader.currentShader;

        Shader.use(DefaultFields.defaultShader);
        Camera.updateProjection((float) RuntimeFields.getFrameBuilder().window.getResolution().x / (float) RuntimeFields.getFrameBuilder().window.getResolution().y, 1, fieldOfView);

        Shader.use(DefaultFields.particlesShader);
        Camera.updateProjection((float) RuntimeFields.getFrameBuilder().window.getResolution().x / (float) RuntimeFields.getFrameBuilder().window.getResolution().y, 1, fieldOfView);

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
