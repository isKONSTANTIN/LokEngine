package ru.lokincompany.lokengine.applications;

import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.Scene;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.executorservices.EngineExecutors;
import ru.lokincompany.lokengine.tools.saveworker.Prefs;

public class ApplicationConsole extends Application {

    public Scene scene;

    @Override
    public void start() {
        start(true);
    }

    public void start(boolean haveScene) {
        if (isRun) return;
        isRun = true;

        EngineExecutors.longTasksExecutor.submit(() -> {
            try {
                Prefs.init();
            } catch (Throwable e) {
                Logger.warning("Fail load in prefs!", "LokEngine_start");
                Logger.printThrowable(e);
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
                } catch (Throwable e) {
                    Logger.warning("Fail user-init!", "LokEngine_start");
                    Logger.printThrowable(e);
                }
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

            try {
                exitEvent();
            } catch (Throwable e) {
                Logger.warning("Fail user-exit!", "LokEngine_postRuntime");
                Logger.printThrowable(e);
            }

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

            applicationRuntime.update();

            if (!isRun) break;

            if (scene != null)
                scene.update(applicationRuntime, null);
        }
    }
}
