package LokEngine.Network.Report;

import LokEngine.Tools.Base64.Base64;

import java.util.HashMap;

public class Report {
    public String title;
    public String errorBody;
    public HashMap<String, String> systemInfo;
    public HashMap<String, String> otherInfo;

    public Report(String title, Exception error, HashMap<String, String> otherInfo){
        this.title = title;
        this.errorBody = Base64.toBase64(error.getClass().getName() + " - " + error.getMessage() + "\n" + LokEngine.Tools.Logger.stackTraceToString(error.getStackTrace()));
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

    protected static HashMap<String, String> getSystemInfo(){
        HashMap<String, String> hashMap = new HashMap();
        long maxMemory = Runtime.getRuntime().maxMemory();
        boolean unlimitedMemory = maxMemory == Long.MAX_VALUE;

        hashMap.put("AvailableProcessors", String.valueOf(Runtime.getRuntime().availableProcessors()));
        hashMap.put("Free memory", String.valueOf(Runtime.getRuntime().freeMemory() / 1000000f));
        hashMap.put("Max memory", String.valueOf(unlimitedMemory ? "unlimited" : maxMemory / 1000000f));
        hashMap.put("Total memory available to JVM", String.valueOf(Runtime.getRuntime().totalMemory() / 1000000f));

        return hashMap;
    }
}
