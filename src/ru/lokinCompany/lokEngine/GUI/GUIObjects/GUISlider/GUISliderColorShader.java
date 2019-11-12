package ru.lokinCompany.lokEngine.GUI.GUIObjects.GUISlider;

import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.SimpleColorShader;

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
