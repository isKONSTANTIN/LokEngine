package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIText extends GUIObject {

    private GUITextFramePart framePart;

    public GUIText(Vector2i position, String FontName, String text, int fontStyle, int size, boolean antiAlias) {
        super(position, new Vector2i(0,0));
        framePart = new GUITextFramePart(FontName, text,fontStyle,size,antiAlias);
        this.size.y = framePart.getHeight();
        this.size.x = framePart.getWidth();
        framePart.position = this.position;
    }

    public GUIText(Vector2i position, String text, int fontStyle, int size) {
        this(position,"Arial", text, fontStyle, size, true);
    }

    public GUIText(Vector2i position, String text, int fontStyle) {
        this(position,"Arial", text, fontStyle, 24, true);
    }

    public GUIText(Vector2i position, String text) {
        this(position,"Arial", text, 0, 24, true);
    }

    public GUIText(Vector2i position) {
        this(position,"Arial", "Text", 0, 24, true);
    }

    public String getText(){
        return framePart.text;
    }

    public void updateText(String text){
        framePart.text = text;
        this.size.y = framePart.getHeight();
        this.size.x = framePart.getWidth();
    }

    @Override
    public void update(){
        RuntimeFields.frameBuilder.addPart(framePart);
    }

}
