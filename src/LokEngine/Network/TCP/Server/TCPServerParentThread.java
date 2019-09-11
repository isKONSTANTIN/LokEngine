package LokEngine.Network.TCP.Server;

import LokEngine.Network.TCP.TCPTools;
import LokEngine.Tools.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class TCPServerParentThread extends Thread {

    BufferedReader fromClient;
    BufferedWriter toClient;
    TCPServerHandler handler;
    Socket clientSocket;

    public TCPServerParentThread(BufferedReader fromClient, BufferedWriter toClient, TCPServerHandler handler, Socket clientSocket) {
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.handler = handler;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        while(true){
            try {
                String messageFrom = TCPTools.getMessage(fromClient);

                if (messageFrom != null){
                    String messageTo = handler.acceptMessage(messageFrom);

                    if (messageTo != null)
                        TCPTools.sendMessage(messageTo, toClient);
                }

            } catch (SocketException e) {
                break;
            } catch (Exception e) {
                Logger.warning("Fail process client!", "LokEngine_TCPServer-ClientThread");
                Logger.printException(e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        handler.disconnected();
    }

    public void close(){
        try {
            clientSocket.close();
            this.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
