package ru.lokincompany.lokengine.gui.additionalobjects;

import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface GUIObjectUpdateScript extends Scriptable {
    void execute(GUIObject object);

    @Override
    default void execute() {
        execute(null);
    }
}
