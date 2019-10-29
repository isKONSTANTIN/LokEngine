package LokEngine.Network.Report;

import java.util.HashMap;

public class Report {
    public String title;
    public String errorBody;
    public String systemInfo;
    public HashMap<String, String> otherInfo;

    public Report(String title, Exception error, HashMap<String, String> otherInfo){
        this.title = title;
        this.errorBody = error.getClass().getName() + " - " + error.getMessage() + "\n" + LokEngine.Tools.Logger.stackTraceToString(error.getStackTrace());
        this.otherInfo = otherInfo;
        this.systemInfo = getSystemInfo();
    }

    public Report(String title, String errorBody, HashMap<String, String> otherInfo){
        this.title = title;
        this.errorBody = errorBody;
        this.otherInfo = otherInfo;
        this.systemInfo = getSystemInfo();
    }

    public Report(String title, Exception error){
        this(title,error,new HashMap<>());
    }

    public Report(String title, String errorBody){
        this(title,errorBody,new HashMap<>());
    }

    protected static String getSystemInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        long maxMemory = Runtime.getRuntime().maxMemory();
        boolean unlimitedMemory = maxMemory == Long.MAX_VALUE;

        stringBuilder.append("AvailableProcessors: ").append(Runtime.getRuntime().availableProcessors()).append("\n");
        stringBuilder.append("Free memory: ").append(Runtime.getRuntime().freeMemory() / 1000000f).append(" MB").append("\n");
        stringBuilder.append("Max memory: ").append(unlimitedMemory ? "unlimited" : maxMemory / 1000000f).append(unlimitedMemory ? "" : " MB").append("\n");
        stringBuilder.append("Total memory available to JVM: ").append(Runtime.getRuntime().totalMemory() / 1000000f).append(" MB");

        return stringBuilder.toString();
    }
}
