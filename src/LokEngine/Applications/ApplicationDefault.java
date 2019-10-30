package LokEngine.Applications;

import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.BlurActionWorker;
import LokEngine.Render.Window.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.ApplicationRuntime;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Prefs;
import LokEngine.Tools.SplashScreen;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC10.alcCloseDevice;

public class ApplicationDefault extends Application {
    public Window window;
    public Scene scene;

    private static boolean staticInited;
    private static final Object key = new Object();

    @Override
    public void start(){
        start(false);
    }

    public void start(boolean windowFullscreen){
        start(windowFullscreen, true);
    }

    public void start(boolean windowFullscreen, boolean vSync){
        start(windowFullscreen, vSync, new Vector2i(512,512));
    }

    public void start(boolean windowFullscreen, boolean vSync, Vector2i windowResolution){
        start(windowFullscreen, vSync, windowResolution, "LokEngine application");
    }

    public void start(boolean windowFullscreen, boolean vSync, Vector2i windowResolution, String windowTitle) {
        myThread = new Thread(() -> {
            try {
                synchronized (key) {
                    if (!staticInited) {
                        try {
                            Prefs.init();
                        } catch (Exception e) {
                            Logger.warning("Fail load in prefs!", "LokEngine_start");
                            Logger.printException(e);
                        }

                        Logger.debug("Init glfw", "LokEngine_start");
                        glfwInit();
                        staticInited = true;
                    }
                }

                Logger.debug("Init window", "LokEngine_start");
                window = new Window();
                try {
                    window.open(windowFullscreen, vSync, windowResolution);
                    window.setTitle(windowTitle);
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
                Logger.debug("Init application runtime", "LokEngine_start");

                applicationRuntime = new ApplicationRuntime();
                applicationRuntime.init();

                SplashScreen.updateStatus(0.2f);
                Logger.debug("Init scene", "LokEngine_start");
                scene = new Scene();

                SplashScreen.updateStatus(0.3f);
                Logger.debug("Init OpenAL", "LokEngine_start");

                String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
                openALDevice = alcOpenDevice(defaultDeviceName);
                openALContext = ALC10.alcCreateContext(openALDevice, new int[]{0});
                alcMakeContextCurrent(openALContext);

                alcCapabilities = ALC.createCapabilities(openALDevice);
                alCapabilities = AL.createCapabilities(alcCapabilities);

                SplashScreen.updateStatus(0.5f);
                Logger.debug("Call user init method", "LokEngine_start");
                try {
                    initEvent();
                } catch (Exception e) {
                    Logger.warning("Fail user-init!", "LokEngine_start");
                    Logger.printException(e);
                }

                Logger.debug("Init engine post processing action workers", "LokEngine_start");

                window.getFrameBuilder().addPostProcessingActionWorker(new BlurActionWorker(window));

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
            } catch (Exception e) {
                Logger.error("Critical error in main while engine!", "LokEngine_runtime");
                Logger.printException(e);
                System.exit(-2);
            }

            Logger.debug("Exit from main while", "LokEngine_start");

            try {
                exitEvent();
            } catch (Exception e) {
                Logger.warning("Fail user-exit!", "LokEngine_postRuntime");
                Logger.printException(e);
            }

            alcDestroyContext(openALContext);
            alcCloseDevice(openALDevice);

            try {
                Prefs.save();
            } catch (Exception e) {
                Logger.warning("Fail save prefs!", "LokEngine_postRuntime");
                Logger.printException(e);
            }
        });
        myThread.start();
    }

    private void mainWhile() {
        while (true) {
            try {
                updateEvent();
            } catch (Exception e) {
                Logger.warning("Fail user-update!", "LokEngine_runtime");
                Logger.printException(e);
            }

            if (!isRun) break;

            applicationRuntime.update();
            window.getCamera().updateAudioListener();

            scene.update(applicationRuntime, window.getFrameBuilder().getScenePartsBuilder());

            window.update();
        }
    }
}
