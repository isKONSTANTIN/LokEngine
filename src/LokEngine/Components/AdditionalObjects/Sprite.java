package LokEngine.Components.AdditionalObjects;

import LokEngine.Render.Shader;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;

public class Sprite {

    public Shader shader;
    public Texture texture;
    public int vertexBuffer;
    public int uvBuffer;
    public double size;

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
}
