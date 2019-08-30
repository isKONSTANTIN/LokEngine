package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.SaveWorker.Saveable;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

public class Animation implements Saveable {

    public Texture altasTexture;
    public int vertexBuffer;
    public ArrayList<Integer> uvBuffers;
    private AtlasPositions atlasPositions;

    public Animation(){}

    public Animation(Texture atlas, AtlasPositions atlasPositions){
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

    public Animation(String atlasPath, AtlasPositions atlasPositions){
        try {
            altasTexture = TextureLoader.loadTexture(atlasPath);
        } catch (Exception e) {
            altasTexture = DefaultFields.unknownSprite.texture;
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
        return altasTexture.save() + "\n" + atlasPositions.save();
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = savedString.split(System.getProperty("line.separator"));

        Animation loadedAnimation = new Animation((Texture)new Texture().load(data[0]), (AtlasPositions)(new AtlasPositions().load(data[1])));

        this.altasTexture = loadedAnimation.altasTexture;
        this.atlasPositions = loadedAnimation.atlasPositions;
        this.uvBuffers = loadedAnimation.uvBuffers;
        this.vertexBuffer = loadedAnimation.vertexBuffer;

        return this;
    }
}
