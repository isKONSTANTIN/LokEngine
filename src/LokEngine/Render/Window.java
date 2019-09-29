package LokEngine.Render;

import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Enums.DrawMode;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Input.Keyboard;
import LokEngine.Tools.Logger;
import LokEngine.Tools.Input.Mouse;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

public class Window {

    private Camera camera;
    private Vector2i resolution;
    private boolean fullscreen = false;
    private boolean isOpened = false;
    private String title;
    private long id;

    private Keyboard keyboard;
    private Mouse mouse;

    private long openALDevice;
    private long openALContext;
    ALCCapabilities alcCapabilities;
    ALCapabilities alCapabilities;

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
    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(id,title);
    }

    public long getId(){ return id; }

    public Keyboard getKeyboard(){ return keyboard; }
    public Mouse getMouse(){ return mouse; }

    public void open(boolean fullscreen, boolean vSync, Vector2i resolution) {
        if (!isOpened) {
            this.fullscreen = fullscreen;

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            glfwWindowHint(GLFW_SAMPLES, 8);

            if (fullscreen) {
                GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                this.resolution = new Vector2i(mode.width(), mode.height());
            } else {
                this.resolution = resolution;
            }

            id = glfwCreateWindow(resolution.x, resolution.y, "LokEngine Application", fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

            if (id == NULL){
                Logger.error("Failed to create the window", "LokEngine_Window");
                return;
            }

            glfwMakeContextCurrent(id);
            GL.createCapabilities();
            this.setIcon(DefaultFields.pathsWindowIcon);

            glfwSwapInterval(vSync ? 1 : 0);
            glfwShowWindow(id);

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            GL30.glBindVertexArray(GL30.glGenVertexArrays());

            isOpened = true;
            camera = new Camera();
            keyboard = new Keyboard(this);
            mouse = new Mouse(this);
            String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
            openALDevice = alcOpenDevice(defaultDeviceName);
            openALContext = ALC10.alcCreateContext(openALDevice, new int[]{0});
            alcMakeContextCurrent(openALContext);

            alcCapabilities = ALC.createCapabilities(openALDevice);
            alCapabilities = AL.createCapabilities(alcCapabilities);
        }
    }

    public void close() {
        glfwDestroyWindow(id);
        alcDestroyContext(openALContext);
        alcCloseDevice(openALDevice);
        keyboard.close();
        isOpened = false;
    }

    public void update() {
        if (isOpened){
            glfwSwapBuffers(id);
            glfwPollEvents();
            mouse.update();
        }
    }

    public void setIcon(String[] paths){
        GLFWImage.Buffer iconGB = GLFWImage.malloc(paths.length);

        for (String path : paths) {
            try {
                Object[] image = TextureLoader.loadTextureInBuffer(path);
                GLFWImage GLFWimage = GLFWImage.create().set(
                        ((BufferedImage)image[1]).getWidth(),
                        ((BufferedImage) image[1]).getHeight(),
                        (ByteBuffer)image[0]);

                iconGB.put(GLFWimage);
            } catch (Exception e) {
                Logger.warning("Fail load icon", "LokEngine_Window");
                Logger.printException(e);
            }
        }
        iconGB.flip();

        glfwSetWindowIcon(id,iconGB);
    }

    public void setDrawMode(DrawMode dm) {
        if (dm == DrawMode.Display || dm == DrawMode.RawGUI) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            gluOrtho2D(0.0f, resolution.x, resolution.y, 0.0f);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glDisable(GL_DEPTH_TEST);
            glDisable(GL_MULTISAMPLE);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);

            if (dm == DrawMode.Display) {
                Shader.use(DefaultFields.displayShader);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            } else {
                Shader.unUse();
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

            Shader.use(DefaultFields.defaultShader);
        }
    }
}
