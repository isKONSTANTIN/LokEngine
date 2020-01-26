package ru.lokincompany.lokengine.tools.input.additionalobjects;

public class KeyInfo {
    public char aChar;
    public int buttonID;
    public int action;

    public KeyInfo(char aChar, int buttonID, int action) {
        this.aChar = aChar;
        this.buttonID = buttonID;
        this.action = action;
    }
}
