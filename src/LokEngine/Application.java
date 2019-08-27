package LokEngine;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.BlurActionWorker;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.*;
import LokEngine.Tools.SplashScreen;
import LokEngine.Tools.SaveWorker.Prefs;
import LokEngine.Tools.Utilities.MouseStatus;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.openal.AL;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform2f;

public class Application {
    public Window window;
    private boolean isRun;

    private void startApp(boolean windowFullscreen, boolean vSync, Vector2i windowResolution, String windowTitle) {
        try {
            Prefs.init();
        }catch (Exception e) {
            Logger.warning("Fail load in prefs!", "LokEngine_start");
            Logger.printException(e);
        }
        try {
            Logger.debug("Init window", "LokEngine_start");
            window = new Window();

            try {
                window.setTitle(windowTitle);
                window.open(windowFullscreen, vSync, windowResolution);
                windowResolution = window.getResolution();
            } catch (Exception e) {
                Logger.error("Fail open window!", "LokEngine_start");
                Logger.printException(e);
            }
            try {
                SplashScreen.init(window);
            } catch (Exception e) {
                Logger.error("Fail init splash screen!", "LokEngine_start");
                Logger.printException(e);
            }

            SplashScreen.updateStatus(0.1f);
            Logger.debug("Init default vertex screen buffer", "LokEngine_start");
            DefaultFields.defaultVertexScreenBuffer = BufferLoader.load(new float[]{
                    -windowResolution.x / 2, windowResolution.y / 2,
                    -windowResolution.x / 2, -windowResolution.y / 2,
                    windowResolution.x / 2, -windowResolution.y / 2,
                    windowResolution.x / 2, windowResolution.y / 2,
            });
            SplashScreen.updateStatus(0.2f);
            Logger.debug("Init runtime fields", "LokEngine_start");

            RuntimeFields.init(new FrameBuilder(window), new Scene(), new GUICanvas(new Vector2i(0,0), windowResolution), new MouseStatus());

            Logger.debug("Init default font", "LokEngine_start");
            try {
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/Fonts/Default.ttf")));
            } catch (FontFormatException | IOException e) {
                Logger.error("Fail register default font!", "LokEngine_start");
                Logger.printException(e);
            }

            SplashScreen.updateStatus(0.3f);
            Logger.debug("Init shaders", "LokEngine_start");
            try {
                DefaultFields.defaultShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl", "#/resources/shaders/DefaultFragShader.glsl");
                DefaultFields.unknownSprite = SpriteLoader.loadSprite("#/resources/textures/unknown.png", 100 , DefaultFields.defaultShader);
                DefaultFields.displayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl", "#/resources/shaders/DisplayFragShader.glsl");
                DefaultFields.postProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl", "#/resources/shaders/BlurFragShader.glsl");
                DefaultFields.particlesShader = ShaderLoader.loadShader("#/resources/shaders/ParticleVertShader.glsl", "#/resources/shaders/ParticleFragShader.glsl");

                Shader.use(DefaultFields.postProcessingShader);
                window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);

                Shader.use(DefaultFields.displayShader);
                glUniform2f(glGetUniformLocation(Shader.currentShader.program, "screenSize"), window.getResolution().x, window.getResolution().y);
                window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);

                Shader.use(DefaultFields.particlesShader);
                MatrixCreator.PutMatrixInShader(DefaultFields.particlesShader,"ObjectModelMatrix",MatrixCreator.CreateModelMatrix(0,new Vector3f(0,0,0)));

                Shader.use(DefaultFields.defaultShader);
                window.getCamera().setFieldOfView(1);
            } catch (Exception e) {
                Logger.error("Fail load shaders!", "LokEngine_start");
                Logger.printException(e);
            }
            SplashScreen.updateStatus(0.4f);
            Logger.debug("Init OpenAL", "LokEngine_start");

            try{
                AL.create();
            }catch (Exception e){
                Logger.error("Fail init OpenAL!", "LokEngine_start");
                Logger.printException(e);
            }

            SplashScreen.updateStatus(0.5f);
            Logger.debug("Call user init method", "LokEngine_start");
            try {
                Init();
            } catch (Exception e) {
                Logger.warning("Fail user-init!", "LokEngine_start");
                Logger.printException(e);
            }

