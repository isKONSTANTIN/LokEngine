package ru.lokincompany.lokengine.render;

import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.ArrayList;

public class Animation implements Saveable {

    public Texture altasTexture;
    public VBO vertexVBO;
    public ArrayList<VBO> uvVBOs;
    private AtlasPositions atlasPositions;

    public Animation() {
    }

    public Animation(Texture atlas, AtlasPositions atlasPositions) {
        altasTexture = atlas;
        this.atlasPositions = atlasPositions;
        uvVBOs = atlasPositions.build(altasTexture);

        Vector2i size = new Vector2i(atlasPositions.startPosition.z - atlasPositions.startPosition.x, atlasPositions.startPosition.w - atlasPositions.startPosition.y);

        vertexVBO = new VBO(new float[]
                {
                        -size.x / 2f * 0.0005f, -size.y / 2f * 0.0005f,
                        -size.x / 2f * 0.0005f, size.y / 2f * 0.0005f,
                        size.x / 2f * 0.0005f, size.y / 2f * 0.0005f,
                        size.x / 2f * 0.0005f, -size.y / 2f * 0.0005f
                }
        );
    }

    public Animation(String atlasPath, AtlasPositions atlasPositions) {
        try {
            altasTexture = new Texture(atlasPath);
        } catch (Throwable e) {
            Logger.warning("Fail load atlas from path!", "LokEngine_Animation");
            return;
        }
        this.atlasPositions = atlasPositions;
        uvVBOs = atlasPositions.build(altasTexture);

        Vector2i size = new Vector2i(atlasPositions.startPosition.z - atlasPositions.startPosition.x, atlasPositions.startPosition.w - atlasPositions.startPosition.y);

        vertexVBO = new VBO(new float[]
                {
                        -size.x / 2f * 0.0005f, -size.y / 2f * 0.0005f,
                        -size.x / 2f * 0.0005f, size.y / 2f * 0.0005f,
                        size.x / 2f * 0.0005f, size.y / 2f * 0.0005f,
                        size.x / 2f * 0.0005f, -size.y / 2f * 0.0005f
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
        this.uvVBOs = loadedAnimation.uvVBOs;
        this.vertexVBO = loadedAnimation.vertexVBO;

        return this;
    }
}
