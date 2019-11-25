package ru.lokinCompany.lokEngine.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Logger {

    public static boolean debugMessages = false;
    public static boolean warningMessages = true;
    public static boolean infoMessages = true;
    public static boolean errorMessages = true;
    public static boolean exceptionMessages = true;

    public static boolean useColors = true;

    public static String ANSIReset = "\u001B[0m";
    public static String ANSIBlackColor = "\u001B[30m";
    public static String ANSIRedColor = "\u001B[31m";
    public static String ANSIGreenColor = "\u001B[32m";
    public static String ANSIYellowColor = "\u001B[33m";
    public static String ANSIBlueColor = "\u001B[34m";
    public static String ANSIPurpleColor = "\u001B[35m";
    public static String ANSICyanColor = "\u001B[36m";
    public static String ANSIWhiteColor = "\u001B[37m";

    private static int lastMessageType = 0;

    public static void debug(Object message) {
        if (debugMessages) {
            if (useColors) {
                System.out.println(ANSICyanColor + "[T-" + Thread.currentThread().getId() + "] " + "Debug: " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Debug: " + message);
            }

            lastMessageType = 1;
        }
    }

    public static void debug(Object message, String tag) {
        if (debugMessages) {
            if (useColors) {
                System.out.println(ANSICyanColor + "[T-" + Thread.currentThread().getId() + "] " + "Debug." + tag + ": " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Debug." + tag + ": " + message);
            }
            lastMessageType = 1;
        }
    }

    public static void info(Object message) {
        if (infoMessages) {
            if (useColors) {
                System.out.println(ANSIWhiteColor + "[T-" + Thread.currentThread().getId() + "] " + "Info: " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Info: " + message);
            }

            lastMessageType = 2;
        }
    }

    public static void info(Object message, String tag) {
        if (infoMessages) {
            if (useColors) {
                System.out.println(ANSIWhiteColor + "[T-" + Thread.currentThread().getId() + "] " + "Info." + tag + ": " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Info." + tag + ": " + message);
            }
            lastMessageType = 2;
        }
    }

    public static void warning(Object message) {
        if (warningMessages) {
            if (useColors) {
                System.out.println(ANSIYellowColor + "[T-" + Thread.currentThread().getId() + "] " + "Warning: " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Warning: " + message);
            }
            lastMessageType = 3;
        }
    }

    public static void warning(Object message, String tag) {
        if (warningMessages) {
            if (useColors) {
                System.out.println(ANSIYellowColor + "[T-" + Thread.currentThread().getId() + "] " + "Warning." + tag + ": " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Warning." + tag + ": " + message);
            }

            lastMessageType = 3;
        }
    }

    public static void error(Object message) {
        if (errorMessages) {
            if (useColors) {
                System.out.println(ANSIRedColor + "[T-" + Thread.currentThread().getId() + "] " + "Error: " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Error: " + message);
            }

            lastMessageType = 4;
        }
    }

    public static void error(Object message, String tag) {
        if (errorMessages) {
            if (useColors) {
                System.out.println(ANSIRedColor + "[T-" + Thread.currentThread().getId() + "] " + "Error." + tag + ": " + message + ANSIReset);
            } else {
                System.out.println("[T-" + Thread.currentThread().getId() + "] " + "Error." + tag + ": " + message);
            }

            lastMessageType = 4;
        }
    }

    private static void printUnderTag(BufferedReader reader) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            boolean fistLine = true;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(fistLine ? " " : "\n ").append(line);

                if (fistLine) {
                    fistLine = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
    }

    public static void printException(Exception e) {
        if (exceptionMessages) {
            underMessage(e.getClass().getName() + " - " + e.getMessage());
            underMessage(stackTraceToString(e.getStackTrace()));
        }
    }

    public static String stackTraceToString(StackTraceElement[] elements) {
        StringBuilder StackTrace = new StringBuilder();

        for (StackTraceElement element : elements) {
            StackTrace
                    .append("Class name: '")
                    .append(element.getClassName())
                    .append("' Method name: '")
                    .append(element.getMethodName())
                    .append("' - ")
                    .append(element.getLineNumber())
                    .append(" line\n");
        }
        return StackTrace.toString();
    }

    public static void underMessage(String message) {
        if (lastMessageType == 1) {
            if (debugMessages) {

                BufferedReader reader;
                if (useColors) {
                    reader = new BufferedReader(new StringReader(ANSICyanColor + message + ANSIReset));
                } else {
                    reader = new BufferedReader(new StringReader(message));
                }

                printUnderTag(reader);
            }
        } else if (lastMessageType == 2) {
            if (infoMessages) {

                BufferedReader reader;
                if (useColors) {
                    reader = new BufferedReader(new StringReader(ANSIWhiteColor + message + ANSIReset));
                } else {
                    reader = new BufferedReader(new StringReader(message));
                }

                printUnderTag(reader);
            }
        } else if (lastMessageType == 3) {
            if (warningMessages) {

                BufferedReader reader;
                if (useColors) {
                    reader = new BufferedReader(new StringReader(ANSIYellowColor + message + ANSIReset));
                } else {
                    reader = new BufferedReader(new StringReader(message));
                }

                printUnderTag(reader);
            }
        } else if (lastMessageType == 4) {
            if (errorMessages) {

                BufferedReader reader;
                if (useColors) {
                    reader = new BufferedReader(new StringReader(ANSIRedColor + message + ANSIReset));
                } else {
                    reader = new BufferedReader(new StringReader(message));
                }

                printUnderTag(reader);
            }
        }
    }
}
