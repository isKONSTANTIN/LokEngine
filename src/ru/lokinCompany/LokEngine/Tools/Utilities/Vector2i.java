package ru.lokinCompany.LokEngine.Tools.Utilities;

public class Vector2i {
    public int x;
    public int y;

    public Vector2i() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2i vector2i){
        return (x == vector2i.x) && (y == vector2i.y);
    }

}
