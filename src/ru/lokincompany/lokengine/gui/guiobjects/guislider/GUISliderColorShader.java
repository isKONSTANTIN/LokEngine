package ru.lokincompany.lokengine.gui.guiobjects.guislider;

import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.color.SimpleColorShader;

public interface GUISliderColorShader extends SimpleColorShader {

    Color getColor(float sliderValue);

    @Override
    default Color getColor(Object input) {
        try {
            return getColor((float) input);
        } catch (Exception e) {
            return Colors.black();
        }
    }
}
