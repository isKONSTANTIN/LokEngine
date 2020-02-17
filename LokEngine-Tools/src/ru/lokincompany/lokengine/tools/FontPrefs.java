package ru.lokincompany.lokengine.tools;

import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;

public class FontPrefs {
    public final static FontPrefs defaultFontPrefs = new FontPrefs();
    private String fontName = "Arial";
    private Color color = Colors.white();
    private TextColorShader shader = charPos -> color;
    private int fontStyle = 0;
    private int size = 14;

    public FontPrefs() {
    }

    public int getSize() {
        return size;
    }

    public FontPrefs setSize(int size) {
        this.size = size;
        return this;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public FontPrefs setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public TextColorShader getShader() {
        return shader;
    }

    public FontPrefs setShader(TextColorShader shader) {
        this.shader = shader;
        return this;
    }

    public String getFontName() {
        return fontName;
    }

    public FontPrefs setFontName(String fontName) {
        this.fontName = fontName;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public FontPrefs setColor(Color color) {
        this.color = color;
        return this;
    }
}
