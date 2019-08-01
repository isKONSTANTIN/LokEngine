package LokEngine.Tools;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Render.Shader;
import LokEngine.Components.AdditionalObjects.Sprite;

public class DefaultFields {
    public static Shader defaultShader;
    public static Shader DisplayShader;
    public static Shader PostProcessingShader;

    public static Sprite unknownSprite;
    public static int defaultUVBuffer = BufferLoader.load(new float[] {
            0,1,
            0,0,
            1,0,
            1,1,
    });
    public static int defaultVertexScreenBuffer;
}
