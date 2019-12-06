package ru.lokinCompany.lokEngine.GUI.Canvases;

import org.lwjgl.util.vector.Vector2f;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class GUIScrollCanvas extends GUICanvas {

    public Vector2i maxOffset;
    public Vector2i minOffset;

    public float scrollStrength = 15;

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

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        Vector2f mouseScroll = parentProperties.mouseRaycastStatus.mouse.getMouseScroll();

        int Xmin = (int)(minOffset != null ? (int)Math.max(framePart.viewOffset.x + mouseScroll.x * scrollStrength, minOffset.x) : framePart.viewOffset.x + mouseScroll.x * 10);
        int Xmax = maxOffset != null ? Math.min(Xmin, maxOffset.x) : Xmin;

        int Ymin = (int)(minOffset != null ? (int)Math.max(framePart.viewOffset.y + mouseScroll.y * scrollStrength, minOffset.y) : framePart.viewOffset.y + mouseScroll.y * 10);
        int Ymax = maxOffset != null ? Math.min(Ymin, maxOffset.y) : Ymin;

        framePart.viewOffset.x = Xmax;
        framePart.viewOffset.y = Ymax;

        properties.globalPosition.x += framePart.viewOffset.x;
        properties.globalPosition.y += framePart.viewOffset.y;
    }
}
