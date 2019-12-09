package ru.lokincompany.lokengine.network.tcp.handlers.defaulthandles;

import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface TCPServerMethod extends Scriptable {

    String execute(String[] args);

    @Override
    default void execute() {
        execute(null);
    }

}
