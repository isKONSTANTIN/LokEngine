package LokEngine.Tools;

import LokEngine.Tools.Utilities.Vector2i;

public class Misc {
    public static boolean mouseInField(Vector2i position, Vector2i size){
        Vector2i mousePosition = RuntimeFields.getMouseStatus().getMousePosition();
        Vector2i resolution = RuntimeFields.getFrameBuilder().window.getResolution();
        mousePosition.y = Math.abs(mousePosition.y - resolution.y);

        return (mousePosition.x > position.x && mousePosition.x < size.x + position.x) &&
               (mousePosition.y > position.y && mousePosition.y < size.y + position.y);
    }
}
