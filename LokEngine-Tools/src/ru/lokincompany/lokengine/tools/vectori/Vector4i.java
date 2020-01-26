package ru.lokincompany.lokengine.tools.vectori;

public class Vector4i extends Vector3i {
    public int w;

    public Vector4i() {
        super(0, 0, 0);
        this.w = 0;
    }

    public Vector4i(int x, int y, int z, int w) {
        super(x, y, z);
        this.w = w;
    }

    public boolean equals(Vector4i vector4i) {
        return (x == vector4i.x) && (y == vector4i.y) && (z == vector4i.z) && (w == vector4i.w);
    }

}
