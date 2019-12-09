package ru.lokincompany.lokengine.network.tcp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public interface TCPServerHandler {
    void connected(int userID, BufferedReader fromClient, BufferedWriter toClient, Socket socket);

    String acceptMessage(String message);

    void disconnected();
}
