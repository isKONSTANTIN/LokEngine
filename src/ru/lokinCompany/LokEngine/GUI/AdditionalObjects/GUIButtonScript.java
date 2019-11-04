package ru.lokinCompany.LokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.LokEngine.GUI.GUIObjects.GUIButton;
import ru.lokinCompany.LokEngine.Tools.Scripting.Scriptable;

public interface GUIButtonScript extends Scriptable {

    void execute(GUIButton button);

    @Override
    default void execute() {
        execute(null);
    }
}
