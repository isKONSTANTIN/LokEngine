package ru.lokinCompany.lokEngine.Tools.Input.AdditionalObjects;

import ru.lokinCompany.lokEngine.Tools.Scripting.Scriptable;

public interface MouseScrollScript extends Scriptable {

    void execute(double xoffset, double yoffset);

    @Override
    default void execute() {
        execute(0, 0);
    }
}
