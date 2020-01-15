package ru.lokincompany.lokengine.loaders;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.Sprite;
import ru.lokincompany.lokengine.tools.vectori.Vector4i;

public class SpriteLoader {

    public static Sprite loadSprite(Texture texture, float vertexSize) {
        VBO vertexVBO = new VBO(new float[]
                {
                        -texture.sizeX * vertexSize / 2f * 0.0005f, -texture.sizeY * vertexSize / 2f * 0.0005f,
                        -texture.sizeX * vertexSize / 2f * 0.0005f, texture.sizeY * vertexSize / 2f * 0.0005f,
                        texture.sizeX * vertexSize / 2f * 0.0005f, texture.sizeY * vertexSize / 2f * 0.0005f,
                        texture.sizeX * vertexSize / 2f * 0.0005f, -texture.sizeY * vertexSize / 2f * 0.0005f
                }
        );

        return new Sprite(texture, vertexVBO, vertexSize);
    }

    public static Sprite loadSprite(Texture texture, float vertexSize, Vector4i imagePosFromAtlas) {
        Vector2f fistPoint = new Vector2f((float) imagePosFromAtlas.x / (float) texture.sizeX, (float) imagePosFromAtlas.w / (float) texture.sizeY);
        Vector2f secondPoint = new Vector2f((float) imagePosFromAtlas.x / (float) texture.sizeX, (float) imagePosFromAtlas.y / (float) texture.sizeY);
        Vector2f thirdPoint = new Vector2f((float) imagePosFromAtlas.z / (float) texture.sizeX, (float) imagePosFromAtlas.y / (float) texture.sizeY);
        Vector2f fourthPoint = new Vector2f((float) imagePosFromAtlas.z / (float) texture.sizeX, (float) imagePosFromAtlas.w / (float) texture.sizeY);

        VBO vertexVBO = new VBO(new float[]
                {
                        -texture.sizeX * vertexSize / 2f * 0.0005f, -texture.sizeY * vertexSize / 2f * 0.0005f,
                        -texture.sizeX * vertexSize / 2f * 0.0005f, texture.sizeY * vertexSize / 2f * 0.0005f,
                        texture.sizeX * vertexSize / 2f * 0.0005f, texture.sizeY * vertexSize / 2f * 0.0005f,
                        texture.sizeX * vertexSize / 2f * 0.0005f, -texture.sizeY * vertexSize / 2f * 0.0005f
                }
        );

        return new Sprite(texture, vertexVBO, vertexSize, new VBO(new float[]{
                fistPoint.x, fistPoint.y,
                secondPoint.x, secondPoint.y,
                thirdPoint.x, thirdPoint.y,
                fourthPoint.x, fourthPoint.y
        }));
    }

    public static Sprite loadSprite(String texturePath, float vertexSize) {
        Texture tex = TextureLoader.loadTexture(texturePath);

        VBO vertexVBO = new VBO(new float[]
                {
                        -tex.sizeX * vertexSize / 2f * 0.0005f, -tex.sizeY * vertexSize / 2f * 0.0005f,
                        -tex.sizeX * vertexSize / 2f * 0.0005f, tex.sizeY * vertexSize / 2f * 0.0005f,
                        tex.sizeX * vertexSize / 2f * 0.0005f, tex.sizeY * vertexSize / 2f * 0.0005f,
                        tex.sizeX * vertexSize / 2f * 0.0005f, -tex.sizeY * vertexSize / 2f * 0.0005f
                }
        );

        return new Sprite(tex, vertexVBO, vertexSize);
    }

    public static Sprite loadSprite(String texturePath) {
        return SpriteLoader.loadSprite(texturePath, 1);
    }

}
