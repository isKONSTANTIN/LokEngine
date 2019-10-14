package LokEngine.Network.TCP.Handlers;

import LokEngine.Network.TCP.Server.TCPServerHandler;
import LokEngine.Tools.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class SimpleTCPServerHandler implements TCPServerHandler {
    protected int userID;

    @Override
    public void connected(int userID, BufferedReader fromClient, BufferedWriter toClient, Socket socket) {
        this.userID = userID;
        Logger.info("New user session: " + userID, "Server");
    }

    @Override
    public String acceptMessage(String message) {
        Logger.info("message from " + userID + ": ", "Server");
        Logger.underMessage(message);
        return message;
    }

    @Override
    public void disconnected() {
        Logger.info("user session " + userID + " disconnected", "Server");
    }
}
