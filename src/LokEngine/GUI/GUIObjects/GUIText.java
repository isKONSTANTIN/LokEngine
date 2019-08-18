package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIText extends GUIObject {

    private GUITextFramePart framePart;

    public GUIText(Vector2i position, String FontName, String text, Color color, int fontStyle, int size, boolean antiAlias) {
        super(position, new Vector2i(0,0));
        framePart = new GUITextFramePart(text,new org.newdawn.slick.Color(color.red,color.green,color.blue,color.alpha), fontStyle,size,antiAlias);

        this.size.y = framePart.getHeight();
        this.size.x = framePart.getWidth();
        framePart.position = this.position;
    }

    public GUIText(Vector2i position, String text, Color color,  int fontStyle, int size) {
        this(position,"Times New Roman", text, color, fontStyle, size, true);
    }

    public GUIText(Vector2i position, String text, Color color, int fontStyle) {
        this(position,"Times New Roman", text, color, fontStyle, 24, true);
    }

    public GUIText(Vector2i position, String text) {
        this(position,"Times New Roman", text, new Color(1,1,1,1), 0, 24, true);
    }

    public GUIText(Vector2i position) {
        this(position,"Arial", "Text", new Color(1,1,1,1), 0, 24, true);
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
        RuntimeFields.getFrameBuilder().addPart(framePart);
    }

}
