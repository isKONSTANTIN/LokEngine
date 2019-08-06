package LokEngine;

import LokEngine.GUI.Canvas;
import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Camera;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.MouseStatus;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform2f;

public class Application {
    public Window window;

    private void startApp(boolean windowFullscreen, Vector2i windowResolution, String windowTitle){
        window = new Window();

        try {
            window.setTitle(windowTitle);
            window.open(windowFullscreen, windowResolution);
            windowResolution = window.getResolution();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultFields.defaultVertexScreenBuffer = BufferLoader.load(new float[] {
                -windowResolution.x / 2, windowResolution.y / 2,
                -windowResolution.x / 2, -windowResolution.y / 2,
                windowResolution.x / 2, -windowResolution.y / 2,
                windowResolution.x / 2, windowResolution.y / 2,
        });

        RuntimeFields.mouseStatus = new MouseStatus();
        RuntimeFields.frameBuilder = new FrameBuilder(window);
        RuntimeFields.scene = new Scene();
        RuntimeFields.canvas = new Canvas();

        try {
            DefaultFields.defaultShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl","#/resources/shaders/DefaultFragShader.glsl");
            DefaultFields.unknownSprite = SpriteLoader.loadSprite("#/resources/textures/unknown.png",DefaultFields.defaultShader);
            DefaultFields.DisplayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl","#/resources/shaders/DisplayFragShader.glsl");
            DefaultFields.PostProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl","#/resources/shaders/BlurFragShader.glsl");
            DefaultFields.unknownSprite.size = 50;

            Shader.use(DefaultFields.PostProcessingShader);
            Camera.updateProjection(window.getResolution().x, window.getResolution().y,1 / 0.000520833f / 4);

            Shader.use(DefaultFields.DisplayShader);
            glUniform2f(glGetUniformLocation(Shader.currentShader.program, "screenSize"), window.getResolution().x, window.getResolution().y);
            Camera.updateProjection(window.getResolution().x, window.getResolution().y,1 / 0.000520833f / 4);

            Shader.use(DefaultFields.defaultShader);
            Camera.updateProjection((float) window.getResolution().x / (float) window.getResolution().y, 1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Init();
        }catch (Exception e){
            e.printStackTrace();
        }

        while (true){
            RuntimeFields.mouseStatus.mousePosition.x = Mouse.getX();
            RuntimeFields.mouseStatus.mousePosition.y = Mouse.getY();
            RuntimeFields.mouseStatus.mousePressed = Mouse.isButtonDown(0);

            try {
                Update();
            }catch (Exception e){
                e.printStackTrace();
            }

            if (!window.isOpened()) break;

            RuntimeFields.scene.update();
            RuntimeFields.canvas.update();

            try {
                 nextFrame();
            }catch (Exception e){
                e.printStackTrace();
            }

            window.update();
        }
    }

    private void nextFrame(){
        Shader.use(DefaultFields.defaultShader);
        window.getCamera().updateView();
        try {
            RuntimeFields.frameBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        startApp(false, new Vector2i(512,512), "LokEngine application");
    }
    public void start(boolean windowFullscreen) { startApp(windowFullscreen, new Vector2i(512,512), "LokEngine application"); }
    public void start(boolean windowFullscreen, Vector2i windowResolution) { startApp(windowFullscreen, windowResolution, "LokEngine application"); }
    public void start(boolean windowFullscreen, Vector2i windowResolution, String windowTitle) {startApp(windowFullscreen, windowResolution, windowTitle); }

    public void Init(){}
    public void Update(){}
}
