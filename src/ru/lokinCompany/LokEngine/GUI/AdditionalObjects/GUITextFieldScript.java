package ru.lokinCompany.LokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.LokEngine.GUI.GUIObjects.GUITextField;
import ru.lokinCompany.LokEngine.Tools.Scripting.Scriptable;

public interface GUITextFieldScript extends Scriptable {

    void execute(GUITextField textField);

    @Override
    default void execute() {
        execute(null);
    }
}
