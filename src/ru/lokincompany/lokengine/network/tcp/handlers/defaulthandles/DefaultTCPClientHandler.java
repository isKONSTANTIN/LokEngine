package ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles;

import ru.lokincompany.lokengine.network.tcp.handlers.SimpleTCPClientHandler;
import ru.lokincompany.lokengine.tools.base64.Base64;

import static ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles.DefaultTCPHandlersHeads.*;

public class DefaultTCPClientHandler extends SimpleTCPClientHandler {

    public String getPublicData(String name) {
        sendMessage(publicDataHeadName + "\n" + name);
        return getMessage();
    }

    public String runServerMethod(String name, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) {
            stringBuilder.append(Base64.toBase64(arg)).append(";");
        }

        sendMessage(runServerMethodHeadName + "\n" + name + "\n" + stringBuilder.toString());
        return getMessage();
    }

    public String runServerMethod(String name) {
        return runServerMethod(name, new String[0]);
    }

}
