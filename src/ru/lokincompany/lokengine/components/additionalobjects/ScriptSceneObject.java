package ru.lokincompany.lokengine.components.additionalobjects;

import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.scripting.Scriptable;

public interface ScriptSceneObject extends Scriptable {

    void execute(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder);

    @Override
    default void execute() {
        execute(null, null, null);
    }

}
