package LokEngine.GUI.Canvases;

import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.Render.Frame.FrameParts.GUI.GUICanvasFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.Vector;

public class GUICanvas extends GUIObject {

    Vector<GUIObject> objects = new Vector<>();
    PartsBuilder partsBuilder;

    public GUICanvas(Vector2i position, Vector2i size) {
        super(position, size);
        if (RuntimeFields.getFrameBuilder() == null){
            partsBuilder = new PartsBuilder(size);
        }else {
            partsBuilder = new PartsBuilder(RuntimeFields.getFrameBuilder().window.getResolution());
        }
    }

    public int addObject(GUIObject object){
        objects.add(object);
        return objects.size()-1;
    }

    @Override
    public void setSize(Vector2i size){
        partsBuilder.setResolution(size);
        super.setSize(size);
    }

    public void removeObject(int id){
        objects.remove(id);
    }
    public void removeAll(){ objects.clear(); }
    public GUIObject getObject(int id){
        return objects.get(id);
    }

    @Override
    public void update(PartsBuilder partsBuilder){
        for (GUIObject object : objects) {
            if (!object.hidden)
                object.update(this.partsBuilder);
        }
        partsBuilder.addPart(new GUICanvasFramePart(this.partsBuilder, getPosition()));
    }
}
