package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.AdditionalObjects.GUIObjectProperties;
import LokEngine.Render.Frame.FrameParts.GUI.GUITextFieldFramePart;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Input.Keyboard;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class GUITextField extends GUIObject {

    GUITextFieldFramePart framePart;
    private boolean active;
    private boolean lastActive;
    public boolean canResize;
    public boolean getActive(){
        return active;
    }

    public GUITextField(Vector2i position, GUITextFieldFramePart customFramePart) {
        super(position, new Vector2i(0,0));
        this.framePart = customFramePart;
        this.size.y = framePart.getHeight();
        this.size.x = framePart.getWidth();
        framePart.position = this.position;
    }

    public GUITextField(Vector2i position, Vector2i size, String fontName, String text, LokEngine.Tools.Utilities.Color color, int fontStyle, int fontSize, boolean antiAlias, boolean canResize) {
        super(position, new Vector2i(0,0));
        framePart = new GUITextFieldFramePart(text, new Color(color.red, color.green, color.blue, color.alpha));
        this.canResize = canResize;
        this.size = size;

        if (size.x < 1 || size.y < 1){
            if (canResize){
                this.size.y = framePart.getHeight();
                this.size.x = framePart.getWidth();
            }
        }

        framePart.position = this.position;
    }

    public GUITextField(Vector2i position, Vector2i size, String text, LokEngine.Tools.Utilities.Color color, int fontStyle, int fontSize) {
        this(position, size, "Times New Roman", text, color, fontStyle, fontSize, true, false);
    }

    public GUITextField(Vector2i position, Vector2i size, String text, LokEngine.Tools.Utilities.Color color, int fontStyle) {
        this(position, size, text, color, fontStyle, 24);
    }

    public GUITextField(Vector2i position, Vector2i size, String text) {
        this(position, size , text, new LokEngine.Tools.Utilities.Color(1,1,1,1), 0);
    }

    public GUITextField(Vector2i position, String text) {
        this(position, new Vector2i(), text, new LokEngine.Tools.Utilities.Color(1,1,1,1), 0);
    }

    public GUITextField(Vector2i position) {
        this(position,"");
    }

    public String getText(){
        return framePart.text;
    }

    public void updateText(String text){
        framePart.text = text;
        if (canResize){
            this.size.y = framePart.getHeight();
            this.size.x = framePart.getWidth();
        }
    }

    @Override
    public void setPosition(Vector2i position){
        this.position = position;
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size){
        this.size = size;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties){
        super.update(partsBuilder, parentProperties);

        if (parentProperties.window.getMouse().inField(properties.globalPosition, size) && parentProperties.window.getMouse().getPressedStatus()){
            active = true;
            framePart.pointer = framePart.text.length();

        }else if (parentProperties.window.getMouse().getPressedStatus()){
            active = false;
        }

        Keyboard keyboard = parentProperties.window.getKeyboard();

        while (active && keyboard.next()) {
            if (lastActive) {
                char eventCharacter = keyboard.getPressedChar();
                int eventKey = keyboard.getPressedKey();

                if (eventCharacter == 27) break;

                if (eventCharacter == 13) {
                    active = false;
                    break;
                }

                String pointerText = framePart.text.substring(0, framePart.pointer);

                if (eventCharacter != 0) {
                    int pointerOffset = 0;
                    if (eventCharacter == 8) {
                        if (pointerText.length() > 0){
                            pointerOffset = -1;
                            pointerText = pointerText.substring(0, pointerText.length() - 1);
                        }
                    }else {
                        pointerOffset = 1;
                        pointerText += eventCharacter;
                    }

                    framePart.text = pointerText + framePart.text.substring(framePart.pointer);
                    framePart.pointer += pointerOffset;
                }

                if (eventKey == GLFW_KEY_LEFT) {
                    if (framePart.pointer > 0)
                        framePart.pointer--;
                }else if (eventKey == GLFW_KEY_RIGHT) {
                    framePart.pointer++;
                }

                framePart.pointer = Math.min(framePart.text.length(),framePart.pointer);
            }
        }
        framePart.active = active;
        partsBuilder.addPart(framePart);
        lastActive = active;
    }
}
