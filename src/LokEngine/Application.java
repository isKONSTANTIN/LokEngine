package LokEngine;

import LokEngine.Render.Frame.FrameParts.PostProcessing.Workers.BlurActionWorker;
import LokEngine.Render.Window.Window;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.ApplicationRuntime;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Prefs;
import LokEngine.Tools.SplashScreen;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.openal.*;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.openal.ALC10.*;

public class Application {
    public Window window;
    public Scene scene;
    public ApplicationRuntime applicationRuntime;
    private boolean isRun;

    protected Thread myThread;
    private long openALDevice;
    private long openALContext;
    private ALCCapabilities alcCapabilities;
    private ALCapabilities alCapabilities;

    private static boolean staticInited;
    private static final Object key = new Object();

    private void startApp(boolean windowFullscreen, boolean vSync, Vector2i windowResolution, String windowTitle) {
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                    Logger.debug("Init default font", "LokEngine_start");
                    try {
                        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/Fonts/Default.ttf")));
                    } catch (FontFormatException | IOException e) {
                        Logger.error("Fail register default font!", "LokEngine_start");
                        Logger.printException(e);
                    }

                    SplashScreen.updateStatus(0.4f);
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
                        Init();
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
                    Exit();
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
            }
        });
        myThread.start();
    }

    private void mainWhile() {
        while (true) {
            try {
                Update();
            } catch (Exception e) {
                Logger.warning("Fail user-update!", "LokEngine_runtime");
                Logger.printException(e);
            }

            if (!isRun) break;

            try {
                applicationRuntime.update();
            } catch (Exception e) {
                Logger.warning("Fail update runtime fields!", "LokEngine_runtime");
                Logger.printException(e);
            }

            window.getCamera().updateAudioListener();

            scene.update(applicationRuntime, window.getFrameBuilder().getScenePartsBuilder());

            window.update();
        }
    }

    private void consoleMainWhile() {
        while (true) {
            try {
                Update();
            } catch (Exception e) {
                Logger.warning("Fail user-update!", "LokEngine_runtime");
                Logger.printException(e);
            }

            try {
                applicationRuntime.update();
            } catch (Exception e) {
                Logger.warning("Fail update runtime fields!", "LokEngine_runtime");
                Logger.printException(e);
            }

            if (!isRun) break;

            scene.update(applicationRuntime, null);
        }
    }

    public void startConsole() {
        try {
            Prefs.init();
        } catch (Exception e) {
            Logger.warning("Fail load in prefs!", "LokEngine_start");
            Logger.printException(e);
        }
        try {
            Logger.debug("Init Scene", "LokEngine_start");
            scene = new Scene();
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
        } catch (Exception e) {
            Logger.warning("Fail save prefs!", "LokEngine_postRuntime");
            Logger.printException(e);
        }
    }

    public void close() {
        if (window != null) {
            window.close();
        }
        isRun = false;
    }

    public void start() {
        startApp(false, true, new Vector2i(512, 512), "LokEngine application");
    }

    public void start(boolean windowFullscreen) {
        startApp(windowFullscreen, true, new Vector2i(512, 512), "LokEngine application");
    }

    public void start(boolean windowFullscreen, boolean vSync, Vector2i windowResolution) {
        startApp(windowFullscreen, vSync, windowResolution, "LokEngine application");
    }

    public void start(boolean windowFullscreen, boolean vSync, Vector2i windowResolution, String windowTitle) {
        startApp(windowFullscreen, vSync, windowResolution, windowTitle);
    }

    public void Init() {
    }

    public void Update() {
    }

    public void Exit() {
    }
}
