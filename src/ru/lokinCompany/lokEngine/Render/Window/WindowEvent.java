package ru.lokinCompany.lokEngine.Render.Window;

import ru.lokinCompany.lokEngine.Tools.Scripting.Scriptable;

public interface WindowEvent extends Scriptable {

    void execute(Window window, Object[] args);

    @Override
    default void execute() {
        execute(null, null);
    }

}
