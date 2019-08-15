package LokEngine.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Logger {

    public static boolean debugMessages = false;
    public static boolean warningMessages = true;
    public static boolean infoMessages = true;
    public static boolean errorMessages = true;
    private static int lastMessageType = 0;

    public static void debug(Object message){
        if (debugMessages){
            System.out.println("Debug: " + message);
            lastMessageType = 1;
        }
    }

    public static void debug(Object message, String tag){
        if (debugMessages){
            System.out.println("Debug." + tag + ": " + message);
            lastMessageType = 1;
        }
    }

    public static void info(Object message){
        if (infoMessages){
            System.out.println("Info: " + message);
            lastMessageType = 2;
        }
    }

    public static void info(Object message, String tag){
        if (infoMessages){
            System.out.println("Info." + tag + ": " + message);
            lastMessageType = 2;
        }
    }

    public static void warning(Object message){
        if (warningMessages){
            System.out.println("Warning: " + message);
            lastMessageType = 3;
        }
    }

    public static void warning(Object message, String tag){
        if (warningMessages){
            System.out.println("Warning." + tag + ": " + message);
            lastMessageType = 3;
        }
    }

    public static void error(Object message){
        if (errorMessages){
            System.out.println("Error: " + message);
            lastMessageType = 4;
        }
    }

    public static void error(Object message, String tag){
        if (errorMessages){
            System.out.println("Error." + tag + ": " + message);
            lastMessageType = 4;
        }
    }

    private static void printUnderTag(BufferedReader reader){

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                stringBuilder.append(line);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder);
    }

    public static void underMessage(Object message){
        BufferedReader reader = new BufferedReader(new StringReader(message.toString()));

        if (lastMessageType == 1){
            if (debugMessages){
                printUnderTag(reader);
            }
        }else if (lastMessageType == 2){
            if (infoMessages){
                printUnderTag(reader);
            }
        }else if (lastMessageType == 3){
            if (warningMessages){
                printUnderTag(reader);
            }
        }else if (lastMessageType == 4){
            if (errorMessages){
                printUnderTag(reader);
            }
        }
    }
}
