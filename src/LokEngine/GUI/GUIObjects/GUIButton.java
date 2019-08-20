package LokEngine.GUI.GUIObjects;

import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

public class GUIButton extends GUIObject {

    public Color pressedColor;
    public Color calmStateColor;
    public GUIText text;
    public GUIPanel panel;

    private Color activeColor;
    private boolean pressed;

    public boolean isPressed(){
        return pressed;
    }

    public GUIButton(Vector2i position, Vector2i size, Color pressed, Color calmState, GUIText text, GUIPanel panel) {
        super(position, size);
        this.text = text;
        if (size.x <= 0 || size.y <= 0){
            size = this.text.size;
            this.size = size;
        }
        this.pressedColor = pressed;
        this.calmStateColor = calmState;
        this.panel = panel;
        this.activeColor = new Color(calmStateColor.red,calmStateColor.green,calmStateColor.blue, calmStateColor.alpha);
    }

    public GUIButton(Vector2i position, Vector2i size, Color calmState, String text) {
        super(position, size);
        this.text = new GUIText(position,text);
        if (size.x <= 0 || size.y <= 0){
            size = this.text.size;
            this.size = size;
        }
        float colorChange = (calmState.red + calmState.green + calmState.blue) / 3 >= 0.5f ? -0.1f : 0.1f;
        this.pressedColor = new Color(calmState.red + colorChange,calmState.green + colorChange,calmState.blue + colorChange,calmState.alpha);
        this.calmStateColor = calmState;
        this.activeColor = new Color(calmStateColor.red,calmStateColor.green,calmStateColor.blue, calmStateColor.alpha);
        this.panel = new GUIPanel(position,size,activeColor);
    }

    public void pressed(){
        activeColor.red = pressedColor.red;
        activeColor.green = pressedColor.green;
        activeColor.blue = pressedColor.blue;
    }

    public void unPressed(){
        activeColor.red = calmStateColor.red;
        activeColor.green = calmStateColor.green;
        activeColor.blue = calmStateColor.blue;
    }

    private void checkMouse(){
        Vector2i mousePosition = RuntimeFields.getMouseStatus().mousePosition;
        Vector2i resolution = RuntimeFields.getFrameBuilder().window.getResolution();

        mousePosition.y = Math.abs(mousePosition.y - resolution.y);
        boolean enterInBox = Misc.mouseInField(position,size);

        if (enterInBox && RuntimeFields.getMouseStatus().mousePressed){
            if (!pressed){
                pressed();
                pressed = true;
            }
        }

        if ((!RuntimeFields.getMouseStatus().mousePressed || !enterInBox) && pressed){
            unPressed();
            pressed = false;
        }
    }

    @Override
    public void update(){
        panel.position.x = position.x;
        panel.position.y = position.y;
        panel.size.x = size.x;
        panel.size.y = size.y;
        text.position.x  = position.x;
        text.position.y = position.y;

        checkMouse();

        panel.update();
        text.update();
    }

}