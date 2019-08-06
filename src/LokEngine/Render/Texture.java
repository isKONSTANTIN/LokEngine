package LokEngine.Render;

public class Texture {

    public int buffer;
    public int sizeX;
    public int sizeY;

    public Texture(int buffer, int sizeX, int sizeY){
        this.buffer = buffer;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public boolean equals(Object obj){
        Texture objt = (Texture)obj;

        return objt.buffer == buffer &&
                objt.sizeX == sizeX &&
                objt.sizeY == sizeY;
    }
}
