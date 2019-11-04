package ru.lokinCompany.lokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUITextField;
import ru.lokinCompany.lokEngine.Tools.Scripting.Scriptable;

public interface GUITextFieldScript extends Scriptable {

    void execute(GUITextField textField);

    @Override
    default void execute() {
        execute(null);
    }
}
