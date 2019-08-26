package LokEngine.Network.TCPServer;

import LokEngine.Tools.Logger;

public class DefaultTCPServerHandler implements TCPServerHandler {

    int userID;

    @Override
    public void connected(int userID) {
        this.userID = userID;
        Logger.info("New user session: " + userID, "Server");
    }

    @Override
    public String acceptMessage(String message) {
        Logger.info("message from " + userID + ": ", "Server");
        Logger.underMessage(message);
        return message;
    }

    @Override
    public void disconnected() {
        Logger.info("user session " + userID + " disconnected", "Server");
    }
}
