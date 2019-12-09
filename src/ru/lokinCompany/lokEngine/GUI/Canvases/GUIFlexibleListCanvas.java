package ru.lokinCompany.lokEngine.GUI.Canvases;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class GUIFlexibleListCanvas extends GUIListCanvas {
    public GUIFlexibleListCanvas(Vector2i position, Vector2i size) {
        super(position, size, null);
    }

    public GUIFlexibleListCanvas(Vector2i position, Vector2i size, int gap) {
        super(position, size, null, gap);
    }

    @Override
    public void calculatePositions(){
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
