package LokEngine.Network.TCPServer;

import LokEngine.Network.TCPTools;
import LokEngine.Tools.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServerThread extends Thread {

    private ServerSocket serverSocket;
    private TCPServerHandler serverHandler;
    private ArrayList<Thread> clientThreads = new ArrayList<>();

    public TCPServerThread(ServerSocket serverSocket, TCPServerHandler serverHandler){
        this.serverSocket = serverSocket;
        this.serverHandler = serverHandler;
    }

    @Override
    public void run(){
        while (!Thread.interrupted()){
            try {
                Socket socket = serverSocket.accept();

                TCPServerHandler handler = serverHandler.getClass().newInstance();
                handler.connected(clientThreads.size());

                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                Thread clientThread = new Thread(() -> {
                    while(!Thread.interrupted()){
                        try {
                            String messageFrom = TCPTools.getMessage(fromClient);
                            String messageTo = handler.acceptMessage(messageFrom);

                            if (messageTo != null)
                                TCPTools.sendMessage(messageTo, toClient);

                        } catch (Exception e) {
                            Logger.warning("Fail process client!", "LokEngine_TCPServer-ClientThread");
                            Logger.printException(e);
                        }
                    }

                    handler.disconnected();
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                clientThread.start();

                clientThreads.add(clientThread);
            } catch (Exception e) {
                Logger.warning("Fail make a connection", "LokEngine_TCPServer");
                Logger.printException(e);
            }
        }
        close();
    }

    public void close(){
        for (Thread thread : clientThreads){
            thread.interrupt();
        }
    }

}
