package ru.lokincompany.lokengine.render.frame;

import ru.lokincompany.lokengine.render.enums.FramePartType;

public abstract class FramePart {
    public FramePartType frameType;

    public boolean inited;

    public FramePart(FramePartType frameType) {
        this.frameType = frameType;
    }

    public abstract void partRender(RenderProperties renderProperties);

    public abstract void init(RenderProperties renderProperties);

}
