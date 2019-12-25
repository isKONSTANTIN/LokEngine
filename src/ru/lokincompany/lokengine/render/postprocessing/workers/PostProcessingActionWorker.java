package ru.lokincompany.lokengine.render.postprocessing.workers;

import ru.lokincompany.lokengine.render.postprocessing.actions.PostProcessingAction;

import java.util.Vector;

public class PostProcessingActionWorker {

    public Vector<PostProcessingAction> postProcessingActions = new Vector<>();

    public void addPostProcessingAction(PostProcessingAction action) {
        postProcessingActions.add(action);
    }

    public int render(int sourceFrame) {
        return -1;
    }
}
