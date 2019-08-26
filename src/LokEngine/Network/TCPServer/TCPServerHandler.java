package LokEngine.Network.TCPServer;

public interface TCPServerHandler {
    void connected(int userID);
    String acceptMessage(String message);
    void disconnected();
}
