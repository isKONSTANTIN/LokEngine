package LokEngine.Tools;

public class Logger {

    public static boolean debugMessages = false;
    public static boolean warningMessages = true;
    public static boolean infoMessages = true;

    public static void debug(Object message){
        if (debugMessages){
            System.out.println("Debug: " + message);
        }
    }

    public static void warning(Object message){
        if (warningMessages){
            System.out.println("Warning: " + message);
        }
    }

    public static void info(Object message){
        if (infoMessages){
            System.out.println("Info: " + message);
        }
    }

    public static void debug(Object message, String tag){
        if (debugMessages){
            System.out.println("Debug." + tag + ": " + message);
        }
    }

    public static void warning(Object message, String tag){
        if (warningMessages){
            System.out.println("Warning." + tag + ": " + message);
        }
    }

    public static void info(Object message, String tag){
        if (infoMessages){
            System.out.println("Info." + tag + ": " + message);
        }
    }
}
