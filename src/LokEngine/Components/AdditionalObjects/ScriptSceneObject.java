package LokEngine.Components.AdditionalObjects;

import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Scripting.Scriptable;

public interface ScriptSceneObject extends Scriptable {

    void execute(SceneObject sceneObject);

    @Override
    default void execute(){
        execute(null);
    }

}
