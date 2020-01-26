package ru.lokincompany.lokengine.gui.additionalobjects;

import ru.lokincompany.lokengine.tools.input.Mouse;

public class MouseRaycastStatus {

    public Mouse mouse;
    public boolean touched;
    public boolean lastFramePressed;

    public MouseRaycastStatus(Mouse mouse) {
        this.mouse = mouse;
    }

}
