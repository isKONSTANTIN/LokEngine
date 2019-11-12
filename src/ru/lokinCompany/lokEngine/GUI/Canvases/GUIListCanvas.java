package ru.lokinCompany.lokEngine.GUI.Canvases;

import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class GUIListCanvas extends GUICanvas {

    Vector2i sizeObjects;
    int gap;

    public GUIListCanvas(Vector2i position, Vector2i size, Vector2i sizeObjects) {
        this(position, size, sizeObjects, 0);
    }

    public GUIListCanvas(Vector2i position, Vector2i size, Vector2i sizeObjects, int gap) {
        super(position, size);
        this.sizeObjects = sizeObjects;
        this.gap = gap;
    }

    @Override
    public int addObject(GUIObject object) {
        object.setPosition(new Vector2i(getPosition().x, getPosition().y + (sizeObjects.y + gap) * objects.size()));
        object.setSize(sizeObjects);

        objects.add(object);
        return objects.size() - 1;
    }

}
