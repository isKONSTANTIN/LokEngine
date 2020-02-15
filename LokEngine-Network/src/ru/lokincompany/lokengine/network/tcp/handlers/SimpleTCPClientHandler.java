package ru.lokincompany.lokengine.network.tcp.handlers;

import ru.lokincompany.lokengine.network.tcp.TCPTools;
import ru.lokincompany.lokengine.network.tcp.client.TCPClientHandler;
import ru.lokincompany.lokengine.tools.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class SimpleTCPClientHandler implements TCPClientHandler {

    protected BufferedReader fromServer;
    protected BufferedWriter toServer;

    @Override
    public void connected(BufferedReader fromServer, BufferedWriter toServer, Socket socket) {
        this.fromServer = fromServer;
        this.toServer = toServer;
    }

    public String getMessage() {
        try {
            return TCPTools.getMessage(fromServer);
        } catch (IOException e) {
            Logger.warning("Fail get message from server", "LokEngine_SimpleTCPClientHandler");
            Logger.printThrowable(e);
        }
        return null;
    }

    public void sendMessage(String message) {
        try {
            TCPTools.sendMessage(message, toServer);
        } catch (IOException e) {
            Logger.warning("Fail send message to server", "LokEngine_SimpleTCPClientHandler");
            Logger.printThrowable(e);
        }
    }

    @Override
    public void disconnected() {
    }
}
