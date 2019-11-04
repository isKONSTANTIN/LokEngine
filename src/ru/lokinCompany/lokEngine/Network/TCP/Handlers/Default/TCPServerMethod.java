package ru.lokinCompany.lokEngine.Network.TCP.Handlers.Default;

import ru.lokinCompany.lokEngine.Tools.Scripting.Scriptable;

public interface TCPServerMethod extends Scriptable {

    String execute(String[] args);

    @Override
    default void execute() {
        execute(null);
    }

}
