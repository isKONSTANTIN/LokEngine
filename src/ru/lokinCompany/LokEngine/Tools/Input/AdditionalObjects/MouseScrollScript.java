package ru.lokinCompany.LokEngine.Tools.Input.AdditionalObjects;

import ru.lokinCompany.LokEngine.Tools.Scripting.Scriptable;

public interface MouseScrollScript extends Scriptable {

    void execute(double xoffset, double yoffset);

    @Override
    default void execute() {
        execute(0, 0);
    }
}
