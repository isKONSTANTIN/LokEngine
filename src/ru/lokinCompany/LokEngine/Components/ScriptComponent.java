package ru.lokinCompany.LokEngine.Components;

import ru.lokinCompany.LokEngine.Components.AdditionalObjects.ScriptSceneObject;
import ru.lokinCompany.LokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.LokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.LokEngine.Tools.ApplicationRuntime;

public class ScriptComponent extends Component {

    ScriptSceneObject script;

    public ScriptComponent() {
    }

    public ScriptComponent(ScriptSceneObject script) {
        this.script = script;
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        if (script != null)
            script.execute(source, applicationRuntime, partsBuilder);
    }
}
