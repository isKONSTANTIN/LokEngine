package LokEngine;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.BlurActionWorker;
import LokEngine.Render.Shader;
import LokEngine.Render.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.*;
import LokEngine.Tools.SplashScreen;
import LokEngine.Tools.SaveWorker.Prefs;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniform2f;

public class Application {
    public Window window;
    public Scene scene;
    public GUICanvas canvas;
    public DefaultFields defaultFields;
    public RuntimeFields runtimeFields;
    private boolean isRun;

    private void startApp(boolean windowFullscreen, boolean vSync, Vector2i windowResolution, String windowTitle) {
        try {
            Prefs.init();
        }catch (Exception e) {
            Logger.warning("Fail load in prefs!", "LokEngine_start");
            Logger.printException(e);
        }
        try {
            Logger.debug("Init glfw", "LokEngine_start");
            glfwInit();
            Logger.debug("Init window", "LokEngine_start");
            window = new Window();

            try {
                window.open(windowFullscreen, vSync, windowResolution);
                window.setTitle(windowTitle);

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
            defaultFields.defaultVertexScreenBuffer = BufferLoader.load(new float[]{-windowResolution.x / 2, windowResolution.y / 2, -windowResolution.x / 2, -windowResolution.y / 2, windowResolution.x / 2, -windowResolution.y / 2, windowResolution.x / 2, windowResolution.y / 2,});

            SplashScreen.updateStatus(0.15f);
            Logger.debug("Init default UV buffer", "LokEngine_start");
            defaultFields.defaultUVBuffer = BufferLoader.load(new float[] {
                    0,1,
                    0,0,
                    1,0,
                    1,1,
            });

            SplashScreen.updateStatus(0.2f);
            Logger.debug("Init runtime fields", "LokEngine_start");

            runtimeFields.init(new FrameBuilder(window), new Scene(), new GUICanvas(new Vector2i(0,0), windowResolution));
            scene = runtimeFields.getScene();
            canvas = runtimeFields.getCanvas();

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
                shadersInit();
            } catch (Exception e) {
                Logger.error("Fail load shaders!", "LokEngine_start");
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

            runtimeFields.getFrameBuilder().addPostProcessingActionWorker(new BlurActionWorker(window));

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
            mainWhile();
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
    }

    private void mainWhile(){
        while (true) {
            try {
                Update();
            } catch (Exception e) {
                Logger.warning("Fail user-update!", "LokEngine_runtime");
                Logger.printException(e);
            }

            if (!isRun) break;

            try {
                runtimeFields.update();
            } catch (Exception e) {
                Logger.warning("Fail update runtime fields!", "LokEngine_runtime");
                Logger.printException(e);
            }

            runtimeFields.getScene().update();

            try {
                nextFrame();
            } catch (Exception e) {
                Logger.error("Fail build frame!", "LokEngine_runtime");
                Logger.printException(e);
            }

            window.update();
        }
    }

    private void consoleMainWhile(){
        while (true) {
            try {
                Update();
            } catch (Exception e) {
                Logger.warning("Fail user-update!", "LokEngine_runtime");
                Logger.printException(e);
            }

            try {
                runtimeFields.update();
            } catch (Exception e) {
                Logger.warning("Fail update runtime fields!", "LokEngine_runtime");
                Logger.printException(e);
            }

            if (!isRun) break;

            runtimeFields.getScene().update();
        }
    }

    private void shadersInit() throws Exception {
        defaultFields.defaultShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl", "#/resources/shaders/DefaultFragShader.glsl");
        defaultFields.unknownTexture = TextureLoader.loadTexture("#/resources/textures/unknown.png");
        defaultFields.displayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl", "#/resources/shaders/DisplayFragShader.glsl");
        defaultFields.postProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl", "#/resources/shaders/BlurFragShader.glsl");
        defaultFields.particlesShader = ShaderLoader.loadShader("#/resources/shaders/ParticleVertShader.glsl", "#/resources/shaders/ParticleFragShader.glsl");

        Shader.use(defaultFields.postProcessingShader);
        window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);

        Shader.use(defaultFields.displayShader);
        glUniform2f(glGetUniformLocation(Shader.currentShader.program, "screenSize"), window.getResolution().x, window.getResolution().y);
        window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);

        Shader.use(defaultFields.particlesShader);
        MatrixCreator.PutMatrixInShader(defaultFields.particlesShader,"ObjectModelMatrix",MatrixCreator.CreateModelMatrix(0,new Vector3f(0,0,0)));

        Shader.use(defaultFields.defaultShader);
        window.getCamera().setFieldOfView(1);
    }

    private void nextFrame(){
        GL.createCapabilities();
        Shader.use(defaultFields.defaultShader);
        window.getCamera().updateView();
        runtimeFields.getFrameBuilder().build(RuntimeFields.getCanvas());
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
            runtimeFields.init(null, new Scene(), null);
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
            consoleMainWhile();
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
