package ru.lokinCompany.lokEngine.Components;

import ru.lokinCompany.lokEngine.Components.AdditionalObjects.ScriptSceneObject;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.ApplicationRuntime;

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
