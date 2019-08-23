package LokEngine.Loaders;

import LokEngine.Render.Shader;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;

import java.io.IOException;

public class SpriteLoader {

    public static Sprite loadSprite(String texturePath, float vertexSize, Shader shader) throws IOException {
        Texture tex = TextureLoader.loadTexture(texturePath);

        int vertexBuffer = BufferLoader.load(new float[]
                {
                        -tex.sizeX * vertexSize / 2f * 0.000520833f, -tex.sizeY * vertexSize / 2f * 0.000520833f,
                        -tex.sizeX * vertexSize / 2f * 0.000520833f, tex.sizeY * vertexSize / 2f * 0.000520833f,
                        tex.sizeX * vertexSize / 2f * 0.000520833f, tex.sizeY * vertexSize / 2f * 0.000520833f,
                        tex.sizeX * vertexSize / 2f * 0.000520833f, -tex.sizeY * vertexSize / 2f * 0.000520833f
                }
        );

        return new Sprite(tex,vertexBuffer,DefaultFields.defaultUVBuffer,1, shader);
    }

    public static Sprite loadSprite(String texturePath, float vertexSize) throws IOException {
        return SpriteLoader.loadSprite(texturePath, vertexSize, DefaultFields.defaultShader);
    }

    public static Sprite loadSprite(String texturePath) throws IOException {
        return SpriteLoader.loadSprite(texturePath, 1, DefaultFields.defaultShader);
    }

}
