package LokEngine.Network.TCP.Server;

import LokEngine.Network.TCP.Handlers.Default.DefaultTCPServerHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

    private volatile ServerSocket serverSocket;
    public TCPServerHandler serverHandler;

    public TCPServer(int port, TCPServerHandler serverHandler) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverHandler = serverHandler;
        startServer();
    }

    public TCPServer(int port) throws IOException {
        this(port,new DefaultTCPServerHandler());
    }

    private void startServer(){
        new TCPServerThread(serverSocket, serverHandler).start();
    }

}
