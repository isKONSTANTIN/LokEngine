package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.GUITextFieldScript;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFieldFramePart;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.additionalobjects.KeyInfo;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;

import static org.lwjgl.glfw.GLFW.*;

public class GUITextField extends GUIObject {

    protected GUITextFieldFramePart framePart;
    private boolean lastActive;
    public boolean canResize;

    protected GUITextFieldScript activeScript;
    protected GUITextFieldScript inactiveScript;
    protected GUITextFieldScript statusChangedScript;

    public boolean getActive() {
        return active;
    }

    public void setActiveScript(GUITextFieldScript script) {
        activeScript = script;
    }

    public void setInactiveScript(GUITextFieldScript script) {
        inactiveScript = script;
    }

    public void setStatusChangedScript(GUITextFieldScript script) {
        statusChangedScript = script;
    }

    public GUITextField(Vector2i position, GUITextFieldFramePart customFramePart) {
        super(position, new Vector2i(0, 0));
        this.framePart = customFramePart;
        this.size.y = framePart.getHeight();
        this.size.x = framePart.getWidth();
        this.touchable = true;
        framePart.position = this.position;
    }

    public GUITextField(Vector2i position, Vector2i size, String fontName, String text, Color color, int fontStyle, int fontSize, boolean antiAlias, boolean canResize) {
        super(position, new Vector2i(0, 0));
        framePart = new GUITextFieldFramePart(size, text, fontName, color, fontStyle, fontSize, antiAlias);
        this.canResize = canResize;
        this.size = size;
        this.touchable = true;

        if (size.x < 1 || size.y < 1) {
            if (canResize) {
                this.size.y = framePart.getHeight();
                this.size.x = framePart.getWidth();
            }
        }

        framePart.position = this.position;
    }

    public GUITextField(Vector2i position, Vector2i size, String text, Color color, int fontStyle, int fontSize) {
        this(position, size, "Arial", text, color, fontStyle, fontSize, true, false);
    }

    public GUITextField(Vector2i position, Vector2i size, String text, Color color, int fontStyle) {
        this(position, size, text, color, fontStyle, 24);
    }

    public GUITextField(Vector2i position, Vector2i size, String text) {
        this(position, size, text, new Color(1, 1, 1, 1), 0);
    }

    public GUITextField(Vector2i position, String text) {
        this(position, new Vector2i(), text, new Color(1, 1, 1, 1), 0);
    }

    public GUITextField(Vector2i position) {
        this(position, "");
    }

    public String getText() {
        return framePart.text;
    }

    public void updateText(String text) {
        framePart.text = text;
        if (canResize) {
            this.size.y = framePart.getHeight();
            this.size.x = framePart.getWidth();
        }
    }

    public void setBackgroundColor(Color color){
        framePart.backgroundColor = color;
    }

    public Color getBackgroundColor(){
        return framePart.backgroundColor;
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
    }

    @Override
    public void setSize(Vector2i size) {
        this.size = size;
        framePart.size = size;
    }

    @Override
    public Vector2i getSize() {
        return new Vector2i(framePart.getWidth(), framePart.getHeight());
    }

    @Override
    protected void focused() {
        framePart.pointer = framePart.text.length();

        if (statusChangedScript != null)
            statusChangedScript.execute(this);
    }

    @Override
    protected void unfocused() {
        if (statusChangedScript != null)
            statusChangedScript.execute(this);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        Keyboard keyboard = parentProperties.window.getKeyboard();

        while (focused && keyboard.next()) {
            KeyInfo keyInfo = keyboard.getPressedKey();
            if (lastActive) {
                int eventKey = keyInfo.buttonID;
                char eventCharacter = keyInfo.aChar;

                if (eventKey == 27) break;

                if (eventKey == 257) {
                    focused = false;
                    unfocused();
                    break;
                }

                String pointerText = framePart.text.substring(0, framePart.pointer);

                if (eventCharacter != 0 || eventKey != 0) {
                    int pointerOffset = 0;
                    if (eventKey == 259 && keyInfo.action != GLFW_RELEASE) {
                        if (pointerText.length() > 0) {
                            pointerOffset = -1;
                            pointerText = pointerText.substring(0, pointerText.length() - 1);
                        }
                    } else if (eventCharacter != 0) {
                        pointerOffset = 1;
                        pointerText += eventCharacter;
                    }

                    framePart.text = pointerText + framePart.text.substring(framePart.pointer);
                    framePart.pointer += pointerOffset;
                }

                if (eventKey == GLFW_KEY_LEFT && keyInfo.action != GLFW_RELEASE) {
                    if (framePart.pointer > 0) {
                        framePart.pointer--;
                        framePart.printSelecter = true;
                        framePart.timer.resetTimer();
                    }


                } else if (eventKey == GLFW_KEY_RIGHT && keyInfo.action != GLFW_RELEASE) {
                    framePart.pointer++;
                    framePart.printSelecter = true;
                    framePart.timer.resetTimer();
                }

                framePart.pointer = Math.min(framePart.text.length(), framePart.pointer);
            }
        }

        if (focused) {
            if (activeScript != null)
                activeScript.execute(this);
        } else {
            if (inactiveScript != null)
                inactiveScript.execute(this);
        }

        framePart.active = focused;
        lastActive = focused;
        partsBuilder.addPart(framePart);
    }
}