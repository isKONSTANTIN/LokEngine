package ru.lokincompany.lokengine.render;

import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class Texture implements Saveable {

    public int buffer;
    public int sizeX;
    public int sizeY;
    public String path;

    public Texture() {
    }

    public Texture(int buffer, int sizeX, int sizeY, String path) {
        this.buffer = buffer;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.path = path;
    }

    public boolean equals(Object obj) {
        Texture objt = (Texture) obj;

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
        Texture loadedTexture;
        try {
            loadedTexture = TextureLoader.loadTexture(savedString);

            this.buffer = loadedTexture.buffer;
            this.sizeX = loadedTexture.sizeX;
            this.sizeY = loadedTexture.sizeY;
            this.path = loadedTexture.path;

        } catch (Exception e) {
            Logger.warning("Fail load texture!", "LokEngine_Texture");

            this.buffer = -1;
            this.sizeX = 0;
            this.sizeY = 0;
            this.path = savedString;
        }

        return this;
    }
}
