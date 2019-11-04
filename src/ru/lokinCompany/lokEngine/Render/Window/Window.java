package ru.lokinCompany.lokEngine.Render.Window;

import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.MouseRaycastStatus;
import ru.lokinCompany.lokEngine.GUI.Canvases.GUICanvas;
import ru.lokinCompany.lokEngine.Loaders.TextureLoader;
import ru.lokinCompany.lokEngine.Render.Camera;
import ru.lokinCompany.lokEngine.Render.Enums.DrawMode;
import ru.lokinCompany.lokEngine.Render.Frame.FrameBuilder;
import ru.lokinCompany.lokEngine.Tools.Input.Keyboard;
import ru.lokinCompany.lokEngine.Tools.Input.Mouse;
import ru.lokinCompany.lokEngine.Tools.Logger;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
    private boolean isInited = false;

    public boolean isFullscreen() {
        return fullscreen;
    }

    public boolean isInited() {
        return isInited;
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

    public void setCloseEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowCloseCallback(id, new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long l) {
                event.execute(window, null);
            }
        });
    }

    public void setResizeEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowSizeCallback(id, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                window.resolution.x = i;
                window.resolution.y = i;
                event.execute(window, new Integer[] {i, i1});
            }
        });
    }

    public void setMoveEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowPosCallback(id, new GLFWWindowPosCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                event.execute(window, new Integer[] {i, i1});
            }
        });
    }

    public void setIconifyEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowIconifyCallback(id, new GLFWWindowIconifyCallback() {
            @Override
            public void invoke(long l, boolean b) {
                event.execute(window, new Boolean[] {b});
            }
        });
    }

    public void setMaximizeEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowMaximizeCallback(id, new GLFWWindowMaximizeCallback() {
            @Override
            public void invoke(long l, boolean b) {
                event.execute(window, new Boolean[] {b});
            }
        });
    }

    public void setFocusEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowFocusCallback(id, new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long l, boolean b) {
                event.execute(window, new Boolean[] {b});
            }
        });
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setPosition(Vector2i position){
        glfwSetWindowPos(id,position.x,position.y);
    }

    public Vector2i getPosition(){
        IntBuffer xBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer yBuffer = BufferUtils.createIntBuffer(1);

        glfwGetWindowPos(id,xBuffer, yBuffer);

        return new Vector2i(xBuffer.get(0),yBuffer.get(0));
    }

    public void iconify(){
        glfwIconifyWindow(id);
    }

    public void restore(){
        glfwRestoreWindow(id);
    }

    public void show(){
        glfwShowWindow(id);
    }

    public void hide(){
        glfwHideWindow(id);
    }

    public void open(boolean fullscreen, boolean vSync, Vector2i resolution, String[] pathsWindowIcon) {
        if (!isInited) {
            this.fullscreen = fullscreen;

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

            if (fullscreen) {
                GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                this.resolution = new Vector2i(mode.width(), mode.height());
                resolution = this.resolution;
            } else {
                this.resolution = resolution;
            }

            id = glfwCreateWindow(resolution.x, resolution.y, "lokEngine Application", fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

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

            isInited = true;
            camera = new Camera(this);
            keyboard = new Keyboard(this);
            mouse = new Mouse(this);
            canvas = new GUICanvas(new Vector2i(), resolution);
            canvas.properties.window = this;
            canvas.properties.mouseRaycastStatus = new MouseRaycastStatus(mouse);

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
        isInited = false;
    }

    public void update() {
        if (isInited) {
            try {
                frameBuilder.build();
                glfwSwapBuffers(id);
            } catch (Exception e) {
                Logger.error("Fail build frame!", "LokEngine_Window");
                Logger.printException(e);
            }

            glfwPollEvents();
            mouse.update();
            canvas.properties.mouseRaycastStatus.touched = false;
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
                return;
            }
        }
        iconGB.flip();

        glfwSetWindowIcon(id, iconGB);
    }

    public void setCursor(String path){
        GLFWImage GLFWimage;
        try {
            Object[] image = TextureLoader.loadTextureInBuffer(path);
            GLFWimage = GLFWImage.create().set(((BufferedImage) image[1]).getWidth(), ((BufferedImage) image[1]).getHeight(), (ByteBuffer) image[0]);
        } catch (Exception e) {
            Logger.warning("Fail load icon", "LokEngine_Window");
            Logger.printException(e);
            return;
        }

        long cursor = GLFW.glfwCreateCursor(GLFWimage,0,0);
        GLFW.glfwSetCursor(id, cursor);
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
            } else {
                frameBuilder.getBuilderProperties().unUseShader();
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
