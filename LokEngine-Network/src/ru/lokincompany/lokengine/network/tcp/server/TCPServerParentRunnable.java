package ru.lokincompany.lokengine.network.tcp.server;

import ru.lokincompany.lokengine.network.tcp.TCPTools;
import ru.lokincompany.lokengine.tools.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServerParentRunnable implements Runnable {

    BufferedReader fromClient;
    BufferedWriter toClient;
    TCPServerHandler handler;
    Socket clientSocket;

    final AtomicBoolean closed = new AtomicBoolean(false);

    public TCPServerParentRunnable(BufferedReader fromClient, BufferedWriter toClient, TCPServerHandler handler, Socket clientSocket) {
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.handler = handler;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String messageFrom = TCPTools.getMessage(fromClient);

                if (messageFrom != null) {
                    String messageTo = handler.acceptMessage(messageFrom);

                    if (messageTo != null)
                        TCPTools.sendMessage(messageTo, toClient);
                }

            } catch (SocketException e) {
                break;
            } catch (Throwable e) {
                Logger.warning("Fail process client!", "LokEngine_TCPServer-ClientThread");
                Logger.printThrowable(e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        closed.set(true);
        handler.disconnected();
    }

    public void close() {
        try {
            clientSocket.close();
            while (!closed.get()) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
