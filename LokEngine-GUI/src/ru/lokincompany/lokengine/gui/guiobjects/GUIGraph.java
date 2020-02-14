package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUIGraphFramePart;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.ArrayList;

public class GUIGraph extends GUIObject {
    protected ArrayList<Float> points = new ArrayList<>();

    protected GUIGraphFramePart framePart;
    protected GUIFreeTextDrawer freeTextDrawer;

    public GUIGraph(Color color, Color colorB) {
        super(new Vector2i(), new Vector2i(100, 100));
        this.freeTextDrawer = new GUIFreeTextDrawer(new FontPrefs()
                .setSize(
                        Math.min(Math.max(size.y / 10, 10), 24)
                ));
        framePart = new GUIGraphFramePart(position, size, points, 100, 0, 100, Colors.engineMainColor(), Colors.engineBackgroundColor(), freeTextDrawer);
    }

    public GUIGraph setMaxHeight(float maxHeight) {
        framePart.maxHeight = maxHeight;
        return this;
    }

    public GUIGraph setMinHeight(float minHeight) {
        framePart.minHeight = minHeight;
        return this;
    }

    public GUIGraph setMaxPoints(int maxPoints) {
        framePart.maxPoints = maxPoints;
        return this;
    }

    public ArrayList<Float> getPoints() {
        return points;
    }

    public float getMaxHeight() {
        return framePart.maxHeight;
    }

    public float getMinHeight() {
        return framePart.minHeight;
    }

    public int getMaxPoints() {
        return framePart.maxPoints;
    }

    public GUIFreeTextDrawer getFreeTextDrawer() {
        return freeTextDrawer;
    }

    public GUIGraph addPoint(float height) {
        if (framePart.maxPoints != 0 && framePart.maxPoints == points.size()) {
            points.remove(0);
        }

        points.add(height);
        return this;
    }

    @Override
    public GUIGraph setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
        return this;
    }

    @Override
    public GUIGraph setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        freeTextDrawer.update(partsBuilder, properties);
        partsBuilder.addPart(framePart);
    }


}
