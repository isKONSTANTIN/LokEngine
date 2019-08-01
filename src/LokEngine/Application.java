package LokEngine;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Camera;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform2f;

public class Application {
    public Window appWin;

    private void startApp(boolean windowFullscreen, Vector2f windowResolution, String windowTitle){
        appWin = new Window();

        try {
            appWin.setTitle(windowTitle);
            appWin.open(windowFullscreen, windowResolution);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultFields.defaultVertexScreenBuffer = BufferLoader.load(new float[] {
                -windowResolution.x / 2, windowResolution.y / 2,
                -windowResolution.x / 2, -windowResolution.y / 2,
                windowResolution.x / 2, -windowResolution.y / 2,
                windowResolution.x / 2, windowResolution.y / 2,
        });
        RuntimeFields.frameBuilder = new FrameBuilder(appWin);
        RuntimeFields.scene = new Scene();

        try {
            DefaultFields.defaultShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl","#/resources/shaders/DefaultFragShader.glsl");
            DefaultFields.unknownSprite = SpriteLoader.loadSprite("#/resources/textures/unknown.png",DefaultFields.defaultShader);
            DefaultFields.DisplayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl","#/resources/shaders/DisplayFragShader.glsl");
            DefaultFields.PostProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl","#/resources/shaders/BlurFragShader.glsl");
            DefaultFields.unknownSprite.size = 50;


            Shader.use(DefaultFields.PostProcessingShader);
            Camera.updateProjection(appWin.getResolution().x, appWin.getResolution().y,1 / 0.000520833f / 4);

            Shader.use(DefaultFields.DisplayShader);
            glUniform2f(glGetUniformLocation(Shader.currentShader.program, "screenSize"), appWin.getResolution().x, appWin.getResolution().y);
            Camera.updateProjection(appWin.getResolution().x, appWin.getResolution().y,1 / 0.000520833f / 4);

            Shader.use(DefaultFields.defaultShader);
            Camera.updateProjection(appWin.getResolution().x / appWin.getResolution().y, 1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Init();
        }catch (Exception e){
            e.printStackTrace();
        }

        while (true){
            try {
                Update();
            }catch (Exception e){
                e.printStackTrace();
            }

            RuntimeFields.scene.update();

            try {
                nextFrame();
            }catch (Exception e){
                e.printStackTrace();
            }

            appWin.update();
        }
    }

    public void start() {
        startApp(false, new Vector2f(512,512), "LokEngine application");
    }

    public void start(boolean windowFullscreen) {
        startApp(windowFullscreen, new Vector2f(512,512), "LokEngine application");
    }

    public void start(boolean windowFullscreen, Vector2f windowResolution) {
        startApp(windowFullscreen, windowResolution, "LokEngine application");
    }

    public void start(boolean windowFullscreen, Vector2f windowResolution, String windowTitle) {
        startApp(windowFullscreen, windowResolution, windowTitle);
    }

    public void Init(){}
    public void Update(){}

    private void nextFrame(){
        Shader.use(DefaultFields.defaultShader);
        appWin.getCamera().updateView();
        try {
            RuntimeFields.frameBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
