package LokEngine.Loaders;

import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Shader;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;

import java.io.IOException;
import java.util.HashMap;

public class SpriteLoader {

    private static HashMap<Sprite,Float> vertexSizes = new HashMap<>();

    public static Float getVertexSizes(Sprite sprite){
        if (vertexSizes.containsKey(sprite)){
            return vertexSizes.get(sprite);
        }
        return null;
    }

    public static Sprite loadSprite(Texture texture, float vertexSize, Shader shader){
        int vertexBuffer = BufferLoader.load(new float[]
                {
                        -texture.sizeX * vertexSize / 2f * 0.000520833f, -texture.sizeY * vertexSize / 2f * 0.000520833f,
                        -texture.sizeX * vertexSize / 2f * 0.000520833f, texture.sizeY * vertexSize / 2f * 0.000520833f,
                        texture.sizeX * vertexSize / 2f * 0.000520833f, texture.sizeY * vertexSize / 2f * 0.000520833f,
                        texture.sizeX * vertexSize / 2f * 0.000520833f, -texture.sizeY * vertexSize / 2f * 0.000520833f
                }
        );

        Sprite newSprite = new Sprite(texture,vertexBuffer,DefaultFields.defaultUVBuffer,1, shader);

        vertexSizes.put(newSprite, vertexSize);

        return newSprite;
    }

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

        Sprite newSprite = new Sprite(tex,vertexBuffer,DefaultFields.defaultUVBuffer,1, shader);

        vertexSizes.put(newSprite, vertexSize);

        return newSprite;
    }

    public static Sprite loadSprite(String texturePath, float vertexSize) throws IOException {
        return SpriteLoader.loadSprite(texturePath, vertexSize, DefaultFields.defaultShader);
    }

    public static Sprite loadSprite(String texturePath) throws IOException {
        return SpriteLoader.loadSprite(texturePath, 1, DefaultFields.defaultShader);
    }

}
