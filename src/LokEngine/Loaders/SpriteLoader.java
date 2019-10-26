package LokEngine.Loaders;

import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Texture;
import LokEngine.Tools.Utilities.Vector4i;
import org.lwjgl.util.vector.Vector2f;

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

        return new Sprite(texture, vertexBuffer, vertexSize);
    }

    public static Sprite loadSprite(Texture texture, float vertexSize, Vector4i imagePosFromAtlas){
        Vector2f fistPoint   = new Vector2f((float) imagePosFromAtlas.x / (float) texture.sizeX, (float) imagePosFromAtlas.w / (float) texture.sizeY);
        Vector2f secondPoint = new Vector2f((float) imagePosFromAtlas.x / (float) texture.sizeX, (float) imagePosFromAtlas.y / (float) texture.sizeY);
        Vector2f thirdPoint  = new Vector2f((float) imagePosFromAtlas.z / (float) texture.sizeX, (float) imagePosFromAtlas.y / (float) texture.sizeY);
        Vector2f fourthPoint = new Vector2f((float) imagePosFromAtlas.z / (float) texture.sizeX, (float) imagePosFromAtlas.w / (float) texture.sizeY);

        int vertexBuffer = BufferLoader.load(new float[]
                {
                        -texture.sizeX * vertexSize / 2f * 0.000520833f, -texture.sizeY * vertexSize / 2f * 0.000520833f,
                        -texture.sizeX * vertexSize / 2f * 0.000520833f, texture.sizeY * vertexSize / 2f * 0.000520833f,
                        texture.sizeX * vertexSize / 2f * 0.000520833f, texture.sizeY * vertexSize / 2f * 0.000520833f,
                        texture.sizeX * vertexSize / 2f * 0.000520833f, -texture.sizeY * vertexSize / 2f * 0.000520833f
                }
        );

        return new Sprite(texture, vertexBuffer,vertexSize, BufferLoader.load(new float[]{
                fistPoint.x, fistPoint.y,
                secondPoint.x, secondPoint.y,
                thirdPoint.x, thirdPoint.y,
                fourthPoint.x, fourthPoint.y
        }));
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

        return new Sprite(tex, vertexBuffer, vertexSize);
    }

    public static Sprite loadSprite(String texturePath) {
        return SpriteLoader.loadSprite(texturePath, 1);
    }

}
