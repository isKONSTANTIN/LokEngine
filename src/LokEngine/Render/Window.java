package LokEngine.Render;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.Tools.Input.Keyboard;
import LokEngine.Tools.Input.Mouse;
import LokEngine.Tools.Logger;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

public class Window {
    private Camera camera;
    private GUICanvas canvas;
    private FrameBuilder frameBuilder;

    private Keyboard keyboard;
    private Mouse mouse;

    private Vector2i resolution;
    private String title;
    private long id;
    private boolean fullscreen = false;
    private boolean isOpened = false;

    public boolean isFullscreen() {
        return fullscreen;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public Camera getCamera() {
        return camera;
    }

    public String getTitle() {
        return title;
    }

    public GUICanvas getCanvas() {
        return canvas;
    }

    public FrameBuilder getFrameBuilder() {
        return frameBuilder;
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(id, title);
    }

    public long getId() {
        return id;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void open(boolean fullscreen, boolean vSync, Vector2i resolution, String[] pathsWindowIcon) {
        if (!isOpened) {
            this.fullscreen = fullscreen;

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

            if (fullscreen) {
                GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                this.resolution = new Vector2i(mode.width(), mode.height());
            } else {
                this.resolution = resolution;
            }

            id = glfwCreateWindow(resolution.x, resolution.y, "LokEngine Application", fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

            if (id == NULL) {
                Logger.error("Failed to create the window", "LokEngine_Window");
                return;
            }

            glfwMakeContextCurrent(id);
            GL.createCapabilities();
            this.setIcon(pathsWindowIcon);

            glfwSwapInterval(vSync ? 1 : 0);
            glfwShowWindow(id);

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            GL30.glBindVertexArray(GL30.glGenVertexArrays());

            isOpened = true;
            camera = new Camera(this);
            keyboard = new Keyboard(this);
            mouse = new Mouse(this);
            canvas = new GUICanvas(new Vector2i(), resolution);
            canvas.properties.window = this;

            try {
                frameBuilder = new FrameBuilder(this);
                frameBuilder.getBuilderProperties().init();
            } catch (Exception e) {
                Logger.error("Fail init Frame Builder!", "LokEngine_Window");
                Logger.printException(e);
                return;
            }
        }
    }

    public void open(boolean fullscreen, boolean vSync, Vector2i resolution) {
        open(fullscreen, vSync, resolution,
                new String[]{
                        "#/resources/textures/EngineIcon16.png",
                        "#/resources/textures/EngineIcon32.png",
                        "#/resources/textures/EngineIcon128.png"});
    }

    public void close() {
        glfwDestroyWindow(id);
        keyboard.close();
        isOpened = false;
    }

    public void update() {
        if (isOpened) {

            try {
                frameBuilder.build();
                glfwSwapBuffers(id);
            } catch (Exception e) {
                Logger.error("Fail build frame!", "LokEngine_Window");
                Logger.printException(e);
            }

            glfwPollEvents();
            mouse.update();
        }
    }

    public void setIcon(String[] paths) {
        GLFWImage.Buffer iconGB = GLFWImage.malloc(paths.length);

        for (String path : paths) {
            try {
                Object[] image = TextureLoader.loadTextureInBuffer(path);
                GLFWImage GLFWimage = GLFWImage.create().set(
                        ((BufferedImage) image[1]).getWidth(),
                        ((BufferedImage) image[1]).getHeight(),
                        (ByteBuffer) image[0]);

                iconGB.put(GLFWimage);
            } catch (Exception e) {
                Logger.warning("Fail load icon", "LokEngine_Window");
                Logger.printException(e);
            }
        }
        iconGB.flip();

        glfwSetWindowIcon(id, iconGB);
    }

    public void setDrawMode(DrawMode dm) {
        if (dm == DrawMode.Display || dm == DrawMode.RawGUI) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            gluOrtho2D(0.0f, resolution.x, resolution.y, 0.0f);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glDisable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);

            if (dm == DrawMode.Display) {
                frameBuilder.getBuilderProperties().useShader(frameBuilder.getBuilderProperties().getDisplayShader());
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            } else {
                frameBuilder.getBuilderProperties().unUseShader();
                glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
            }

        } else if (dm == DrawMode.Scene) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            gluOrtho2D(0.0f, this.resolution.x / this.resolution.y, 1, 0.0f);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_ALPHA_TEST);
            glAlphaFunc(GL_GREATER, 0.1f);

            frameBuilder.getBuilderProperties().useShader(frameBuilder.getBuilderProperties().getObjectShader());
        }
    }
}
