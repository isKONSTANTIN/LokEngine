package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.util.ArrayList;

public class Animation {

    public Texture altasTexture;
    public int vertexBuffer;
    public ArrayList<Integer> uvBuffers;

    public Animation(String atlasPath, AtlasPositions atlasPositions){
        try {
            altasTexture = TextureLoader.loadTexture(atlasPath);
        } catch (Exception e) {
            altasTexture = DefaultFields.unknownSprite.texture;
        }
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

}
