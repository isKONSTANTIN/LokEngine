package ru.lokincompany.lokengine.gui.canvases;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPositionAlgorithms;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUICanvasFramePart;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

import java.util.ArrayList;

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

    protected void updateObjects(){
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
    }

    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties, boolean updateObjects) {
        super.update(partsBuilder, parentProperties);

        if (updateObjects) updateObjects();

        partsBuilder.addPart(framePart);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        update(partsBuilder, parentProperties, true);
    }
}
