package ru.lokinCompany.lokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.lokEngine.Tools.Input.Mouse;

public class MouseRaycastStatus {

    public Mouse mouse;
    public boolean touched;
    public boolean lastFramePressed;

    public MouseRaycastStatus(Mouse mouse) {
        this.mouse = mouse;
    }

}
