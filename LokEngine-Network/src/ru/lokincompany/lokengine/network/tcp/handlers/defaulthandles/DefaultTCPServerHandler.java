package ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles;

import ru.lokincompany.lokengine.network.tcp.handlers.SimpleTCPServerHandler;
import ru.lokincompany.lokengine.tools.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.HashMap;

import static ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles.DefaultTCPHandlersHeads.*;

public class DefaultTCPServerHandler extends SimpleTCPServerHandler {

    public static HashMap<String, TCPServerMethod> methods = new HashMap<>();
    public static HashMap<String, String> publicData = new HashMap<>();

    @Override
    public void connected(int userID, BufferedReader fromClient, BufferedWriter toClient, Socket socket) {
        this.userID = userID;
    }

    private String getPublicData(String name) {
        return publicData.containsKey(name) ? publicData.get(name) : errorHeadName;
    }

    private String runMethod(String name, String[] rawargs) {
        if (methods.containsKey(name)) {
            String[] args = new String[rawargs.length];
            for (int i = 0; i < rawargs.length; i++) {
                args[i] = Base64.fromBase64(rawargs[i]);
            }
            return methods.get(name).execute(args);
        }
        return errorHeadName;
    }

    @Override
    public String acceptMessage(String message) {
        String[] lines = message.split("\n");
        String returnMessage = errorHeadName;
        String head = lines[0];

        if (head.equals(publicDataHeadName)) {
            returnMessage = getPublicData(lines[1]);
        } else if (head.equals(runServerMethodHeadName)) {
            returnMessage = runMethod(lines[1], lines.length >= 3 ? lines[2].split(";") : new String[0]);
        }

        return returnMessage;
    }

    @Override
    public void disconnected() {

    }
}
