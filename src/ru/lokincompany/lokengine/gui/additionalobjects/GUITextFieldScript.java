package ru.lokincompany.lokengine.gui.additionalobjects;


import ru.lokincompany.lokengine.gui.guiobjects.GUITextField;
import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface GUITextFieldScript extends Scriptable {

    void execute(GUITextField textField);

    @Override
    default void execute() {
        execute(null);
    }
}
