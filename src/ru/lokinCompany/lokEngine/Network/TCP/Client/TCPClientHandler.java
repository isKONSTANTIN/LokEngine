package ru.lokinCompany.lokEngine.Network.TCP.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public interface TCPClientHandler {
    void connected(BufferedReader fromServer, BufferedWriter toServer, Socket socket);

    void disconnected();
}
