package LokEngine.Network.TCPClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface TCPClientHandler {
    void connected(BufferedReader fromServer, BufferedWriter toServer);
    void disconnected();
}
