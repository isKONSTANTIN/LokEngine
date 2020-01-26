package ru.lokincompany.lokengine.gui.canvases;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.MouseRaycastStatus;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIMainCanvas extends GUICanvas {
    protected Window window;

    public GUIMainCanvas(Window window) {
        super(new Vector2i(), window.getResolution());
        this.window = window;

        properties.window = window;
        properties.mouseRaycastStatus = new MouseRaycastStatus(window.getMouse());
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(window.getFrameBuilder().getGUIPartsBuilder(), properties);

        properties.mouseRaycastStatus.lastFramePressed = window.getMouse().getPressedStatus();
        properties.mouseRaycastStatus.touched = false;
    }

    public void update() {
        update(window.getFrameBuilder().getGUIPartsBuilder(), properties);
    }
}
