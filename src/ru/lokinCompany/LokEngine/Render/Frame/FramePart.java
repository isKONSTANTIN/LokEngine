package ru.lokinCompany.LokEngine.Render.Frame;

import ru.lokinCompany.LokEngine.Render.Enums.FramePartType;

public class FramePart {

    public FramePartType frameType;

    public FramePart(FramePartType frameType) {
        this.frameType = frameType;
    }

    public void partRender(BuilderProperties builderProperties) {
    }
}
