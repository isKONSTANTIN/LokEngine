package ru.lokincompany.lokengine.tools.opensimplexnoise;

import ru.lokincompany.lokengine.tools.utilities.StringToLongTransformer;

public abstract class OpenSimplexNoise {

    static final double STRETCH_CONSTANT_2D = -0.211324865405187;
    static final double SQUISH_CONSTANT_2D = 0.366025403784439;
    static final double STRETCH_CONSTANT_3D = -1.0 / 6;
    static final double SQUISH_CONSTANT_3D = 1.0 / 3;
    static final double STRETCH_CONSTANT_4D = -0.138196601125011;
    static final double SQUISH_CONSTANT_4D = 0.309016994374947;

    static final double NORM_CONSTANT_2D = 47;
    static final double NORM_CONSTANT_3D = 103;
    static final double NORM_CONSTANT_4D = 30;

    short[] perm;
    short[] permGradIndex3D;

    public OpenSimplexNoise(long seed) {
        perm = new short[256];
        permGradIndex3D = new short[256];
        short[] source = new short[256];
        for (short i = 0; i < 256; i++)
            source[i] = i;
        seed = seed * 6364136223846793005L + 1442695040888963407L;
        seed = seed * 6364136223846793005L + 1442695040888963407L;
        seed = seed * 6364136223846793005L + 1442695040888963407L;
        for (int i = 255; i >= 0; i--) {
            seed = seed * 6364136223846793005L + 1442695040888963407L;
            int r = (int)((seed + 31) % (i + 1));
            if (r < 0)
                r += (i + 1);
            perm[i] = source[r];
            permGradIndex3D[i] = (short)((perm[i] % (gradients3D.length / 3)) * 3);
            source[r] = source[i];
        }
    }

    public OpenSimplexNoise(){
        this(Double.doubleToLongBits(Math.random()));
    }

    public OpenSimplexNoise(String seed, StringToLongTransformer transformer){
        this(transformer.transform(seed));
    }

    public OpenSimplexNoise(String seed){
        this(seed, text -> {
            long longSeed = 0;

            char[] symbols = seed.toCharArray();

            for (int i = 1; i <= symbols.length; i++){
                longSeed += Math.pow((int)symbols[i-1], i);
            }

            return longSeed;
        });
    }

    double extrapolate(int xsb, int ysb, double dx, double dy)
    {
        int index = perm[(perm[xsb & 0xFF] + ysb) & 0xFF] & 0x0E;
        return gradients2D[index] * dx
                + gradients2D[index + 1] * dy;
    }

    double extrapolate(int xsb, int ysb, int zsb, double dx, double dy, double dz)
    {
        int index = permGradIndex3D[(perm[(perm[xsb & 0xFF] + ysb) & 0xFF] + zsb) & 0xFF];
        return gradients3D[index] * dx
                + gradients3D[index + 1] * dy
                + gradients3D[index + 2] * dz;
    }

    double extrapolate(int xsb, int ysb, int zsb, int wsb, double dx, double dy, double dz, double dw)
    {
        int index = perm[(perm[(perm[(perm[xsb & 0xFF] + ysb) & 0xFF] + zsb) & 0xFF] + wsb) & 0xFF] & 0xFC;
        return gradients4D[index] * dx
                + gradients4D[index + 1] * dy
                + gradients4D[index + 2] * dz
                + gradients4D[index + 3] * dw;
    }

    static int fastFloor(double x) {
        int xi = (int)x;
        return x < xi ? xi - 1 : xi;
    }

    static byte[] gradients2D = new byte[] {
            5,  2,    2,  5,
            -5,  2,   -2,  5,
            5, -2,    2, -5,
            -5, -2,   -2, -5,
    };

    static byte[] gradients3D = new byte[] {
            -11,  4,  4,     -4,  11,  4,    -4,  4,  11,
            11,  4,  4,      4,  11,  4,     4,  4,  11,
            -11, -4,  4,     -4, -11,  4,    -4, -4,  11,
            11, -4,  4,      4, -11,  4,     4, -4,  11,
            -11,  4, -4,     -4,  11, -4,    -4,  4, -11,
            11,  4, -4,      4,  11, -4,     4,  4, -11,
            -11, -4, -4,     -4, -11, -4,    -4, -4, -11,
            11, -4, -4,      4, -11, -4,     4, -4, -11,
    };

    static byte[] gradients4D = new byte[] {
            3,  1,  1,  1,      1,  3,  1,  1,      1,  1,  3,  1,      1,  1,  1,  3,
            -3,  1,  1,  1,     -1,  3,  1,  1,     -1,  1,  3,  1,     -1,  1,  1,  3,
            3, -1,  1,  1,      1, -3,  1,  1,      1, -1,  3,  1,      1, -1,  1,  3,
            -3, -1,  1,  1,     -1, -3,  1,  1,     -1, -1,  3,  1,     -1, -1,  1,  3,
            3,  1, -1,  1,      1,  3, -1,  1,      1,  1, -3,  1,      1,  1, -1,  3,
            -3,  1, -1,  1,     -1,  3, -1,  1,     -1,  1, -3,  1,     -1,  1, -1,  3,
            3, -1, -1,  1,      1, -3, -1,  1,      1, -1, -3,  1,      1, -1, -1,  3,
            -3, -1, -1,  1,     -1, -3, -1,  1,     -1, -1, -3,  1,     -1, -1, -1,  3,
            3,  1,  1, -1,      1,  3,  1, -1,      1,  1,  3, -1,      1,  1,  1, -3,
            -3,  1,  1, -1,     -1,  3,  1, -1,     -1,  1,  3, -1,     -1,  1,  1, -3,
            3, -1,  1, -1,      1, -3,  1, -1,      1, -1,  3, -1,      1, -1,  1, -3,
            -3, -1,  1, -1,     -1, -3,  1, -1,     -1, -1,  3, -1,     -1, -1,  1, -3,
            3,  1, -1, -1,      1,  3, -1, -1,      1,  1, -3, -1,      1,  1, -1, -3,
            -3,  1, -1, -1,     -1,  3, -1, -1,     -1,  1, -3, -1,     -1,  1, -1, -3,
            3, -1, -1, -1,      1, -3, -1, -1,      1, -1, -3, -1,      1, -1, -1, -3,
            -3, -1, -1, -1,     -1, -3, -1, -1,     -1, -1, -3, -1,     -1, -1, -1, -3,
    };
}