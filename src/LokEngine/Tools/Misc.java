package LokEngine.Tools;

import LokEngine.Tools.Utilities.Vector2i;

import java.util.Base64;

public class Misc {

    public static String toBase64(String text){
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String fromBase64(String base64){
        return new String(Base64.getDecoder().decode(base64));
    }

    public static boolean mouseInField(Vector2i position, Vector2i size){
        Vector2i mousePosition = RuntimeFields.getMouseStatus().getMousePosition();
        Vector2i resolution = RuntimeFields.getFrameBuilder().window.getResolution();
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
