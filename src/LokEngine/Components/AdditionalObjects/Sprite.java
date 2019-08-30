package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.SpriteLoader;
import LokEngine.Render.Shader;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.SaveWorker.Saveable;

public class Sprite implements Saveable {

    public Shader shader;
    public Texture texture;
    public int vertexBuffer;
    public int uvBuffer;
    public double size;

    public Sprite(){ }

    public Sprite(Texture texture, int vertexBuffer, int uvBuffer){
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = 1;
        this.uvBuffer = uvBuffer;
        this.shader = DefaultFields.defaultShader;
    }

    public Sprite(Texture texture, int vertexBuffer, int uvBuffer, double size){
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = size;
        this.uvBuffer = uvBuffer;
        this.shader = DefaultFields.defaultShader;
    }

    public Sprite(Texture texture, int vertexBuffer, int uvBuffer, double size, Shader shader){
        this.texture = texture;
        this.vertexBuffer = vertexBuffer;
        this.size = size;
        this.uvBuffer = uvBuffer;
        this.shader = shader;
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
        return shader.save() + "\n" + texture.save() + "\n" + size + "\n" + SpriteLoader.getVertexSizes(this);
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = savedString.split(System.getProperty("line.separator"));
        Sprite loadedSprite = SpriteLoader.loadSprite((Texture)new Texture().load(data[1]), Float.valueOf(data[3]), (Shader)new Shader().load(data[0]));

        this.size = Double.valueOf(data[2]);
        this.texture = loadedSprite.texture;
        this.shader = loadedSprite.shader;
        this.vertexBuffer = loadedSprite.vertexBuffer;
        this.uvBuffer = loadedSprite.uvBuffer;

        return this;
    }
}
