package LokEngine.GUI.GUIObjects;

import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Keyboard;

public class GUITextField extends GUIObject {

    GUIText text;
    private boolean active;

    public GUIText getGUIText(){
        return text;
    }

    public boolean getActive(){
        return active;
    }

    public GUITextField(Vector2i position, Vector2i size, GUIText text) {
        super(position, size);
        this.text = text;
    }

    public GUITextField(Vector2i position, Vector2i size) {
        super(position, size);
        this.text = new GUIText(position,"");
        this.text.size = size;
    }

    @Override
    public void setPosition(Vector2i position){
        this.position = position;
        text.setPosition(position);
    }

    @Override
    public void setSize(Vector2i size){
        this.size = size;
        text.setSize(size);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalSourcePos){
        Vector2i myGlobalPosition = new Vector2i(globalSourcePos.x + position.x,globalSourcePos.y + position.y);

        if (Misc.mouseInField(myGlobalPosition, size) && RuntimeFields.getMouseStatus().getPressedStatus()){
            active = true;
        }else if (RuntimeFields.getMouseStatus().getPressedStatus()){
            active = false;
        }

        while (active && Keyboard.next()){
            char Key = Keyboard.getEventCharacter();

            if (Key == 0 || Key == 27 || Key == 13) break;

            if (Key == 8){
                if (text.getText().length() > 0)
                    text.updateText(text.getText().substring(0,text.getText().length()-1));
            }else {
                text.updateText(text.getText() + Key);
            }
        }
        text.update(partsBuilder, myGlobalPosition);
    }
}
