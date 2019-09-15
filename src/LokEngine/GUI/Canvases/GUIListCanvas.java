package LokEngine.GUI.Canvases;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIListCanvas extends GUICanvas {

    Vector2i sizeObjects;

    public GUIListCanvas(Vector2i position, Vector2i size, Vector2i sizeObjects) {
        super(position, size);
        this.sizeObjects = sizeObjects;
    }

    @Override
    public int addObject(GUIObject object){

        if (sizeObjects.y * objects.size()+1 > this.getSize().y){
            return -1;
        }

        object.setPosition(new Vector2i(getPosition().x, getPosition().y + sizeObjects.y * objects.size()));
        object.setSize(sizeObjects);

        objects.add(object);
        return objects.size()-1;
    }

}
