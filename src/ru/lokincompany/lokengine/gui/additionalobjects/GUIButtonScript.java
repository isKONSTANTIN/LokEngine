package ru.lokincompany.lokengine.gui.additionalobjects;

import ru.lokincompany.lokengine.gui.guiobjects.GUIButton;
import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface GUIButtonScript extends Scriptable {

    void execute(GUIButton button);

    @Override
    default void execute() {
        execute(null);
    }
}
