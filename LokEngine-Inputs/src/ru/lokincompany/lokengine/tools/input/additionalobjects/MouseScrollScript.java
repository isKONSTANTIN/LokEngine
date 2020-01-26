package ru.lokincompany.lokengine.tools.input.additionalobjects;

import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface MouseScrollScript extends Scriptable {

    void execute(double xoffset, double yoffset);

    @Override
    default void execute() {
        execute(0, 0);
    }
}
