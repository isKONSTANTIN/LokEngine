package ru.lokincompany.lokengine.gui.canvases;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class GUIFlexibleListCanvas extends GUIListCanvas {
    public GUIFlexibleListCanvas(Vector2i position, Vector2i size) {
        super(position, size, null);
    }

    public GUIFlexibleListCanvas(Vector2i position, Vector2i size, int gap) {
        super(position, size, null, gap);
    }

    @Override
    public void calculatePositions() {
        int y = 0;
        for (GUIObject object : objects) {
            object.setPosition(new Vector2i(0, y));
            y += object.getSize().y + gap;
        }
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        calculatePositions();
    }
}
