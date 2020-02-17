package ru.lokincompany.lokengine.tools.color;

public class Color {

    public float red;
    public float green;
    public float blue;
    public float alpha;

    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color setRed(float red) {
        this.red = red;
        return this;
    }

    public Color setGreen(float green) {
        this.green = green;
        return this;
    }

    public Color setBlue(float blue) {
        this.blue = blue;
        return this;
    }

    public Color setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }
}
