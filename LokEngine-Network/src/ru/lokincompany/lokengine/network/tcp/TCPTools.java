package ru.lokincompany.lokengine.network.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class TCPTools {

    public static String endLineDesignation = "__END__";

    public static String getMessage(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while (true) {
            String line = reader.readLine();
            if (line == null || line.equals(endLineDesignation)) {
                break;
            }

            stringBuilder.append(line).append("\n");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static void sendMessage(String message, BufferedWriter writer) throws IOException {
        writer.write(message + "\n" + endLineDesignation + "\n");
        writer.flush();
    }

}
