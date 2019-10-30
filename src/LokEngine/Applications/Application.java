package LokEngine.Applications;

import LokEngine.Network.Report.Report;
import LokEngine.Network.Report.URLReportSender;
import LokEngine.Tools.ApplicationRuntime;
import LokEngine.Tools.Logger;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class Application {
    public ApplicationRuntime applicationRuntime;
    private static URLReportSender reportSender;

    static {
        try {
            reportSender = new URLReportSender(new URL("https://lokincompany.ru/bugs/new/uploadReport.php"));
        } catch (MalformedURLException e) {
            Logger.debug("Fail create url", "LokEngine_Application_ReportSender");
            Logger.printException(e);
        }
    }

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

    static void errorClose(Exception e){
        Logger.errorMessages = true;
        Logger.error("Critical error in engine! Sorry for that :C", "LokEngine");
        Logger.printException(e);

        if (reportSender != null) {
            Logger.error("Sending bug report...");
            try {
                reportSender.sendReportOverHttps(new Report("Error close call", e));
                Logger.error("Done! Thanks.");
            } catch (Exception ex) {
                Logger.debug("Fail send report", "LokEngine_Application_ReportSender");
                Logger.printException(e);
            }
        }

        System.exit(-1);
    }

    public abstract void start();
    public void initEvent() {}
    public void updateEvent() {}
    public void exitEvent() {}
}
