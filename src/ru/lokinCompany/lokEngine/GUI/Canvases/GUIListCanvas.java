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

    public int addObject(GUIObject object, boolean first){
        if (first) {
            objects.add(0, object);
            calculatePositions();
            return 0;
        }else{
            objects.add(object);
            calculatePositions();
            return objects.size() - 1;
        }
    }

    public void calculatePositions(){
        for (int i = 0; i < objects.size(); i++){
            GUIObject object = objects.get(i);
            object.setPosition(new Vector2i(0, (sizeObjects.y + gap) * i));
            object.setSize(sizeObjects);
        }
    }

    @Override
    public int addObject(GUIObject object) {
        return addObject(object,false);
    }

}
