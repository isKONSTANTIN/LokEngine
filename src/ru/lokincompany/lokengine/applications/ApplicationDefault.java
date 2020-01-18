package ru.lokincompany.lokengine.applications;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
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
        start(windowFullscreen, allowResize, vSync, new Vector2i(512, 512));
    }

    public void start(boolean windowFullscreen, boolean allowResize, boolean vSync, Vector2i windowResolution) {
        start(windowFullscreen, allowResize, vSync, windowResolution, "LokEngine application");
    }

    public void start(boolean windowFullscreen, boolean allowResize, boolean vSync, Vector2i windowResolution, String windowTitle) {
        if (isRun) return;
        isRun = true;

        EngineExecutors.longTasksExecutor.submit(() -> {
            try {
                Logger.debug("Init glfw", "LokEngine_start");
                GLFW.init();
                Logger.debug("Init window", "LokEngine_start");
                window = new Window();
                try {
                    window.open(windowFullscreen, allowResize, vSync, windowResolution);
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

                Logger.debug("Init engine post processing action workers", "LokEngine_start");

                window.getFrameBuilder().addPostProcessingActionWorker(new BloomActionWorker(window));
                window.getFrameBuilder().addPostProcessingActionWorker(new BlurActionWorker(window));
                window.getFrameBuilder().addPostProcessingActionWorker(new ColorCorrectionActionWorker(window));

                SplashScreen.updateStatus(0.5f);
                Logger.debug("Call user init method", "LokEngine_start");
                try {
                    initEvent();
                } catch (Exception e) {
                    Logger.warning("Fail user-init!", "LokEngine_start");
                    Logger.printException(e);
                }

                SplashScreen.updateStatus(0.9f);
                Logger.debug("Turn in main while!", "LokEngine_start");
                System.gc();
            } catch (Exception e) {
                errorClose(e);
            }

            try {
                mainWhile();
            } catch (Exception e) {
                errorClose(e);
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

            window.close();

            try {
                Prefs.save();
            } catch (Exception e) {
                Logger.warning("Fail save prefs!", "LokEngine_postRuntime");
                Logger.printException(e);
            }

            isRun = false;
        });
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
