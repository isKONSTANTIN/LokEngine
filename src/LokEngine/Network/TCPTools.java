package LokEngine.Network;

import LokEngine.Tools.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class TCPTools {

    public static String endLineDesignation = "__END__";

     public static String getMessage(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while(true) {
            String line = reader.readLine();
            if (line.equals(endLineDesignation)) {
                break;
            }

            stringBuilder.append(line).append("\n");
        }

         stringBuilder.deleteCharAt(stringBuilder.length()-1);

        return stringBuilder.toString();
    }

    public static void sendMessage(String message, BufferedWriter writer) throws IOException {
        writer.write(message + "\n"+ endLineDesignation + "\n");
        writer.flush();
    }

}
