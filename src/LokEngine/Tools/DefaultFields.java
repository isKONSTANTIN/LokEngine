package LokEngine.Tools;

import LokEngine.Render.Shader;
import LokEngine.Render.Texture;

public class DefaultFields {
    public static Shader defaultShader;
    public static Shader displayShader;
    public static Shader postProcessingShader;
    public static Shader particlesShader;
    public static Texture unknownTexture;

    public static int defaultUVBuffer;
    public static int defaultVertexScreenBuffer;

    public static String[] pathsWindowIcon = new String[]{"#/resources/textures/EngineIcon16.png","#/resources/textures/EngineIcon32.png","#/resources/textures/EngineIcon128.png"};

}
