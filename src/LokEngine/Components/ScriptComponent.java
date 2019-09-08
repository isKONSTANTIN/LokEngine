package LokEngine.Components;

import LokEngine.Components.AdditionalObjects.ScriptSceneObject;
import LokEngine.SceneEnvironment.SceneObject;

public class ScriptComponent extends Component{

    ScriptSceneObject script;

    public ScriptComponent(){}

    public ScriptComponent(ScriptSceneObject script){
        this.script = script;
    }

    public void update(SceneObject source){
        if (script != null)
            script.execute(source);
    }



}
