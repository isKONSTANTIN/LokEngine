package ru.lokincompany.lokengine.render.window;

import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface WindowEvent extends Scriptable {

    void execute(Window window, Object[] args);

    @Override
    default void execute() {
        execute(null, null);
    }

}
