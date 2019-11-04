package ru.lokinCompany.lokEngine.Tools.Text;

import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.SimpleColorShader;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public interface TextColorShader extends SimpleColorShader {

    Color getColor(Vector2i charPos);

    @Override
    default Color getColor(Object input) {
        try {
            return getColor((Vector2i)input);
        }catch (Exception e){
            return Colors.black();
        }
    }
}
