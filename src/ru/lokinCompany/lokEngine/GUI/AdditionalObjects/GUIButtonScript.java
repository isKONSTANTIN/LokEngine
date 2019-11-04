package ru.lokinCompany.lokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIButton;
import ru.lokinCompany.lokEngine.Tools.Scripting.Scriptable;

public interface GUIButtonScript extends Scriptable {

    void execute(GUIButton button);

    @Override
    default void execute() {
        execute(null);
    }
}
