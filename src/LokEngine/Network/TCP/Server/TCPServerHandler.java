package LokEngine.Network.TCP.Server;

import java.net.Socket;

public interface TCPServerHandler {
    void connected(int userID, Socket socket);

    String acceptMessage(String message);

    void disconnected();
}
