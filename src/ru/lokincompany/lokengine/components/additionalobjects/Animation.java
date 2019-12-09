package ru.lokincompany.lokengine.components.additionalobjects;

import ru.lokincompany.lokengine.loaders.BufferLoader;
import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.base64.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

import java.util.ArrayList;

public class Animation implements Saveable {

    public Texture altasTexture;
    public int vertexBuffer;
    public ArrayList<Integer> uvBuffers;
    private AtlasPositions atlasPositions;

    public Animation() {}

    public Animation(Texture atlas, AtlasPositions atlasPositions) {
        altasTexture = atlas;
        this.atlasPositions = atlasPositions;
        uvBuffers = atlasPositions.build(altasTexture);

        Vector2i size = new Vector2i(atlasPositions.startPosition.z - atlasPositions.startPosition.x, atlasPositions.startPosition.w - atlasPositions.startPosition.y);

        vertexBuffer = BufferLoader.load(new float[]
                {
                        -size.x / 2f * 0.000520833f, -size.y / 2f * 0.000520833f,
                        -size.x / 2f * 0.000520833f, size.y / 2f * 0.000520833f,
                        size.x / 2f * 0.000520833f, size.y / 2f * 0.000520833f,
                        size.x / 2f * 0.000520833f, -size.y / 2f * 0.000520833f
                }
        );
    }

    public Animation(String atlasPath, AtlasPositions atlasPositions) {
        try {
            altasTexture = TextureLoader.loadTexture(atlasPath);
        } catch (Exception e) {
            Logger.warning("Fail load atlas from path!", "LokEngine_Animation");
            return;
        }
        this.atlasPositions = atlasPositions;
        uvBuffers = atlasPositions.build(altasTexture);

        Vector2i size = new Vector2i(atlasPositions.startPosition.z - atlasPositions.startPosition.x, atlasPositions.startPosition.w - atlasPositions.startPosition.y);

        vertexBuffer = BufferLoader.load(new float[]
                {
                        -size.x / 2f * 0.000520833f, -size.y / 2f * 0.000520833f,
                        -size.x / 2f * 0.000520833f, size.y / 2f * 0.000520833f,
                        size.x / 2f * 0.000520833f, size.y / 2f * 0.000520833f,
                        size.x / 2f * 0.000520833f, -size.y / 2f * 0.000520833f
                }
        );
    }


    @Override
    public String save() {
        return Base64.toBase64(altasTexture.save() + "\n" + atlasPositions.save());
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split("\n");

        Animation loadedAnimation = new Animation((Texture) new Texture().load(data[0]), (AtlasPositions) (new AtlasPositions().load(data[1])));

        this.altasTexture = loadedAnimation.altasTexture;
        this.atlasPositions = loadedAnimation.atlasPositions;
        this.uvBuffers = loadedAnimation.uvBuffers;
        this.vertexBuffer = loadedAnimation.vertexBuffer;

        return this;
    }
}
