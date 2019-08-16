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
import LokEngine.Tools.Logger;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.MouseStatus;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform2f;

public class Application {
    public Window window;

    private void startApp(boolean windowFullscreen, Vector2i windowResolution, String windowTitle) {
        try {
            Logger.debug("Init window", "LokEngine_start");
            window = new Window();

            try {
                window.setTitle(windowTitle);
                window.open(windowFullscreen, windowResolution);
                windowResolution = window.getResolution();
            } catch (Exception e) {
                Logger.error("Fail open window!", "LokEngine_start");
                Logger.printException(e);
            }

            Logger.debug("Init default vertex screen buffer", "LokEngine_start");
            DefaultFields.defaultVertexScreenBuffer = BufferLoader.load(new float[]{
                    -windowResolution.x / 2, windowResolution.y / 2,
                    -windowResolution.x / 2, -windowResolution.y / 2,
                    windowResolution.x / 2, -windowResolution.y / 2,
                    windowResolution.x / 2, windowResolution.y / 2,
            });

            Logger.debug("Init runtime fields", "LokEngine_start");
            RuntimeFields.mouseStatus = new MouseStatus();
            RuntimeFields.frameBuilder = new FrameBuilder(window);
            RuntimeFields.scene = new Scene();
            RuntimeFields.canvas = new Canvas();
            Logger.debug("Init default font", "LokEngine_start");
            try {
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/Fonts/Default.ttf")));
            } catch (FontFormatException | IOException e) {
                Logger.error("Fail register default font!", "LokEngine_start");
                Logger.printException(e);
            }

            Logger.debug("Init shaders", "LokEngine_start");
            try {
                DefaultFields.defaultShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl", "#/resources/shaders/DefaultFragShader.glsl");
                DefaultFields.unknownSprite = SpriteLoader.loadSprite("#/resources/textures/unknown.png", DefaultFields.defaultShader);
                DefaultFields.DisplayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl", "#/resources/shaders/DisplayFragShader.glsl");
                DefaultFields.PostProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl", "#/resources/shaders/BlurFragShader.glsl");
                DefaultFields.unknownSprite.size = 50;

                Shader.use(DefaultFields.PostProcessingShader);
                Camera.updateProjection(window.getResolution().x, window.getResolution().y, 1 / 0.000520833f / 4);

                Shader.use(DefaultFields.DisplayShader);
                glUniform2f(glGetUniformLocation(Shader.currentShader.program, "screenSize"), window.getResolution().x, window.getResolution().y);
                Camera.updateProjection(window.getResolution().x, window.getResolution().y, 1 / 0.000520833f / 4);

                Shader.use(DefaultFields.defaultShader);
                Camera.updateProjection((float) window.getResolution().x / (float) window.getResolution().y, 1, 1);
            } catch (Exception e) {
                Logger.error("Fail load shaders!", "LokEngine_start");
                Logger.printException(e);
            }
            Logger.debug("Call user init method", "LokEngine_start");
            try {
                Init();
            } catch (Exception e) {
                Logger.warning("Fail user-init!", "LokEngine_start");
                Logger.printException(e);
            }
            Logger.debug("Turn in main while!", "LokEngine_start");
        } catch (Exception e) {
            Logger.error("Fail start engine!", "LokEngine_start");
            Logger.printException(e);
        }

        try {
            while (true) {
                RuntimeFields.mouseStatus.mousePosition.x = Mouse.getX();
                RuntimeFields.mouseStatus.mousePosition.y = Mouse.getY();
                RuntimeFields.mouseStatus.mousePressed = Mouse.isButtonDown(0);

                try {
                    Update();
                } catch (Exception e) {
                    Logger.warning("Fail user-update!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                if (!window.isOpened()) break;

                RuntimeFields.scene.update();
                RuntimeFields.canvas.update();

                try {
                    nextFrame();
                } catch (Exception e) {
                    Logger.warning("Fail build frame!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                window.update();
            }
        }catch (Exception e){
            Logger.error("Critical error in main while engine!", "LokEngine_runtime");
            Logger.printException(e);
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
