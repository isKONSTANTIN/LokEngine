package ru.lokincompany.lokengine.render.enums;

public enum FramePartType {
    Scene(0, DrawMode.Scene),
    GUI(1, DrawMode.Display);

    private final int index;
    private final DrawMode drawMode;

    FramePartType(int index, DrawMode drawMode) {
        this.index = index;
        this.drawMode = drawMode;
    }

    public int index() {
        return index;
    }

    public DrawMode drawMode() {
        return drawMode;
    }
}