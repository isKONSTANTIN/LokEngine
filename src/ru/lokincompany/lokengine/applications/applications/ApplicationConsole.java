package ru.lokincompany.lokengine.applications.applications;

import ru.lokincompany.lokengine.sceneenvironment.Scene;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Prefs;

public class ApplicationConsole extends Application {

    public Scene scene;

    @Override
    public void start() {
        start(true);
    }

    public void start(boolean haveScene) {
        if (myThread != null) return;
        myThread = new Thread(() -> {
            try {
                Prefs.init();
            } catch (Exception e) {
                Logger.warning("Fail load in prefs!", "LokEngine_start");
                Logger.printException(e);
            }

            Logger.debug("Init application runtime", "LokEngine_start");

            applicationRuntime = new ApplicationRuntime();
            applicationRuntime.init();

            try {
                if (haveScene) {
                    Logger.debug("Init Scene", "LokEngine_start");
                    scene = new Scene();
                }
                Logger.debug("Call user init method", "LokEngine_start");
                try {
                    initEvent();
                } catch (Exception e) {
                    Logger.warning("Fail user-init!", "LokEngine_start");
                    Logger.printException(e);
                }
                Logger.debug("Turn in main while!", "LokEngine_start");
                System.gc();
                isRun = true;
            } catch (Exception e) {
                errorClose(e);
            }
            try {
                mainWhile();
            } catch (Exception e) {
                errorClose(e);
            }

            try {
                exitEvent();
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

            applicationRuntime.update();

            if (!isRun) break;

            if (scene != null)
                scene.update(applicationRuntime, null);
        }
    }
}
