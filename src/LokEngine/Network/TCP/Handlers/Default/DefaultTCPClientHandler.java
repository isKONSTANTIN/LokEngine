package LokEngine.Network.TCP.Handlers.Default;

import LokEngine.Network.TCP.Handlers.SimpleTCPClientHandler;
import static LokEngine.Network.TCP.Handlers.Default.DefaultTCPHandlersHeads.*;

public class DefaultTCPClientHandler extends SimpleTCPClientHandler {

    public String getPublicData(String name){
        sendMessage(publicDataHeadName + "\n" + name);
        return getMessage();
    }

    public String runServerMethod(String name, String[] args){
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args){
            stringBuilder.append(arg).append(";");
        }

        sendMessage(runServerMethodHeadName + "\n" + name + "\n" + stringBuilder.toString());
        return getMessage();
    }

    public String runServerMethod(String name){
        return runServerMethod(name, new String[0]);
    }

}
