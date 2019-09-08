package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Shader;
import LokEngine.Render.Texture;
import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.SaveWorker.Saveable;

public class Sprite implements Saveable {

    public Shader shader;
    public Texture texture;
    public int vertexBuffer;
    public int uvBuffer;
    public double size;

    private float vertexSize;

    public float getVertexSize(){
        return vertexSize;
    }

    public Sprite(){}

    public Sprite(Texture texture, int vertexBuffer, int uvBuffer, float vertexSize){
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = 1;
        this.uvBuffer = uvBuffer;
        this.shader = DefaultFields.defaultShader;
        this.vertexSize = vertexSize;
    }

    public Sprite(Texture texture, int vertexBuffer, int uvBuffer, double size, float vertexSize){
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = size;
        this.uvBuffer = uvBuffer;
        this.shader = DefaultFields.defaultShader;
        this.vertexSize = vertexSize;
    }

    public Sprite(Texture texture, int vertexBuffer, int uvBuffer, double size, float vertexSize, Shader shader){
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = size;
        this.uvBuffer = uvBuffer;
        this.shader = shader;
        this.vertexSize = vertexSize;
    }

    public boolean equals(Object obj){
        Sprite objs = (Sprite)obj;
        return objs.shader.equals(shader) &&
                objs.texture.equals(texture) &&
                objs.vertexBuffer == vertexBuffer &&
                objs.uvBuffer == uvBuffer &&
                objs.size == size;
    }

    @Override
    public String save() {
        return Base64.toBase64(shader.save() + "\n" + texture.save() + "\n" + size + "\n" + vertexSize);
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split(System.getProperty("line.separator"));
        this.vertexSize = Float.valueOf(data[3]);
        this.size = Double.valueOf(data[2]);

        Sprite loadedSprite = SpriteLoader.loadSprite((Texture)new Texture().load(data[1]), vertexSize, (Shader)new Shader().load(data[0]));
        this.texture = loadedSprite.texture;
        this.shader = loadedSprite.shader;
        this.vertexBuffer = loadedSprite.vertexBuffer;
        this.uvBuffer = loadedSprite.uvBuffer;
        return this;
    }
}
