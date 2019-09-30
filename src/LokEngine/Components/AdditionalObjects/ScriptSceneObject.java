package LokEngine.Components.AdditionalObjects;

import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.ApplicationRuntime;
import LokEngine.Tools.Scripting.Scriptable;

public interface ScriptSceneObject extends Scriptable {

    void execute(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder);

    @Override
    default void execute(){
        execute(null,null,null);
    }

}
