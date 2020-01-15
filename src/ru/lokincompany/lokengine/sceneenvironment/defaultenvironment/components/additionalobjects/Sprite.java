package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects;

import ru.lokincompany.lokengine.loaders.SpriteLoader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

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

    @Override
    public String save() {
        return Base64.toBase64(texture.save() + "\n" + size + "\n" + vertexSize);
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split("\n");
        this.vertexSize = Float.parseFloat(data[2]);
        this.size = Double.parseDouble(data[1]);

        Sprite loadedSprite = SpriteLoader.loadSprite((Texture) new Texture().load(data[0]), vertexSize);
        this.texture = loadedSprite.texture;
        this.vertexVBO = loadedSprite.vertexVBO;
        return this;
    }
}
