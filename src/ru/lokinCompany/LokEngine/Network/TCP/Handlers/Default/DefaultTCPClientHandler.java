package ru.lokinCompany.LokEngine.Network.TCP.Handlers.Default;

import ru.lokinCompany.LokEngine.Network.TCP.Handlers.SimpleTCPClientHandler;
import ru.lokinCompany.LokEngine.Tools.Base64.Base64;

import static ru.lokinCompany.LokEngine.Network.TCP.Handlers.Default.DefaultTCPHandlersHeads.publicDataHeadName;
import static ru.lokinCompany.LokEngine.Network.TCP.Handlers.Default.DefaultTCPHandlersHeads.runServerMethodHeadName;

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
