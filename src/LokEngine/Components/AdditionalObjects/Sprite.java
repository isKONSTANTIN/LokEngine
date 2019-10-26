package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Texture;
import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.SaveWorker.Saveable;

public class Sprite implements Saveable {

    public Texture texture;
    public int vertexBuffer;
    public int uvBuffer = -1;
    public double size;

    private float vertexSize;

    public float getVertexSize() {
        return vertexSize;
    }

    public Sprite() {
    }

    public Sprite(Texture texture, int vertexBuffer, float vertexSize) {
        this(texture,vertexBuffer,1,vertexSize, -1);
    }

    public Sprite(Texture texture, int vertexBuffer, float vertexSize, int uvBuffer) {
        this(texture, vertexBuffer,1, vertexSize, uvBuffer);
    }

    public Sprite(Texture texture, int vertexBuffer, double size, float vertexSize, int uvBuffer) {
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = size;
        this.vertexSize = vertexSize;
        this.uvBuffer = uvBuffer;
    }

    public boolean equals(Object obj) {
        Sprite objs = (Sprite) obj;
        return objs.texture.equals(texture) &&
                objs.vertexBuffer == vertexBuffer &&
                objs.size == size;
    }

    @Override
    public String save() {
        return Base64.toBase64(texture.save() + "\n" + size + "\n" + vertexSize);
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split("\n");
        this.vertexSize = Float.valueOf(data[2]);
        this.size = Double.valueOf(data[1]);

        Sprite loadedSprite = SpriteLoader.loadSprite((Texture) new Texture().load(data[0]), vertexSize);
        this.texture = loadedSprite.texture;
        this.vertexBuffer = loadedSprite.vertexBuffer;
        return this;
    }
}
