package ru.lokincompany.lokengine.render;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector4i;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Sprite implements Saveable {

    public Texture texture;
    public VBO vertexVBO;
    public VBO uvVBO;
    public int renderMode = GL_QUADS;
    public double size;

    private float vertexSize;

    public Sprite() {
    }

    public Sprite(String path) {
        loadSprite(path);
    }

    public Sprite(Texture texture, VBO vertexBuffer, float vertexSize) {
        this(texture, vertexBuffer, 1, vertexSize, null);
    }

    public Sprite(Texture texture, VBO vertexBuffer, float vertexSize, VBO uvVBO) {
        this(texture, vertexBuffer, 1, vertexSize, uvVBO);
    }

    public Sprite(Texture texture, VBO vertexVBO, double size, float vertexSize, VBO uvVBO) {
        this.texture = texture;
        this.vertexVBO = vertexVBO;
        this.size = size;
        this.vertexSize = vertexSize;
        this.uvVBO = uvVBO;
    }

    public float getVertexSize() {
        return vertexSize;
    }

    public boolean equals(Object obj) {
        Sprite objs = (Sprite) obj;
        return objs.texture.equals(texture) &&
                objs.vertexVBO == vertexVBO &&
                objs.size == size;
    }

    public void loadSprite(Texture texture, float vertexSize) {
        VBO vertexVBO = new VBO(new float[]
                {
                        -texture.getSizeX() * vertexSize / 2f * 0.0005f, -texture.getSizeY() * vertexSize / 2f * 0.0005f,
                        -texture.getSizeX() * vertexSize / 2f * 0.0005f, texture.getSizeY() * vertexSize / 2f * 0.0005f,
                        texture.getSizeX() * vertexSize / 2f * 0.0005f, texture.getSizeY() * vertexSize / 2f * 0.0005f,
                        texture.getSizeX() * vertexSize / 2f * 0.0005f, -texture.getSizeY() * vertexSize / 2f * 0.0005f
                }
        );

        this.texture = texture;
        this.vertexVBO = vertexVBO;
        this.size = 1;
        this.vertexSize = vertexSize;
    }

    public void loadSprite(Texture texture, float vertexSize, Vector4i imagePosFromAtlas) {
        Vector2f fistPoint = new Vector2f((float) imagePosFromAtlas.x / (float) texture.getSizeX(), (float) imagePosFromAtlas.w / (float) texture.getSizeY());
        Vector2f secondPoint = new Vector2f((float) imagePosFromAtlas.x / (float) texture.getSizeX(), (float) imagePosFromAtlas.y / (float) texture.getSizeY());
        Vector2f thirdPoint = new Vector2f((float) imagePosFromAtlas.z / (float) texture.getSizeX(), (float) imagePosFromAtlas.y / (float) texture.getSizeY());
        Vector2f fourthPoint = new Vector2f((float) imagePosFromAtlas.z / (float) texture.getSizeX(), (float) imagePosFromAtlas.w / (float) texture.getSizeY());

        VBO vertexVBO = new VBO(new float[]
                {
                        -texture.getSizeX() * vertexSize / 2f * 0.0005f, -texture.getSizeY() * vertexSize / 2f * 0.0005f,
                        -texture.getSizeX() * vertexSize / 2f * 0.0005f, texture.getSizeY() * vertexSize / 2f * 0.0005f,
                        texture.getSizeX() * vertexSize / 2f * 0.0005f, texture.getSizeY() * vertexSize / 2f * 0.0005f,
                        texture.getSizeX() * vertexSize / 2f * 0.0005f, -texture.getSizeY() * vertexSize / 2f * 0.0005f
                }
        );

        this.texture = texture;
        this.vertexVBO = vertexVBO;
        this.size = 1;
        this.vertexSize = vertexSize;
        this.uvVBO = new VBO(new float[]{
                fistPoint.x, fistPoint.y,
                secondPoint.x, secondPoint.y,
                thirdPoint.x, thirdPoint.y,
                fourthPoint.x, fourthPoint.y
        });
    }

    public void loadSprite(String texturePath, float vertexSize) {
        Texture tex = new Texture(texturePath);

        VBO vertexVBO = new VBO(new float[]
                {
                        -tex.getSizeX() * vertexSize / 2f * 0.0005f, -tex.getSizeY() * vertexSize / 2f * 0.0005f,
                        -tex.getSizeX() * vertexSize / 2f * 0.0005f, tex.getSizeY() * vertexSize / 2f * 0.0005f,
                        tex.getSizeX() * vertexSize / 2f * 0.0005f, tex.getSizeY() * vertexSize / 2f * 0.0005f,
                        tex.getSizeX() * vertexSize / 2f * 0.0005f, -tex.getSizeY() * vertexSize / 2f * 0.0005f
                }
        );

        this.texture = tex;
        this.vertexVBO = vertexVBO;
        this.size = 1;
        this.vertexSize = vertexSize;
    }

    public void loadSprite(String texturePath) {
        loadSprite(texturePath, 1);
    }

    @Override
    public String save() {
        return Base64.toBase64(texture.save() + "\n" + size + "\n" + vertexSize);
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split("\n");
        this.vertexSize = Float.parseFloat(data[2]);
        this.size = Double.parseDouble(data[1]);
        loadSprite((Texture) new Texture().load(data[0]), vertexSize);

        return this;
    }
}