            Logger.debug("Init engine post processing action workers", "LokEngine_start");

            RuntimeFields.getFrameBuilder().addPostProcessingActionWorker(new BlurActionWorker(window));

            SplashScreen.updateStatus(0.9f);
            Logger.debug("Turn in main while!", "LokEngine_start");
            System.gc();
            isRun = true;
        } catch (Exception e) {
            Logger.error("Fail start engine!", "LokEngine_start");
            Logger.printException(e);
            System.exit(-1);
        }

        try {
            while (true) {
                try {
                    Update();
                } catch (Exception e) {
                    Logger.warning("Fail user-update!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                if (!isRun) break;

                try {
                    RuntimeFields.update();
                } catch (Exception e) {
                    Logger.warning("Fail update runtime fields!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                RuntimeFields.getScene().update();
                RuntimeFields.getCanvas().update();

                try {
                    nextFrame();
                } catch (Exception e) {
                    Logger.error("Fail build frame!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                window.update();
            }
        }catch (Exception e){
            Logger.error("Critical error in main while engine!", "LokEngine_runtime");
            Logger.printException(e);
            System.exit(-2);
        }

        try {
            Exit();
        } catch (Exception e) {
            Logger.warning("Fail user-exit!", "LokEngine_postRuntime");
            Logger.printException(e);
        }

        try {
            Prefs.save();
        }catch (Exception e) {
            Logger.warning("Fail save prefs!", "LokEngine_postRuntime");
            Logger.printException(e);
        }
        AL.destroy();
    }

    private void nextFrame(){
        Shader.use(DefaultFields.defaultShader);
        window.getCamera().updateView();
        RuntimeFields.getFrameBuilder().build();
    }

    public void startConsole() {
        try {
            Prefs.init();
        }catch (Exception e) {
            Logger.warning("Fail load in prefs!", "LokEngine_start");
            Logger.printException(e);
        }
        try {
            Logger.debug("Init runtime fields (Scene)", "LokEngine_start");
            RuntimeFields.init(null, new Scene(), null, null);
            Logger.debug("Call user init method", "LokEngine_start");
            try {
                Init();
            } catch (Exception e) {
                Logger.warning("Fail user-init!", "LokEngine_start");
                Logger.printException(e);
            }
            Logger.debug("Turn in main while!", "LokEngine_start");
            System.gc();
            isRun = true;
        } catch (Exception e) {
            Logger.error("Fail start engine!", "LokEngine_start");
            Logger.printException(e);
            System.exit(-1);
        }
        try {
            while (true) {
                try {
                    Update();
                } catch (Exception e) {
                    Logger.warning("Fail user-update!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                try {
                    RuntimeFields.update();
                } catch (Exception e) {
                    Logger.warning("Fail update runtime fields!", "LokEngine_runtime");
                    Logger.printException(e);
                }

                if (!isRun) break;

                RuntimeFields.getScene().update();
            }
        } catch (Exception e) {
            Logger.error("Critical error in main while engine!", "LokEngine_runtime");
            Logger.printException(e);
            System.exit(-2);
        }

        try {
            Exit();
        } catch (Exception e) {
            Logger.warning("Fail user-exit!", "LokEngine_postRuntime");
            Logger.printException(e);
        }

        try {
            Prefs.save();
        }catch (Exception e) {
            Logger.warning("Fail save prefs!", "LokEngine_postRuntime");
            Logger.printException(e);
        }
    }

    public void close(){
        if (window != null){
            window.close();
        }
        isRun = false;
    }

    public void start() {
        startApp(false, true, new Vector2i(512,512), "LokEngine application");
    }
    public void start(boolean windowFullscreen) { startApp(windowFullscreen, true, new Vector2i(512,512), "LokEngine application"); }
    public void start(boolean windowFullscreen, boolean vSync, Vector2i windowResolution) { startApp(windowFullscreen, vSync, windowResolution, "LokEngine application"); }
    public void start(boolean windowFullscreen, boolean vSync, Vector2i windowResolution, String windowTitle) {startApp(windowFullscreen, vSync, windowResolution, windowTitle); }

    public void Init(){}
    public void Update(){}
    public void Exit(){}
}
