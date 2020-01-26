package ru.lokincompany.lokengine.applications;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import ru.lokincompany.lokengine.tools.Logger;

public abstract class Application {
    public ApplicationRuntime applicationRuntime;
    protected boolean isRun;
    protected long openALDevice;
    protected long openALContext;
    protected ALCCapabilities alcCapabilities;
    protected ALCapabilities alCapabilities;

    protected void errorClose(Exception e) {
        isRun = false;
        Logger.errorMessages = true;
        Logger.error("Critical error in engine! Sorry for that :C", "LokEngine");
        Logger.printException(e);

        System.exit(-1);
    }

    public boolean isRun() {
        return isRun;
    }

    public void close() {
        isRun = false;
    }

    public abstract void start();

    protected void initEvent() {
    }

    protected void updateEvent() {
    }

    protected void exitEvent() {
    }
}
