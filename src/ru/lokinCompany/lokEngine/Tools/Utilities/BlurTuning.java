package ru.lokinCompany.lokEngine.Tools.Utilities;

public class BlurTuning {

    public double strength = 0.3;
    public int samples = 55;
    public double bokeh = 0.3;

    public BlurTuning(double strength, int samples, double bokeh) {
        this.strength = strength;
        this.samples = samples;
        this.bokeh = bokeh;
    }

    public BlurTuning(double strength) {
        this(strength, 55, 0.3);
    }

    public BlurTuning() {
    }
}
