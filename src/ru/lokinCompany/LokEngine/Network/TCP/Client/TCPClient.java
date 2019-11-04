package ru.lokinCompany.LokEngine.Network.TCP.Client;

import ru.lokinCompany.LokEngine.Network.TCP.Handlers.Default.DefaultTCPClientHandler;
import ru.lokinCompany.LokEngine.Tools.Logger;

import java.io.*;
import java.net.Socket;

public class TCPClient {

    public Socket socket;
    private BufferedReader fromServer;
    private BufferedWriter toServer;
    public TCPClientHandler clientHandler;

    public TCPClient(String address, TCPClientHandler clientHandler, int port) throws IOException {
        socket = new Socket(address, port);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientHandler = clientHandler;
        clientHandler.connected(fromServer, toServer, socket);
    }

    public TCPClient(Socket socket, TCPClientHandler clientHandler) throws IOException {
        this.socket = socket;
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientHandler = clientHandler;
        clientHandler.connected(fromServer, toServer, socket);
    }

    public TCPClient(Socket socket) throws IOException {
        this(socket, new DefaultTCPClientHandler());
    }

    public TCPClient(String address, int port) throws IOException {
        this(address, new DefaultTCPClientHandler(), port);
    }

    public void close() {
        try {
            fromServer.close();
            toServer.close();
            socket.close();
        } catch (IOException e) {
            Logger.warning("Fail close Client!", "LokEngine_TCPClient");
            Logger.printException(e);
        }
    }

}
