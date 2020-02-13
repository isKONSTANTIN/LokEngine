package ru.lokincompany.lokengine.gui.canvases;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUIScrollCanvas extends GUICanvas {

    public Vector2i maxOffset;
    public Vector2i minOffset;
    public float scrollStrength = 2f;
    public float scrollDeceleration = 1.1f;

    protected Vector2f scrollImpulse = new Vector2f();
    protected Vector2f scrollPosition = new Vector2f();

    public GUIScrollCanvas(Vector2i position, Vector2i size, Vector2i maxOffset, Vector2i minOffset) {
        super(position, size);
        this.maxOffset = maxOffset;
        this.minOffset = minOffset;
    }

    public GUIScrollCanvas(Vector2i position, Vector2i size, Vector2i maxOffset) {
        this(position, size, maxOffset, null);
    }

    public GUIScrollCanvas(Vector2i position, Vector2i size) {
        this(position, size, null, null);
    }

    protected void updateScroll(GUIObjectProperties parentProperties) {
        Vector2f mouseScroll = parentProperties.mouseRaycastStatus.mouse.getMouseScroll();

        scrollImpulse.x += mouseScroll.x * scrollStrength;
        scrollImpulse.y += mouseScroll.y * scrollStrength;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties, false);

        if (properties.mouseRaycastStatus.mouse.inField(properties.globalPosition, size))
            updateScroll(parentProperties);

        int Xmin = (int) (minOffset != null ? Math.max(scrollPosition.x + scrollImpulse.x, minOffset.x) : scrollPosition.x + scrollImpulse.x);
        int Xmax = maxOffset != null ? Math.min(Xmin, maxOffset.x) : Xmin;

        int Ymin = (int) (minOffset != null ? Math.max(scrollPosition.y + scrollImpulse.y, minOffset.y) : scrollPosition.y + scrollImpulse.y);
        int Ymax = maxOffset != null ? Math.min(Ymin, maxOffset.y) : Ymin;

        scrollPosition.x = Xmax;
        scrollPosition.y = Ymax;

        scrollImpulse.x /= scrollDeceleration;
        scrollImpulse.y /= scrollDeceleration;

        if (Math.abs(scrollImpulse.x) <= 0.2f) scrollImpulse.x = 0;
        if (Math.abs(scrollImpulse.y) <= 0.2f) scrollImpulse.y = 0;

        framePart.viewOffset.x = (int)scrollPosition.x;
        framePart.viewOffset.y = (int)scrollPosition.y;

        properties.globalPosition.x += framePart.viewOffset.x;
        properties.globalPosition.y += framePart.viewOffset.y;

        updateObjects();
    }
}
