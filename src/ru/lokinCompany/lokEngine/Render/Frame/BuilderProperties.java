package ru.lokinCompany.lokEngine.Render.Frame;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.util.vector.Vector3f;
import ru.lokinCompany.lokEngine.Loaders.BufferLoader;
import ru.lokinCompany.lokEngine.Loaders.ShaderLoader;
import ru.lokinCompany.lokEngine.Loaders.TextureLoader;
import ru.lokinCompany.lokEngine.Render.Enums.DrawMode;
import ru.lokinCompany.lokEngine.Render.Shader;
import ru.lokinCompany.lokEngine.Render.Texture;
import ru.lokinCompany.lokEngine.Render.Window.Window;
import ru.lokinCompany.lokEngine.Tools.MatrixCreator;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector4i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

public class BuilderProperties {
    private Shader activeShader;

    private Shader objectShader;
    private Shader displayShader;
    private Shader postProcessingShader;
    private Shader particlesShader;
    private Window window;
    private Texture unknownTexture;
    private Vector4i orthoView = new Vector4i();

    private int UVBuffer;
    private int vertexScreenBuffer;

    public Shader getActiveShader() {
        return activeShader;
    }

    public Shader getObjectShader() {
        return objectShader;
    }

    public Shader getDisplayShader() {
        return displayShader;
    }

    public Shader getPostProcessingShader() {
        return postProcessingShader;
    }

    public Shader getParticlesShader() {
        return particlesShader;
    }

    public Window getBuilderWindow() {
        return window;
    }

    public Texture getUnknownTexture() {
        return unknownTexture;
    }

    public int getUVBuffer() {
        return UVBuffer;
    }

    public int getVertexScreenBuffer() {
        return vertexScreenBuffer;
    }

    public void useShader(Shader shader) {
        ARBShaderObjects.glUseProgramObjectARB(shader.program);
        activeShader = shader;
    }

    public void unUseShader() {
        ARBShaderObjects.glUseProgramObjectARB(0);
        activeShader = null;
    }

    public BuilderProperties(Window window) {
        this.window = window;
    }

    public void update() {
        Vector2i windowResolution = window.getResolution();

        useShader(postProcessingShader);
        window.getCamera().updateProjection(windowResolution.x, windowResolution.y, 1);

        useShader(displayShader);
        window.getCamera().updateProjection(windowResolution.x, windowResolution.y, 1);

        useShader(particlesShader);
        MatrixCreator.PutMatrixInShader(particlesShader, "ObjectModelMatrix", MatrixCreator.CreateModelMatrix(0, new Vector3f(0, 0, 0)));

        useShader(objectShader);
        window.getCamera().setFieldOfView(1);

        if (vertexScreenBuffer != 0) BufferLoader.unload(vertexScreenBuffer);

        vertexScreenBuffer = BufferLoader.load(new float[]{
                -windowResolution.x / 2f, windowResolution.y / 2f,
                -windowResolution.x / 2f, -windowResolution.y / 2f,
                windowResolution.x / 2f, -windowResolution.y / 2f,
                windowResolution.x / 2f, windowResolution.y / 2f});
    }

    public void init() throws Exception {
        objectShader = ShaderLoader.loadShader("#/resources/shaders/DefaultVertShader.glsl", "#/resources/shaders/DefaultFragShader.glsl");
        displayShader = ShaderLoader.loadShader("#/resources/shaders/DisplayVertShader.glsl", "#/resources/shaders/DisplayFragShader.glsl");
        postProcessingShader = ShaderLoader.loadShader("#/resources/shaders/BlurVertShader.glsl", "#/resources/shaders/BlurFragShader.glsl");
        particlesShader = ShaderLoader.loadShader("#/resources/shaders/ParticleVertShader.glsl", "#/resources/shaders/ParticleFragShader.glsl");

        unknownTexture = TextureLoader.loadTexture("#/resources/textures/unknown.png");

        update();

        UVBuffer = BufferLoader.load(new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1,
        });
    }

    public void setOrthoView(Vector4i view){
        orthoView = new Vector4i(view.x, view.y, view.z, view.w);
        gluOrtho2D(-view.z, view.x - view.z, view.y - view.w, -view.w);
    }

    public Vector4i getOrthoView(){
        return orthoView;
    }

    public void setDrawMode(DrawMode dm) {
        setDrawMode(dm, window.getResolution());
    }

    public void setDrawMode(DrawMode dm, Vector2i resolution) {
        setDrawMode(dm, resolution, new Vector4i(resolution.x, resolution.y, orthoView.z, orthoView.w));
    }

    public void setDrawMode(DrawMode dm, Vector2i resolution, Vector4i orthoView) {
        glViewport(0, 0, resolution.x, resolution.y);
        if (dm == DrawMode.Display || dm == DrawMode.RawGUI) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            setOrthoView(orthoView);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glDisable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
        } else if (dm == DrawMode.Scene) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            gluOrtho2D(0.0f, resolution.x / (float) resolution.y, 1, 0.0f);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_ALPHA_TEST);
            glAlphaFunc(GL_GREATER, 0.1f);
        }

        if (dm == DrawMode.Display) {
            useShader(getDisplayShader());
        } else if (dm == DrawMode.Scene) {
            useShader(getObjectShader());
        } else {
            unUseShader();
        }
    }
}
