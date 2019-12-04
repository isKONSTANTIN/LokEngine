package ru.lokinCompany.lokEngine.GUI.Canvases;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIPositions.GUIPosition;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIPositions.GUIPositionAlgorithms;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI.GUICanvasFramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;
import java.util.Vector;

public class GUICanvas extends GUIObject {

    protected ArrayList <GUIObject> objects = new ArrayList<>();
    protected ArrayList<GUIObject> ignoreOrderObjects = new ArrayList<>();
    protected GUICanvasFramePart framePart;

    public GUICanvas(Vector2i position, Vector2i size) {
        super(position, size);
        framePart = new GUICanvasFramePart(position, size);
        properties = new GUIObjectProperties(position, size, null);
    }

    public int addObject(GUIObject object) {
        objects.add(object);
        return objects.size() - 1;
    }

    public int addObject(GUIObject object, GUIPosition position) {
        object.setPosition(GUIPositionAlgorithms.getAlgorithm(this, position));
        return addObject(object);
    }

    public void setColor(Color color) {
        framePart.color = color;
    }

    public Color getColor() { return framePart.color; }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.partsBuilder.setResolution(size);
        framePart.size = size;
    }

    public void removeObject(int id) {
        objects.remove(id);
    }

    public void removeAll() {
        objects.clear();
    }

    public GUIObject getObject(int id) {
        return objects.get(id);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        for (GUIObject object : objects) {
            if (!object.hidden){
                if (!object.ignoreCanvasUpdateOrder)
                    object.update(framePart.partsBuilder, properties);
                else
                    ignoreOrderObjects.add(object);
            }
        }

        for (GUIObject object : ignoreOrderObjects){
            object.update(framePart.partsBuilder, properties);
        }

        ignoreOrderObjects.clear();

        partsBuilder.addPart(framePart);
    }
}
