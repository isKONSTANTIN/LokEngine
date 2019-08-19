package LokEngine.Render.Frame.FrameParts.PostProcessing.Workers;

import LokEngine.Render.Frame.FrameParts.PostProcessing.Actions.PostProcessingAction;
import LokEngine.Render.Window;

import java.util.Vector;

public class PostProcessingActionWorker {

    public Vector<PostProcessingAction> postProcessingActions = new Vector<>();

    public String getName(){ return "Post Processing Action Worker"; }

    public void addPostProcessingAction(PostProcessingAction action){ postProcessingActions.add(action); }

    public int render(int sourceFrame){
        return -1;
    }

}
