package ru.lokincompany.lokengine.network.tcp.server;

import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.executorservices.EngineExecutors;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServerMainRunnable implements Runnable {

    private ServerSocket serverSocket;
    private Class<? extends TCPServerHandler> serverHandler;
    private ArrayList<TCPServerParentRunnable> clientRunnables = new ArrayList<>();

    public TCPServerMainRunnable(ServerSocket serverSocket, Class<? extends TCPServerHandler> serverHandler) {
        this.serverSocket = serverSocket;
        this.serverHandler = serverHandler;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Socket socket = serverSocket.accept();

                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                TCPServerHandler handler = serverHandler.newInstance();
                handler.connected(clientRunnables.size(), fromClient, toClient, socket);

                TCPServerParentRunnable clientRunnable = new TCPServerParentRunnable(fromClient, toClient, handler, socket);
                EngineExecutors.longTasksExecutor.submit(clientRunnable);

                clientRunnables.add(clientRunnable);
            } catch (Throwable e) {
                if (serverSocket.isClosed())
                    break;

                Logger.warning("Fail make a connection", "LokEngine_TCPServer");
                Logger.printThrowable(e);
            }
        }
    }

    public void close() {
        for (TCPServerParentRunnable thread : clientRunnables) {
            thread.close();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
