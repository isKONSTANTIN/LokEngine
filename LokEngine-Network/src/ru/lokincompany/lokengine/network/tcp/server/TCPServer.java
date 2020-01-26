package ru.lokincompany.lokengine.network.tcp.server;

import ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles.DefaultTCPServerHandler;
import ru.lokincompany.lokengine.tools.executorservices.EngineExecutors;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

    public Class<? extends TCPServerHandler> serverHandler;
    private volatile ServerSocket serverSocket;
    private TCPServerMainRunnable runnable;

    public TCPServer(int port, Class<? extends TCPServerHandler> serverHandler) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverHandler = serverHandler;
        runnable = new TCPServerMainRunnable(serverSocket, serverHandler);
        EngineExecutors.longTasksExecutor.submit(runnable);
    }

    public TCPServer(int port) throws IOException {
        this(port, DefaultTCPServerHandler.class);
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runnable.close();
    }

}
