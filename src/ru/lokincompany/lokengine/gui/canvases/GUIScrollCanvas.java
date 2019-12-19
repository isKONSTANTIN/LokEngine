package ru.lokincompany.lokengine.gui.canvases;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

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

    protected void updateScroll(GUIObjectProperties parentProperties){
        Vector2f mouseScroll = parentProperties.mouseRaycastStatus.mouse.getMouseScroll();

        int Xmin = (int) (minOffset != null ? Math.max(framePart.viewOffset.x + mouseScroll.x * scrollStrength, minOffset.x) : framePart.viewOffset.x + mouseScroll.x * 10);
        int Xmax = maxOffset != null ? Math.min(Xmin, maxOffset.x) : Xmin;

        int Ymin = (int) (minOffset != null ? Math.max(framePart.viewOffset.y + mouseScroll.y * scrollStrength, minOffset.y) : framePart.viewOffset.y + mouseScroll.y * 10);
        int Ymax = maxOffset != null ? Math.min(Ymin, maxOffset.y) : Ymin;

        framePart.viewOffset.x = Xmax;
        framePart.viewOffset.y = Ymax;

        properties.globalPosition.x += framePart.viewOffset.x;
        properties.globalPosition.y += framePart.viewOffset.y;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties, false);

        if (properties.mouseRaycastStatus.mouse.inField(properties.globalPosition, size))
            updateScroll(parentProperties);

        updateObjects();
    }
}
