package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.GUITextFieldScript;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFieldFramePart;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.additionalobjects.KeyInfo;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.glfw.GLFW.*;

public class GUITextField extends GUIObject {

    public boolean canResize;
    protected GUITextFieldFramePart framePart;
    protected GUITextFieldScript activeScript;
    protected GUITextFieldScript inactiveScript;
    protected GUITextFieldScript statusChangedScript;
    private boolean lastActive;

    public GUITextField(Vector2i position, GUITextFieldFramePart customFramePart) {
        super(position, new Vector2i(0, 0));
        this.framePart = customFramePart;
        Vector2i textSize = framePart.getSize();
        this.size.x = textSize.x;
        this.size.y = textSize.y;
        this.touchable = true;
        framePart.position = this.position;
    }

    public GUITextField(Vector2i position, Vector2i size, String text, FontPrefs prefs, boolean canResize, boolean centralizeText) {
        super(position, new Vector2i(0, 0));
        framePart = new GUITextFieldFramePart(size, text, prefs);
        this.canResize = canResize;
        this.size = size;
        this.touchable = true;

        if (size.x < 1 || size.y < 1) {
            if (canResize) {
                Vector2i textSize = framePart.getSize();
                this.size.x = textSize.x;
                this.size.y = textSize.y;
            }
        }

        framePart.position = this.position;
        framePart.centralizeText = centralizeText;
    }

    public GUITextField(Vector2i position, Vector2i size, String text, FontPrefs prefs, boolean centralizeText) {
        this(position, size, text, prefs,false, centralizeText);
    }

    public GUITextField(Vector2i position, Vector2i size, String text) {
        this(position, size, text, new FontPrefs(), false);
    }

    public GUITextField(Vector2i position, String text) {
        this(position, new Vector2i(), text);
    }

    public GUITextField(Vector2i position) {
        this(position, "");
    }

    public GUITextField(GUITextFieldFramePart customFramePart) {
        this(new Vector2i(), customFramePart);
    }

    public GUITextField(String text, FontPrefs prefs, boolean canResize, boolean centralizeText) {
        this(new Vector2i(), new Vector2i(), text, prefs, canResize, centralizeText);
    }

    public GUITextField(String text, FontPrefs prefs, boolean canResize) {
        this(text, prefs, canResize, false);
    }

    public GUITextField(String text, FontPrefs prefs) {
        this(new Vector2i(), new Vector2i(), text, prefs, false);
    }

    public GUITextField(String text) {
        this(text, new FontPrefs());
    }

    public GUITextField() {
        this("");
    }

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

    public String getText() {
        return framePart.text;
    }

    public void updateText(String text) {
        framePart.text = text;
        if (canResize) {
            Vector2i textSize = framePart.getSize();
            this.size.x = textSize.x;
            this.size.y = textSize.y;
        }
    }

    public Color getBackgroundColor() {
        return framePart.backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        framePart.backgroundColor = color;
    }

    @Override
    public GUITextField setPosition(Vector2i position) {
        this.position = position;
        framePart.position = position;
        return this;
    }

    @Override
    public GUITextField setSize(Vector2i size) {
        this.size = size;
        framePart.size = size;
        return this;
    }

    public Vector2i getTextSize() {
        return framePart.getSize();
    }

    @Override
    protected void focused() {
        framePart.pointer = framePart.text.length();
        framePart.pointerTime = 0;

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
                    framePart.pointerTime = 0;
                }

                if (eventKey == GLFW_KEY_LEFT && keyInfo.action != GLFW_RELEASE) {
                    if (framePart.pointer > 0) {
                        framePart.pointer--;
                        framePart.pointerTime = 0;
                    }
                } else if (eventKey == GLFW_KEY_RIGHT && keyInfo.action != GLFW_RELEASE) {
                    framePart.pointer++;
                    framePart.pointerTime = 0;
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
