package ru.lokincompany.lokengine.render.sceneenvironment.threed.components;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frameparts.ModelFramePart;
import ru.lokincompany.lokengine.render.model.Model;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.Component;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class ModelComponent extends Component {
    ModelFramePart framePart;
    Model model;

    public ModelComponent(Model model){
        this.model = model;
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        if (framePart == null)
            framePart = new ModelFramePart(model, source);

        partsBuilder.addPart(framePart);
    }

    @Override
    public String save() {
        return null;
    }

    @Override
    public Saveable load(String savedString) {
        return null;
    }
}
