package ru.lokincompany.lokengine.gui.canvases;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIFullFlexibleListCanvas extends GUIFlexibleListCanvas {

    public boolean lazy;

    public boolean autoX = true;
    public boolean autoY = true;

    public GUIFullFlexibleListCanvas(Vector2i position, boolean lazy, int gap) {
        super(position, new Vector2i(), gap);
        this.lazy = lazy;
    }

    public GUIFullFlexibleListCanvas(Vector2i position, boolean lazy) {
        this(position, lazy, 0);
    }

    public GUIFullFlexibleListCanvas(Vector2i position) {
        this(position, true);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        Vector2i maxObjectOffset = new Vector2i();

        if (!autoX) maxObjectOffset.x = size.x;
        if (!autoY) maxObjectOffset.y = size.y;

        for (GUIObject object : objects) {
            Vector2i objectPosition = object.getPosition();
            Vector2i objectSize = object.getSize();
            if (autoX)
                maxObjectOffset.x = Math.max(maxObjectOffset.x, objectPosition.x + objectSize.x);
            if (autoY)
                maxObjectOffset.y = Math.max(maxObjectOffset.y, objectPosition.y + objectSize.y);
        }

        if (maxObjectOffset.x > size.x || maxObjectOffset.y > size.y || (maxObjectOffset.x < size.x || maxObjectOffset.y < size.y) && !lazy)
            setSize(maxObjectOffset);
    }
}
