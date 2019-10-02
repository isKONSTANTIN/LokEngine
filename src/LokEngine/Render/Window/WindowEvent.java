package LokEngine.Render.Window;

import LokEngine.Tools.Scripting.Scriptable;

public interface WindowEvent extends Scriptable {

    void execute(Window window, Object[] args);

    @Override
    default void execute(){
        execute(null, null);
    }

}
