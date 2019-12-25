package ru.lokincompany.lokengine.render.postprocessing.workers.colorcorrection;

public class ColorCorrectionSettings {
    public float gamma;
    public float exposure;

    public ColorCorrectionSettings(float gamma, float exposure){
        this.gamma = gamma;
        this.exposure = exposure;
    }
}
