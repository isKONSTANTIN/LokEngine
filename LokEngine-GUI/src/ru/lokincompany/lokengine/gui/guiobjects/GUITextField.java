package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.additionalobjects.GUITextFieldScript;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.gui.GUITextFieldFramePart;
import ru.lokincompany.lokengine.tools.ClipboardWorker;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.additionalobjects.KeyInfo;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import static org.lwjgl.glfw.GLFW.*;

public class GUITextField extends GUIObject {

    protected boolean canResize;
    protected GUITextFieldFramePart framePart;
    protected GUITextFieldScript activeScript;
    protected GUITextFieldScript inactiveScript;
    protected GUITextFieldScript statusChangedScript;
    private boolean lastActive;

    public GUITextField(GUITextFieldFramePart customFramePart) {
        this.framePart = customFramePart;
        framePart.position = this.position;
        Vector2i textSize = framePart.getSize();
        this.size.x = textSize.x;
        this.size.y = textSize.y;
        this.touchable = true;
    }

    public GUITextField(FontPrefs prefs) {
        super(new Vector2i(), new Vector2i(50, prefs.getSize() + 4));
        framePart = new GUITextFieldFramePart(size, "", prefs);
        this.canResize = false;
        this.touchable = true;
        framePart.centralizeText = false;
        framePart.position = this.position;
    }

    public GUITextField() {
        this(FontPrefs.defaultFontPrefs);
    }

    public boolean isCentralizeText() {
        return framePart.centralizeText;
    }

    public GUITextField setCentralizeText(boolean centralizeText) {
        framePart.centralizeText = centralizeText;
        return this;
    }

    public boolean isCanResize() {
        return canResize;
    }

    public GUITextField setCanResize(boolean canResize) {
        this.canResize = canResize;
        return this;
    }

    public GUITextField setActiveScript(GUITextFieldScript script) {
        activeScript = script;
        return this;
    }

    public GUITextField setInactiveScript(GUITextFieldScript script) {
        inactiveScript = script;
        return this;
    }

    public GUITextField setStatusChangedScript(GUITextFieldScript script) {
        statusChangedScript = script;
        return this;
    }

    public boolean getActive() {
        return active;
    }

    public Color getBackgroundColor() {
        return framePart.backgroundColor;
    }

    public GUITextField setBackgroundColor(Color color) {
        framePart.backgroundColor = color;
        return this;
    }

    public String getText() {
        return framePart.text;
    }

    public GUITextField setText(String text) {
        framePart.text = text;
        if (canResize) {
            Vector2i textSize = framePart.getSize();
            this.size.x = textSize.x;
            this.size.y = textSize.y;
        }
        return this;
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

                    if (keyInfo.mods == GLFW_MOD_CONTROL && eventKey == GLFW_KEY_V && keyInfo.action == GLFW_PRESS){
                        String clipText = ClipboardWorker.getClipboardContents();
                        pointerOffset = clipText.length();
                        pointerText += clipText;
                    }

                    if (keyInfo.mods == GLFW_MOD_CONTROL && eventKey == GLFW_KEY_C && keyInfo.action == GLFW_PRESS){
                        ClipboardWorker.setClipboardContents(framePart.text);
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
