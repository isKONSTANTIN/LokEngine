package ru.lokincompany.lokengine.render.frame;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokengine.tools.vectori.Vector4i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

public class RenderProperties {
    private Shader activeShader;

    private Shader objectShader;
    private Shader displayShader;
    private Shader particlesShader;
    private Window window;
    private Texture unknownTexture;
    private Vector4i orthoView = new Vector4i();
    private DrawMode activeDrawMode;
    private VBO uvVBO;
    private VBO vertexScreenVBO;

    public RenderProperties(Window window) {
        this.window = window;
    }

    public Shader getActiveShader() {
        return activeShader;
    }

    public Shader getObjectShader() {
        return objectShader;
    }

    public Shader getDisplayShader() {
        return displayShader;
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

    public DrawMode getActiveDrawMode() {
        return activeDrawMode;
    }

    public VBO getUVVBO() {
        return uvVBO;
    }

    public VBO getVertexScreenBuffer() {
        return vertexScreenVBO;
    }

    public void useShader(Shader shader) {
        ARBShaderObjects.glUseProgramObjectARB(shader.program);
        activeShader = shader;
    }

    public void unUseShader() {
        ARBShaderObjects.glUseProgramObjectARB(0);
        activeShader = null;
    }

    public void update() {
        Vector2i windowResolution = window.getResolution();

        useShader(displayShader);
        window.getCamera().updateProjection(windowResolution.x, windowResolution.y, 1);

        useShader(particlesShader);
        particlesShader.setUniformData("ObjectModelMatrix", MatrixTools.createModelMatrix(0, new Vector3f(0, 0, 0)));

        useShader(objectShader);
        window.getCamera().setFieldOfView(window.getCamera().fieldOfView);

        vertexScreenVBO.createNew();

        vertexScreenVBO.putData(new float[]{
                -windowResolution.x / 2f, windowResolution.y / 2f,
                -windowResolution.x / 2f, -windowResolution.y / 2f,
                windowResolution.x / 2f, -windowResolution.y / 2f,
                windowResolution.x / 2f, windowResolution.y / 2f});
    }

    public void init() throws Exception {
        objectShader = new Shader("#/resources/shaders/DefaultVertShader.glsl", "#/resources/shaders/DefaultFragShader.glsl");
        displayShader = new Shader("#/resources/shaders/DisplayVertShader.glsl", "#/resources/shaders/DisplayFragShader.glsl");
        particlesShader = new Shader("#/resources/shaders/ParticleVertShader.glsl", "#/resources/shaders/ParticleFragShader.glsl");

        unknownTexture = new Texture("#/resources/textures/unknown.png");

        uvVBO = new VBO(new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1,
        });

        vertexScreenVBO = new VBO();

        update();
    }

    public Vector4i getOrthoView() {
        return orthoView;
    }

    public void setOrthoView(Vector4i view) {
        orthoView = new Vector4i(view.x, view.y, view.z, view.w);
        gluOrtho2D(-view.z, view.x - view.z, view.y - view.w, -view.w);
    }

    public void setDrawMode(DrawMode dm) {
        setDrawMode(dm, window.getResolution());
    }

    public void setDrawMode(DrawMode dm, Vector2i resolution) {
        setDrawMode(dm, resolution, new Vector4i(resolution.x, resolution.y, orthoView.z, orthoView.w));
    }

    public void setDrawMode(DrawMode dm, Vector2i resolution, Vector4i orthoView) {
        glViewport(0, 0, resolution.x, resolution.y);
        activeDrawMode = dm;
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

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

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
