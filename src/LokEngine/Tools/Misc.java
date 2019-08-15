package LokEngine.Tools;

import LokEngine.Tools.Utilities.Vector2i;

public class Misc {

    public static boolean mouseInField(Vector2i position, Vector2i size){
        Vector2i mousePosition = RuntimeFields.mouseStatus.mousePosition;
        Vector2i resolution = RuntimeFields.frameBuilder.window.getResolution();
        mousePosition.y = Math.abs(mousePosition.y - resolution.y);

        return (mousePosition.x > position.x && mousePosition.x < size.x + position.x) &&
               (mousePosition.y > position.y && mousePosition.y < size.y + position.y);
    }

    public static String stackTraceToString(StackTraceElement[] elements){
        StringBuilder StackTrace = new StringBuilder();

        for (int i = 0; i < elements.length; i++) {
            StackTrace.append("Class name: '" + elements[i].getClassName() + "' Method name: '" + elements[i].getMethodName() + "' - " + elements[i].getLineNumber() + " line\n");
        }
        return StackTrace.toString();
    }

}
