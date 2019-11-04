package ru.lokinCompany.LokEngine.Render.Window;

import ru.lokinCompany.LokEngine.Tools.Scripting.Scriptable;

public interface WindowEvent extends Scriptable {

    void execute(Window window, Object[] args);

    @Override
    default void execute(){
        execute(null, null);
    }

}
