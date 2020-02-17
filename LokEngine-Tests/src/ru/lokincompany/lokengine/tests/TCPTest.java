package ru.lokincompany.lokengine.tests;

import ru.lokincompany.lokengine.applications.ApplicationConsole;
import ru.lokincompany.lokengine.network.tcp.client.TCPClient;
import ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles.DefaultTCPClientHandler;
import ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles.DefaultTCPServerHandler;
import ru.lokincompany.lokengine.network.tcp.server.TCPServer;
import ru.lokincompany.lokengine.tools.Logger;

import java.io.IOException;

public class TCPTest extends ApplicationConsole {
    TCPServer server;
    TCPClient client;

    DefaultTCPClientHandler clientHandler;

    public TCPTest() {
        start(false);
    }

    public static void main(String[] args) {
        new TCPTest();
    }

    @Override
    protected void initEvent() {
        try {
            server = new TCPServer(5564);

            DefaultTCPServerHandler.methods.put("sayhello", args -> {
                String name = args[0];

                return "Hi, " + name;
            });

            client = new TCPClient("localhost", 5564);
            clientHandler = (DefaultTCPClientHandler) client.clientHandler;

            String out = clientHandler.runServerMethod("sayhello", new String[]{
                    "lokin135"
            });

            Logger.info(out, "Server response");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
