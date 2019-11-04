package ru.lokinCompany.lokEngine.GUI.Canvases;

import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

public class GUIGridCanvas extends GUICanvas {

    ArrayList<Vector2i> objectsPos = new ArrayList<>();

    int columns;
    int lines;

    public GUIGridCanvas(Vector2i position, Vector2i size, int columns, int lines) {
        super(position, size);
        this.columns = columns > 0 ? columns : 1;
        this.lines = columns > 0 ? columns : 1;
    }

    public GUIGridCanvas(Vector2i position, Vector2i size) {
        this(position, size, 2, 2);
    }

    public void setColumnsAndLines(Vector2i columnsAndLines) {
        columns = columnsAndLines.x;
        lines = columnsAndLines.y;

        for (int i = 0; i < objects.size(); i++) {
            updatePosition(i);
            updateSize(i);
        }
    }

    public void setObjectsPos(int objectID, Vector2i newColumnAndLinePos) {
        Vector2i objectPos = objectsPos.get(objectID);
        objectPos.x = newColumnAndLinePos.x;
        objectPos.y = newColumnAndLinePos.y;

        updatePosition(objectID);
        updateSize(objectID);
    }

    public void updatePosition(int objectID) {
        GUIObject object = objects.get(objectID);
        Vector2i objectPos = objectsPos.get(objectID);

        object.setPosition(new Vector2i(
                Math.round(objectPos.x * (getSize().x / (float) columns)),
                Math.round(objectPos.y * (getSize().y / (float) lines))
        ));
    }

    public void updateSize(int objectID) {
        GUIObject object = objects.get(objectID);

        object.setSize(new Vector2i(
                Math.round(getSize().x / (float) columns),
                Math.round(getSize().y / (float) lines)
        ));
    }

    @Override
    public void removeObject(int id) {
        objects.remove(id);
        objectsPos.remove(id);
    }

    @Override
    public int addObject(GUIObject object) {
        return addObject(object, 0, 0);
    }

    public int addObject(GUIObject object, int column, int line) {
        int objectID = objects.size();
        objects.add(object);
        objectsPos.add(new Vector2i(column, line));
        updatePosition(objectID);
        updateSize(objectID);
        return objectID;
    }
}
