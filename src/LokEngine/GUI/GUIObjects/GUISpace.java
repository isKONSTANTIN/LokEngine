package LokEngine.GUI.GUIObjects;

import LokEngine.Tools.Utilities.Vector2i;

public class GUISpace extends GUIObject {

    public GUISpace(Vector2i position, Vector2i size) {
        super(position, size);
    }

    public GUISpace(Vector2i size) {
        this(new Vector2i(), size);
    }

    public GUISpace() {
        this(new Vector2i(), new Vector2i());
    }
}
