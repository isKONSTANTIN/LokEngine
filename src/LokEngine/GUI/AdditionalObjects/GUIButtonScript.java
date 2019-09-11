package LokEngine.GUI.AdditionalObjects;

import LokEngine.GUI.GUIObjects.GUIButton;
import LokEngine.Tools.Scripting.Scriptable;

public interface GUIButtonScript extends Scriptable {

    void execute(GUIButton button);

    @Override
    default void execute() {
        execute(null);
    }
}
