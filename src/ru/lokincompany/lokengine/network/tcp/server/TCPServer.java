package ru.lokincompany.lokengine.network.tcp.server;

import ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles.DefaultTCPServerHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

    public TCPServerHandler serverHandler;
    private volatile ServerSocket serverSocket;
    private TCPServerMainThread thread;

    public TCPServer(int port, TCPServerHandler serverHandler) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverHandler = serverHandler;
        thread = new TCPServerMainThread(serverSocket, serverHandler);
        thread.start();
    }

    public TCPServer(int port) throws IOException {
        this(port, new DefaultTCPServerHandler());
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.close();
    }

}
