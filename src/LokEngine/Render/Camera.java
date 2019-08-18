package LokEngine.Render;

import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.util.vector.Vector2f;

public class Camera {

    public Vector2f position = new Vector2f(0,0);
    public float rollRotation;

    public static void updateProjection(float width, float height, float fieldOfView){
        fieldOfView *= 0.000520833f * 4;
        MatrixCreator.PutMatrixInShader(Shader.currentShader, "Projection", MatrixCreator.CreateOrthoMatrix(width * fieldOfView, height * fieldOfView));
    }

    public void updateView(){
        MatrixCreator.PutMatrixInShader(Shader.currentShader, "View", MatrixCreator.CreateViewMatrix(this));
    }

    public void updateView(Shader shader){
        MatrixCreator.PutMatrixInShader(shader, "View", MatrixCreator.CreateViewMatrix(this));
    }
}
