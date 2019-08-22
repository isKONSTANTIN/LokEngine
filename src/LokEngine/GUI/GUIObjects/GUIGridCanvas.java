package LokEngine.GUI.GUIObjects;

import LokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

public class GUIGridCanvas extends GUICanvas {

    public ArrayList<Float> percentSizesY;
    public ArrayList<Float> percentSizesX;
    public Vector2i linesAndColumns;

    public float defaultPercentX;
    public float defaultPercentY;

    public GUIGridCanvas(Vector2i position, Vector2i size, int lines, int columns) {
        super(position, size);

        percentSizesX = new ArrayList<>();
        percentSizesY = new ArrayList<>();

        linesAndColumns = new Vector2i(lines, columns);

        defaultPercentX = (float)size.x / (float)columns / (float)size.x;
        for (int i = 0; i < columns; i++){
            percentSizesX.add(defaultPercentX);
        }

        defaultPercentY = (float)size.y / (float)lines / (float)size.y;
        for (int i = 0; i < lines; i++){
            percentSizesY.add(defaultPercentY);
        }
    }

    public void addColumn(float percentSizeX) {
        linesAndColumns.y++;
        percentSizesX.add(percentSizeX);
    }
    public void addLine(float percentSizeY){
        linesAndColumns.x++;
        percentSizesY.add(percentSizeY);
    }

    public void addColumn() {
        linesAndColumns.y++;
        percentSizesX.add(defaultPercentX);
    }
    public void addLine(){
        linesAndColumns.x++;
        percentSizesY.add(defaultPercentY);
    }

    public void updatePositions(){
        int objectID = 0;
        Vector2i nextObjectPos = new Vector2i(position.x,position.y);

        for (int column = 0; column < linesAndColumns.y; column++) {
            for (int line = 0; line < linesAndColumns.x; line++) {
                if (objectID == objects.size()) break;

                GUIObject object = objects.get(objectID);

                Vector2i objectSize = new Vector2i(Math.round(size.x * percentSizesX.get(column)), Math.round(size.y * percentSizesY.get(line)));
                object.size.x = objectSize.x;
                object.size.y = objectSize.y;
                object.position.x = nextObjectPos.x;
                object.position.y = nextObjectPos.y;
                nextObjectPos.y += objectSize.y;

                objectID++;
            }
            nextObjectPos.x += size.x * percentSizesX.get(column);
            nextObjectPos.y = position.y;
        }
    }

    @Override
    public int putObject(GUIObject object){
        objects.add(object);
        updatePositions();

        return objects.size()-1;
    }
}
