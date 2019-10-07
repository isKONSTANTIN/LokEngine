package LokEngine.GUI.AdditionalObjects;

import LokEngine.GUI.GUIObjects.GUITextField;
import LokEngine.Tools.Scripting.Scriptable;

public interface GUITextFieldScript extends Scriptable {

    void execute(GUITextField textField);

    @Override
    default void execute() {
        execute(null);
    }
}
