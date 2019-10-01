package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Render.Frame.FrameParts.GUI.GUIGraphFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

public class GUIGraph extends GUIObject {

    ArrayList<Float> points = new ArrayList<>();

    float maxHeight;
    float minHeight;
    int maxPoints;

    GUIGraphFramePart framePart;

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight, int maxPoints, Color color) {
        super(position, size);
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxPoints = maxPoints > 0 ? maxPoints : 1;

        framePart = new GUIGraphFramePart(position, size, points, maxHeight, minHeight, maxPoints, color);
    }

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight, int maxPoints) {
        this(position, size, maxHeight, minHeight, maxPoints, new Color(1, 1, 1, 1));
    }

    public GUIGraph(Vector2i position, Vector2i size, float maxHeight, float minHeight) {
        this(position, size, maxHeight, minHeight, size.x);
    }

    public GUIGraph(Vector2i position, Vector2i size) {
        this(position, size, 100, 0);
    }

    public void addPoint(float height) {
        if (maxPoints != 0 && maxPoints == points.size()) {
            points.remove(0);
        }

        points.add(Math.min(maxHeight, Math.max(minHeight, height)));
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        partsBuilder.addPart(framePart);
    }


}
