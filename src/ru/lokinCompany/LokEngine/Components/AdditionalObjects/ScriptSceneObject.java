package ru.lokinCompany.LokEngine.Components.AdditionalObjects;

import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.LokEngine.Tools.ApplicationRuntime;
import ru.lokinCompany.LokEngine.Tools.Scripting.Scriptable;

public interface ScriptSceneObject extends Scriptable {

    void execute(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder);

    @Override
    default void execute() {
        execute(null, null, null);
    }

}
