package LokEngine.Loaders;

import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Texture;

public class SpriteLoader {

    public static Sprite loadSprite(Texture texture, float vertexSize) {
        int vertexBuffer = BufferLoader.load(new float[]
                {
                        -texture.sizeX * vertexSize / 2f * 0.000520833f, -texture.sizeY * vertexSize / 2f * 0.000520833f,
                        -texture.sizeX * vertexSize / 2f * 0.000520833f, texture.sizeY * vertexSize / 2f * 0.000520833f,
                        texture.sizeX * vertexSize / 2f * 0.000520833f, texture.sizeY * vertexSize / 2f * 0.000520833f,
                        texture.sizeX * vertexSize / 2f * 0.000520833f, -texture.sizeY * vertexSize / 2f * 0.000520833f
                }
        );

        return new Sprite(texture, vertexBuffer, 1, vertexSize);
    }

    public static Sprite loadSprite(String texturePath, float vertexSize) {
        Texture tex = TextureLoader.loadTexture(texturePath);

        int vertexBuffer = BufferLoader.load(new float[]
                {
                        -tex.sizeX * vertexSize / 2f * 0.000520833f, -tex.sizeY * vertexSize / 2f * 0.000520833f,
                        -tex.sizeX * vertexSize / 2f * 0.000520833f, tex.sizeY * vertexSize / 2f * 0.000520833f,
                        tex.sizeX * vertexSize / 2f * 0.000520833f, tex.sizeY * vertexSize / 2f * 0.000520833f,
                        tex.sizeX * vertexSize / 2f * 0.000520833f, -tex.sizeY * vertexSize / 2f * 0.000520833f
                }
        );

        return new Sprite(tex, vertexBuffer, 1, vertexSize);
    }

    public static Sprite loadSprite(String texturePath) {
        return SpriteLoader.loadSprite(texturePath, 1);
    }

}
