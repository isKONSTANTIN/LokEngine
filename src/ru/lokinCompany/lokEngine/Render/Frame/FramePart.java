package ru.lokinCompany.lokEngine.Render.Frame;

import ru.lokinCompany.lokEngine.Render.Enums.FramePartType;

public abstract class FramePart {

    public FramePartType frameType;

    public FramePart(FramePartType frameType) {
        this.frameType = frameType;
    }

    public abstract void partRender(BuilderProperties builderProperties);
}
