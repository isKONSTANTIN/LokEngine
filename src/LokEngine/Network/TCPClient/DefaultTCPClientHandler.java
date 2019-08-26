package LokEngine.Network.TCPClient;

import LokEngine.Network.TCPTools;
import LokEngine.Tools.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class DefaultTCPClientHandler implements TCPClientHandler {

    private BufferedReader fromServer;
    private BufferedWriter toServer;

    @Override
    public void connected(BufferedReader fromServer, BufferedWriter toServer) {
        this.fromServer = fromServer;
        this.toServer = toServer;
    }

    public void sendMessage(String message){
        try {
            TCPTools.sendMessage(message,toServer);
        } catch (IOException e) {
            Logger.warning("Fail send message to server");
            Logger.printException(e);
        }
    }

    @Override
    public void disconnected() {}
}
