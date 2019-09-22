package LokEngine.Tools;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Render.Shader;
import LokEngine.Render.Texture;

public class DefaultFields {
    public static Shader defaultShader;
    public static Shader displayShader;
    public static Shader postProcessingShader;
    public static Shader particlesShader;
    public static Texture unknownTexture;

    public static int defaultUVBuffer = BufferLoader.load(new float[] {
            0,1,
            0,0,
            1,0,
            1,1,
    });
    public static int defaultVertexScreenBuffer;
}
