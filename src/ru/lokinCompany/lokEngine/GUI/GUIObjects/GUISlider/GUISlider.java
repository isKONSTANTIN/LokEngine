package ru.lokinCompany.lokEngine.GUI.GUIObjects.GUISlider;

import org.lwjgl.util.vector.Vector2f;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.GUI.GUISliderFramePart;
import ru.lokinCompany.lokEngine.Render.Frame.PartsBuilder;
import ru.lokinCompany.lokEngine.Tools.Logger;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.ColorRGB;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class GUISlider extends GUIObject {

    protected GUISliderFramePart framePart;
    protected GUISliderHead head;

    public GUISliderColorShader colorFillShader;
    public GUISliderColorShader colorBackgroundShader;

    public Vector2f range = new Vector2f(0,100);

    public GUISlider(Vector2i position, Vector2i size, Color background, Color filledBackground, GUISliderHead head) {
        super(position, size);
        framePart = new GUISliderFramePart(position, size, background, filledBackground, head);
        this.head = head;
        touchable = true;

        head.setPosition(object -> {
            Vector2i headPos = new Vector2i((int)(this.getSize().x * framePart.filled) - object.getSize().x / 2,this.getSize().y / 2 - object.getSize().y / 2);

            headPos.x = Math.min(Math.max(headPos.x, 0), this.getSize().x - object.getSize().x);

            headPos.x += this.getPosition().x;
            headPos.y += this.getPosition().y;
            return headPos;
        });

        if (head.getSize().x == -1 || head.getSize().y == -1)
            head.setSize(object -> new Vector2i(getSize().y + 2,this.getSize().y + 2));

    }

    public GUISlider(Vector2i position, Vector2i size, Color background, Color filledBackground) {
        this(position,size,background,filledBackground,
                new GUISliderHead(new Vector2i(), new Vector2i(-1,-1),Colors.engineBrightMainColor())
        );
    }

    public GUISlider(Vector2i position, Vector2i size) {
        this(position,size, Colors.engineBackgroundColor(), Colors.engineMainColor());
    }

    public float getValue(){
        return (range.y - range.x) * framePart.filled + range.x;
    }

    public GUISliderHead getHead() {
        return head;
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        framePart.size = size;
    }

    protected void calculateFilled(){
        framePart.filled = Math.min(Math.max((properties.mouseRaycastStatus.mouse.getMousePosition().x - properties.globalPosition.x) / (float)size.x,0),1);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);
        if (active || head.getActive() || retention || head.getRetention()){
            calculateFilled();
        }
        head.update(partsBuilder, parentProperties);

        if (head.colorShader != null)
            head.color = head.colorShader.getColor(framePart.filled);

        if (colorFillShader != null)
            this.framePart.colorFill = colorFillShader.getColor(framePart.filled);

        if (colorBackgroundShader != null)
            this.framePart.colorBackground = colorBackgroundShader.getColor(framePart.filled);

        partsBuilder.addPart(framePart);
    }

}
