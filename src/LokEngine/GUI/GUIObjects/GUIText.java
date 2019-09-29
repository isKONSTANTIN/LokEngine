package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Render.Frame.FrameParts.GUI.GUITextFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIText extends GUIObject {

    private GUITextFramePart framePart;
    private int maxTextLength;
    public boolean canResize;

    public GUIText(Vector2i position, GUITextFramePart customFramePart) {
        super(position, new Vector2i(0,0));

        //this.framePart = customFramePart;
        //this.size.y = framePart.getHeight();
        //this.size.x = framePart.getWidth();
        //framePart.position = this.position;
    }

    public GUIText(Vector2i position, String fontName, String text, Color color, int fontStyle, int size, boolean antiAlias, boolean canResize) {
        super(position, new Vector2i(0,0));
        //framePart = new GUITextFramePart(text, fontName, color, fontStyle, size, antiAlias);

        //this.size.y = framePart.getHeight();
        //this.size.x = framePart.getWidth();
        //this.canResize = canResize;
        //framePart.position = this.position;
    }

    public GUIText(Vector2i position, String text, Color color,  int fontStyle, int size) {
        this(position,"Times New Roman", text, color, fontStyle, size, true, false);
    }

    public GUIText(Vector2i position, String text, Color color, int fontStyle) {
        this(position, text, color, fontStyle, 24);
    }

    public GUIText(Vector2i position, String text) {
        this(position, text, new Color(1,1,1,1), 0);
    }

    public GUIText(Vector2i position) {
        this(position,"Text");
    }

    public String getText(){
        return "";//framePart.text;
    }

    public void updateText(String text){
        //framePart.text = text;
        if (canResize){
            //this.size.x = framePart.getWidth();
            //this.size.y = framePart.getHeight();
        }else{
         //   updateMaxSize();
         //   framePart.text = text.length() > maxTextLength ? framePart.text.substring(0, maxTextLength) : framePart.text;
        }
    }

    @Override
    public void setPosition(Vector2i position){
        //this.position = position;
        //framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size){
        //this.size = size;
        //updateMaxSize();
        //framePart.text = getText().length() > maxTextLength ? framePart.text.substring(0, maxTextLength) : framePart.text;
    }

    private void updateMaxSize(){
        //int textLength = framePart.text.length();
        //if (textLength > 0 && framePart.getWidth() > 0)
        //    this.maxTextLength = size.x / (framePart.getWidth() / framePart.text.length());
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder, parentProperties);
        //partsBuilder.addPart(framePart);
    }

}
