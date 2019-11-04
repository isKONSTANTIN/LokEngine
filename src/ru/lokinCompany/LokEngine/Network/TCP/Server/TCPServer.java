package ru.lokinCompany.LokEngine.Network.TCP.Server;

import ru.lokinCompany.LokEngine.Network.TCP.Handlers.Default.DefaultTCPServerHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

    private volatile ServerSocket serverSocket;
    public TCPServerHandler serverHandler;
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
