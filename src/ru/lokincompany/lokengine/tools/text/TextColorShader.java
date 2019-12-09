package ru.lokincompany.lokengine.tools.text;

import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;
import ru.lokincompany.lokengine.tools.utilities.color.SimpleColorShader;

public interface TextColorShader extends SimpleColorShader {

    Color getColor(Vector2i charPos);

    @Override
    default Color getColor(Object input) {
        try {
            return getColor((Vector2i) input);
        } catch (Exception e) {
            return Colors.black();
        }
    }
}
