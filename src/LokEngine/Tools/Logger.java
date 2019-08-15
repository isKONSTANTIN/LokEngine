package LokEngine.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Logger {

    public static boolean debugMessages = false;
    public static boolean warningMessages = true;
    public static boolean infoMessages = true;
    public static boolean errorMessages = true;
    public static boolean exceptionMessages = true;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static int lastMessageType = 0;

    public static void debug(Object message){
        if (debugMessages){
            System.out.println(ANSI_CYAN + "Debug: " + message + ANSI_RESET);
            lastMessageType = 1;
        }
    }

    public static void debug(Object message, String tag){
        if (debugMessages){
            System.out.println(ANSI_CYAN + "Debug." + tag + ": " + message + ANSI_RESET);
            lastMessageType = 1;
        }
    }

    public static void info(Object message){
        if (infoMessages){
            System.out.println(ANSI_WHITE + "Info: " + message + ANSI_RESET);
            lastMessageType = 2;
        }
    }

    public static void info(Object message, String tag){
        if (infoMessages){
            System.out.println(ANSI_WHITE + "Info." + tag + ": " + message + ANSI_RESET);
            lastMessageType = 2;
        }
    }

    public static void warning(Object message){
        if (warningMessages){
            System.out.println(ANSI_YELLOW + "Warning: " + message + ANSI_RESET);
            lastMessageType = 3;
        }
    }

    public static void warning(Object message, String tag){
        if (warningMessages){
            System.out.println(ANSI_YELLOW + "Warning." + tag + ": " + message + ANSI_RESET);
            lastMessageType = 3;
        }
    }

    public static void error(Object message){
        if (errorMessages){
            System.out.println(ANSI_RED + "Error: " + message + ANSI_RESET);
            lastMessageType = 4;
        }
    }

    public static void error(Object message, String tag){
        if (errorMessages){
            System.out.println(ANSI_RED + "Error." + tag + ": " + message + ANSI_RESET);
            lastMessageType = 4;
        }
    }

    private static void printUnderTag(BufferedReader reader){

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            boolean fistLine = true;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(fistLine ? " " : "\n ").append(line);

                if (fistLine){
                    fistLine = false;
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
    }

    public static void printException(Exception e){
        if (exceptionMessages){
            underMessage(e.getMessage());
            underMessage(Misc.stackTraceToString(e.getStackTrace()));
        }
    }

    public static void underMessage(String message){
        if (lastMessageType == 1){
            if (debugMessages){
                BufferedReader reader = new BufferedReader(new StringReader(ANSI_CYAN + message + ANSI_RESET));
                printUnderTag(reader);
            }
        }else if (lastMessageType == 2){
            if (infoMessages){
                BufferedReader reader = new BufferedReader(new StringReader(ANSI_WHITE + message + ANSI_RESET));
                printUnderTag(reader);
            }
        }else if (lastMessageType == 3){
            if (warningMessages){
                BufferedReader reader = new BufferedReader(new StringReader(ANSI_YELLOW + message + ANSI_RESET));
                printUnderTag(reader);
            }
        }else if (lastMessageType == 4){
            if (errorMessages){
                BufferedReader reader = new BufferedReader(new StringReader(ANSI_RED + message + ANSI_RESET));
                printUnderTag(reader);
            }
        }
    }
}
