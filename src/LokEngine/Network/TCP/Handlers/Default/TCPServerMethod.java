package LokEngine.Network.TCP.Handlers.Default;

import LokEngine.Tools.Scripting.Scriptable;

public interface TCPServerMethod extends Scriptable {

    String execute(String[] args);

    @Override
    default void execute() {
        execute(null);
    }

}
