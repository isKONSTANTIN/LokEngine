package LokEngine.Applications;

import LokEngine.Tools.ApplicationRuntime;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

public abstract class Application {
    public ApplicationRuntime applicationRuntime;

    protected boolean isRun;
    protected Thread myThread;
    protected long openALDevice;
    protected long openALContext;
    protected ALCCapabilities alcCapabilities;
    protected ALCapabilities alCapabilities;

    public boolean isRun(){return isRun;}
    public void close() {
        isRun = false;
    }
    public abstract void start();
    public void initEvent() {}
    public void updateEvent() {}
    public void exitEvent() {}
}
