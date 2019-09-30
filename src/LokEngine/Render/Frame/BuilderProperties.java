package LokEngine.Render.Frame;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Shader;
import LokEngine.Render.Texture;
import LokEngine.Render.Window;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniform2f;

public class BuilderProperties {
    private Shader activeShader;

    private Shader objectShader;
    private Shader displayShader;
    private Shader postProcessingShader;
    private Shader particlesShader;
    private Window window;
    private Texture unknownTexture;

    private int UVBuffer;
    private int vertexScreenBuffer;

    public Shader getActiveShader(){return activeShader;}

    public Shader getObjectShader(){ return objectShader; }
    public Shader getDisplayShader(){ return displayShader; }
    public Shader getPostProcessingShader(){ return postProcessingShader; }
    public Shader getParticlesShader(){ return particlesShader; }
    public Window getBuilderWindow(){ return window; }
    public Texture getUnknownTexture(){ return unknownTexture; }

    public int getUVBuffer(){ return UVBuffer; }
    public int getVertexScreenBuffer(){ return vertexScreenBuffer; }

    public void useShader(Shader shader){
        ARBShaderObjects.glUseProgramObjectARB(shader.program);
        activeShader = shader;
    }

    public void unUseShader(){
        ARBShaderObjects.glUseProgramObjectARB(0);
        activeShader = null;
    }

    public BuilderProperties(Window window) {
        this.window = window;
    }

    public void init() throws Exception {
        objectShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl", "#/resources/shaders/DefaultFragShader.glsl");
        displayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl", "#/resources/shaders/DisplayFragShader.glsl");
        postProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl", "#/resources/shaders/BlurFragShader.glsl");
        particlesShader = ShaderLoader.loadShader("#/resources/shaders/ParticleVertShader.glsl", "#/resources/shaders/ParticleFragShader.glsl");

        unknownTexture = TextureLoader.loadTexture("#/resources/textures/unknown.png");

        useShader(postProcessingShader);
        window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);

        useShader(displayShader);
        glUniform2f(glGetUniformLocation(activeShader.program, "screenSize"), window.getResolution().x, window.getResolution().y);
        window.getCamera().updateProjection(window.getResolution().x, window.getResolution().y, 1);

        useShader(particlesShader);
        MatrixCreator.PutMatrixInShader(particlesShader,"ObjectModelMatrix",MatrixCreator.CreateModelMatrix(0,new Vector3f(0,0,0)));

        useShader(objectShader);
        window.getCamera().setFieldOfView(1);

        Vector2i windowResolution = window.getResolution();

        vertexScreenBuffer = BufferLoader.load(new float[]{-windowResolution.x / 2, windowResolution.y / 2, -windowResolution.x / 2, -windowResolution.y / 2, windowResolution.x / 2, -windowResolution.y / 2, windowResolution.x / 2, windowResolution.y / 2});

        UVBuffer = BufferLoader.load(new float[] {
                0,1,
                0,0,
                1,0,
                1,1,
        });
    }


}
