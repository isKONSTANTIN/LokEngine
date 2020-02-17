package ru.lokincompany.lokengine.applications;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import ru.lokincompany.lokengine.gui.canvases.GUIMainCanvas;
import ru.lokincompany.lokengine.render.GLFW;
import ru.lokincompany.lokengine.render.SplashScreen;
import ru.lokincompany.lokengine.render.postprocessing.workers.bloom.BloomActionWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.blur.BlurActionWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.colorcorrection.ColorCorrectionActionWorker;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.Scene;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.executorservices.EngineExecutors;
import ru.lokincompany.lokengine.tools.saveworker.Prefs;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.openal.ALC10.*;

public class ApplicationDefault extends Application {
    public Window window;
    public GUIMainCanvas canvas;
    public Scene scene;

    @Override
    public void start() {
        start(false);
    }

    public void start(boolean windowFullscreen) {
        start(windowFullscreen, false);
    }

    public void start(boolean windowFullscreen, boolean allowResize) {
        start(windowFullscreen, allowResize, true);
    }

    public void start(boolean windowFullscreen, boolean allowResize, boolean vSync) {
        start(windowFullscreen, allowResize, vSync, 0, new Vector2i(512, 512));
    }

    public void start(boolean windowFullscreen, boolean allowResize, boolean vSync, int samples) {
        start(windowFullscreen, allowResize, vSync, samples, new Vector2i(512, 512));
    }

    public void start(boolean windowFullscreen, boolean allowResize, boolean vSync, int samples, Vector2i windowResolution) {
        start(windowFullscreen, allowResize, vSync, samples, windowResolution, "LokEngine application");
    }

    public void start(boolean windowFullscreen, boolean allowResize, boolean vSync, int samples, Vector2i windowResolution, String windowTitle) {
        if (isRun) return;
        isRun = true;

        EngineExecutors.longTasksExecutor.submit(() -> {
            try {
                Logger.debug("Init glfw", "LokEngine_start");
                GLFW.init();
                Logger.debug("Init window", "LokEngine_start");
                window = new Window();
                try {
                    window.open(windowFullscreen, allowResize, vSync, samples, windowResolution);
                    window.setTitle(windowTitle);
                } catch (Throwable e) {
                    Logger.error("Fail open window!", "LokEngine_start");
                    Logger.printThrowable(e);
                }
                try {
                    SplashScreen.init(window);
                } catch (Throwable e) {
                    Logger.error("Fail init splash screen!", "LokEngine_start");
                    Logger.printThrowable(e);
                }

                SplashScreen.updateStatus(0.1f);
                Logger.debug("Init main canvas", "LokEngine_start");

                canvas = new GUIMainCanvas(window);

                SplashScreen.updateStatus(0.2f);
                Logger.debug("Init application runtime", "LokEngine_start");

                applicationRuntime = new ApplicationRuntime();
                applicationRuntime.init();

                SplashScreen.updateStatus(0.3f);
                Logger.debug("Init scene", "LokEngine_start");
                scene = new Scene();

                SplashScreen.updateStatus(0.4f);
                Logger.debug("Init OpenAL", "LokEngine_start");

                String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
                openALDevice = alcOpenDevice(defaultDeviceName);
                openALContext = ALC10.alcCreateContext(openALDevice, new int[]{0});
                alcMakeContextCurrent(openALContext);

                alcCapabilities = ALC.createCapabilities(openALDevice);
                alCapabilities = AL.createCapabilities(alcCapabilities);

                SplashScreen.updateStatus(0.5f);
                Logger.debug("Init engine post processing action workers", "LokEngine_start");

                window.getFrameBuilder().addPostProcessingActionWorker(new BloomActionWorker(window));
                window.getFrameBuilder().addPostProcessingActionWorker(new BlurActionWorker(window));
                window.getFrameBuilder().addPostProcessingActionWorker(new ColorCorrectionActionWorker(window));

                SplashScreen.updateStatus(0.6f);
                Logger.debug("Call user init method", "LokEngine_start");
                try {
                    initEvent();
                } catch (Throwable e) {
                    Logger.warning("Fail user-init!", "LokEngine_start");
                    Logger.printThrowable(e);
                }

                SplashScreen.updateStatus(0.9f);
                Logger.debug("Turn in main while!", "LokEngine_start");
                System.gc();
            } catch (Throwable e) {
                errorClose(e);
            }

            try {
                mainWhile();
            } catch (Throwable e) {
                errorClose(e);
            }

            Logger.debug("Exit from main while", "LokEngine_start");

            try {
                exitEvent();
            } catch (Throwable e) {
                Logger.warning("Fail user-exit!", "LokEngine_postRuntime");
                Logger.printThrowable(e);
            }

            alcDestroyContext(openALContext);
            alcCloseDevice(openALDevice);

            window.close();

            try {
                Prefs.save();
            } catch (Throwable e) {
                Logger.warning("Fail save prefs!", "LokEngine_postRuntime");
                Logger.printThrowable(e);
            }

            isRun = false;
        });
    }

    private void mainWhile() {
        while (true) {
            try {
                updateEvent();
            } catch (Throwable e) {
                Logger.warning("Fail user-update!", "LokEngine_runtime");
                Logger.printThrowable(e);
            }

            if (!isRun) break;

            applicationRuntime.update();
            window.getCamera().updateAudioListener();

            scene.update(applicationRuntime, window.getFrameBuilder().getScenePartsBuilder());

            canvas.update();

            window.update();
        }
    }
}
