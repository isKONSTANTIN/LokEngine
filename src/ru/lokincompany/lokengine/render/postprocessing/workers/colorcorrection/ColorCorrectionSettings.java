package ru.lokincompany.lokengine.render.postprocessing.workers.colorcorrection;

import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;

public class ColorCorrectionSettings {
    public float gamma;
    public float exposure;
    public Color colorAddition;
    public Color colorMultiplication;

    public ColorCorrectionSettings(float gamma, float exposure, Color colorMultiplication, Color colorAddition) {
        this.gamma = gamma;
        this.exposure = exposure;
        this.colorMultiplication = colorMultiplication;
        this.colorAddition = colorAddition;
    }

    public ColorCorrectionSettings(float gamma, float exposure) {
        this(gamma, exposure, Colors.white(), Colors.black());
    }
}
