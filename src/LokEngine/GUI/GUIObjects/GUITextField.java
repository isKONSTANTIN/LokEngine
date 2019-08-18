package LokEngine.GUI.GUIObjects;

import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Keyboard;

public class GUITextField extends GUIObject {

    GUIText text;
    private boolean active;

    public GUITextField(Vector2i position, Vector2i size, GUIText text) {
        super(position, size);
        this.text = text;
    }

    public GUITextField(Vector2i position, Vector2i size) {
        super(position, size);
        this.text = new GUIText(position,"");
    }

    @Override
    public void update(){
        if (Misc.mouseInField(position, size) && RuntimeFields.getMouseStatus().mousePressed){
            active = true;
        }else if (RuntimeFields.getMouseStatus().mousePressed){
            active = false;
        }

        while (Keyboard.next() && active){
            char Key = Keyboard.getEventCharacter();

            if (Key == 0 || Key == 27 || Key == 13) break;

            if (Key == 8){
                if (text.getText().length() > 0)
                    text.updateText(text.getText().substring(0,text.getText().length()-1));
            }else {
                String futureText = text.getText() + Key;
                text.updateText(futureText);
                if (text.size.x > size.x){
                    text.updateText(futureText.substring(0,futureText.length()-1));
                }
            }
        }
        text.update();

    }
}
