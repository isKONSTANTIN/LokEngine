package ru.lokincompany.lokengine.tools;

import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.color.SimpleColorShader;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public interface TextColorShader extends SimpleColorShader {

    Color getColor(Vector2i charPos);

    @Override
    default Color getColor(Object input) {
        try {
            return getColor((Vector2i) input);
        } catch (Throwable e) {
            return Colors.black();
        }
    }
}
