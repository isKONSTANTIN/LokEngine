package ru.lokincompany.lokengine.tools.color;

public class Colors {

    public static Color red() {
        return new Color(1, 0, 0, 1);
    }

    public static Color green() {
        return new Color(0, 1, 0, 1);
    }

    public static Color blue() {
        return new Color(0, 0, 1, 1);
    }

    public static Color white() {
        return new Color(1, 1, 1, 1);
    }

    public static Color brown() {
        return new Color(1, 1, 0, 1);
    }

    public static Color purple() {
        return new Color(1, 0, 1, 1);
    }

    public static Color cyan() {
        return new Color(0, 1, 1, 1);
    }

    public static Color black() {
        return new Color(0, 0, 0, 1);
    }

    public static Color engineMainColor() {
        return new ColorRGB(219, 160, 37, 255);
    }

    public static Color engineBrightMainColor() {
        Color color = engineMainColor();
        return new Color(color.red + (color.red * 0.1f), color.green + (color.green * 0.1f), color.blue + (color.blue * 0.1f), 1);
    }

    public static Color engineBackgroundColor() {
        return new Color(0.2f, 0.2f, 0.2f, 1);
    }

    public static Color engineBrightBackgroundColor(){
        return new Color(0.3f, 0.3f, 0.3f, 1);
    }

}
