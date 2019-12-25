package ru.lokincompany.lokengine.render.postprocessing.actions;

import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class PostProcessingAction {
    public Vector2i position;
    public Vector2i size;

    public PostProcessingAction(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
    }

    public void apply() {

    }

}
