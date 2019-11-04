package ru.lokinCompany.LokEngine.Network.TCP.Handlers.Default;

import ru.lokinCompany.LokEngine.Tools.Scripting.Scriptable;

public interface TCPServerMethod extends Scriptable {

    String execute(String[] args);

    @Override
    default void execute() {
        execute(null);
    }

}
