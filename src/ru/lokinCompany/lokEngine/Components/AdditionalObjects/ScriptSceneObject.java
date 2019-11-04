package ru.lokinCompany.lokEngine.Components.AdditionalObjects;

import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.lokEngine.Tools.Scripting.Scriptable;

public interface ScriptSceneObject extends Scriptable {

    void execute(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder);

    @Override
    default void execute() {
        execute(null, null, null);
    }

}
