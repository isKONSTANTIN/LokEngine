package ru.lokinCompany.LokEngine.Tools.Text;

import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.SimpleColorShader;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;

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
