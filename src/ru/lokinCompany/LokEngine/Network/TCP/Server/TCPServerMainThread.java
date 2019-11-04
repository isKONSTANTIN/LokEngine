package ru.lokinCompany.LokEngine.Network.TCP.Server;

import ru.lokinCompany.LokEngine.Tools.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServerMainThread extends Thread {

    private ServerSocket serverSocket;
    private TCPServerHandler serverHandler;
    private ArrayList<TCPServerParentThread> clientThreads = new ArrayList<>();

    public TCPServerMainThread(ServerSocket serverSocket, TCPServerHandler serverHandler) {
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

                TCPServerHandler handler = serverHandler.getClass().newInstance();
                handler.connected(clientThreads.size(), fromClient, toClient, socket);

                TCPServerParentThread clientThread = new TCPServerParentThread(fromClient, toClient, handler, socket);
                clientThread.start();

                clientThreads.add(clientThread);
            } catch (Exception e) {
                if (serverSocket.isClosed())
                    break;

                Logger.warning("Fail make a connection", "LokEngine_TCPServer");
                Logger.printException(e);
            }
        }
    }

    public void close() {
        for (TCPServerParentThread thread : clientThreads) {
            thread.close();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
