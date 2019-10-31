package LokEngine.Tools.Text;

import LokEngine.Tools.Utilities.Color.*;
import LokEngine.Tools.Utilities.Vector2i;

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
