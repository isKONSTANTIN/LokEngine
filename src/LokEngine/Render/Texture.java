package LokEngine.Render;

import LokEngine.Loaders.TextureLoader;
import LokEngine.Tools.SaveWorker.Saveable;

public class Texture implements Saveable {

    public int buffer;
    public int sizeX;
    public int sizeY;
    public String path;

    public Texture(){}

    public Texture(int buffer, int sizeX, int sizeY, String path){
        this.buffer = buffer;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.path = path;
    }

    public boolean equals(Object obj){
        Texture objt = (Texture)obj;

        return objt.buffer == buffer &&
                objt.sizeX == sizeX &&
                objt.sizeY == sizeY;
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        Texture loadedTexture = TextureLoader.loadTexture(savedString);
        this.buffer = loadedTexture.buffer;
        this.sizeX = loadedTexture.sizeX;
        this.sizeY = loadedTexture.sizeY;
        this.path = loadedTexture.path;
        return this;
    }
}
