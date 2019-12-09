package ru.lokincompany.lokengine.applications.applications;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import ru.lokincompany.lokengine.network.report.Report;
import ru.lokincompany.lokengine.network.report.URLReportSender;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Prefs;

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
        try {
            Prefs.init();
        } catch (Exception e) {
            Logger.warning("Fail load in prefs!", "LokEngine_Application");
            Logger.printException(e);
        }
    }

    protected boolean isRun;
    protected Thread myThread;
    protected long openALDevice;
    protected long openALContext;
    protected ALCCapabilities alcCapabilities;
    protected ALCapabilities alCapabilities;

    public boolean isRun() {
        return isRun;
    }

    public void close() {
        isRun = false;
    }

    static void errorClose(Exception e) {
        Logger.errorMessages = true;
        Logger.error("Critical error in engine! Sorry for that :C", "ru/lokinCompany/lokEngine");
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

    protected void initEvent() {}

    protected void updateEvent() {}

    protected void exitEvent() {}
}
