package ru.lokinCompany.LokEngine.Tools.Utilities;

public class Vector3i extends Vector2i {

    public int z;

    public Vector3i() {
        super(0, 0);
        this.z = 0;
    }

    public Vector3i(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public boolean equals(Vector3i vector3i){
        return (x == vector3i.x) && (y == vector3i.y) && (z == vector3i.z);
    }

}
