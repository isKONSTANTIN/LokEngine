package LokEngine.Tools.Utilities;

public class ColorRGB extends Color {

    public ColorRGB(int red, int green, int blue) {
        super(red / 255f, green / 255f, blue / 255f, 1);
    }

    public ColorRGB(int red, int green, int blue, int alpha) {
        super(red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }

}
