package LokEngine.Tools.Utilities;

public class BlurTuning {

    public double strength = 1;
    public int samples = 20;
    public double bokeh = 0.3;

    public BlurTuning(double strength, int samples, double bokeh) {
        this.strength = strength;
        this.samples = samples;
        this.bokeh = bokeh;
    }

    public BlurTuning(double strength) {
        this(strength, 20, 0.3);
    }

    public BlurTuning() {
    }
}
