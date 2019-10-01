package LokEngine.Network.TCP.Server;

public interface TCPServerHandler {
    void connected(int userID);

    String acceptMessage(String message);

    void disconnected();
}
