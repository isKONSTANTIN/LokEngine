package LokEngine.Render.Frame;

import LokEngine.Render.Enums.FramePartType;

public class FramePart {

    public FramePartType frameType;

    public FramePart(FramePartType frameType){
        this.frameType = frameType;
    }

    public void partRender(BuilderProperties builderProperties){}
}
