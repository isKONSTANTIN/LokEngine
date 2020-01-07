package ru.lokincompany.lokengine.render.window;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import ru.lokincompany.lokengine.gui.additionalobjects.MouseRaycastStatus;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.Camera;
import ru.lokincompany.lokengine.render.VAO;
import ru.lokincompany.lokengine.render.frame.FrameBuilder;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.input.Keyboard;
import ru.lokincompany.lokengine.tools.input.Mouse;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private Camera camera;
    private GUICanvas canvas;
    private FrameBuilder frameBuilder;

    private VAO vao;

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

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(id, title);
    }

    public GUICanvas getCanvas() {
        return canvas;
    }

    public FrameBuilder getFrameBuilder() {
        return frameBuilder;
    }

    public long getId() {
        return id;
    }

    public VAO getVAO(){
        return vao;
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
                window.resolution.y = i1;
                window.getFrameBuilder().getRenderProperties().update();
                event.execute(window, new Integer[]{i, i1});
            }
        });
    }

    public void setMoveEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowPosCallback(id, new GLFWWindowPosCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                event.execute(window, new Integer[]{i, i1});
            }
        });
    }

    public void setIconifyEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowIconifyCallback(id, new GLFWWindowIconifyCallback() {
            @Override
            public void invoke(long l, boolean b) {
                event.execute(window, new Boolean[]{b});
            }
        });
    }

    public void setMaximizeEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowMaximizeCallback(id, new GLFWWindowMaximizeCallback() {
            @Override
            public void invoke(long l, boolean b) {
                event.execute(window, new Boolean[]{b});
            }
        });
    }

    public void setFocusEvent(WindowEvent event) {
        Window window = this;
        glfwSetWindowFocusCallback(id, new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long l, boolean b) {
                event.execute(window, new Boolean[]{b});
            }
        });
    }

    public void setResolutionLimits(Vector2i minResolution, Vector2i maxResolution) {
        glfwSetWindowSizeLimits(id, minResolution.x, minResolution.y, maxResolution.x, maxResolution.y);
    }

    public void setResolutionLimits(Vector2i minResolution) {
        glfwSetWindowSizeLimits(id, minResolution.x, minResolution.y, GLFW_DONT_CARE, GLFW_DONT_CARE);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Vector2i getPosition() {
        IntBuffer xBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer yBuffer = BufferUtils.createIntBuffer(1);

        glfwGetWindowPos(id, xBuffer, yBuffer);

        return new Vector2i(xBuffer.get(0), yBuffer.get(0));
    }

    public void setPosition(Vector2i position) {
        glfwSetWindowPos(id, position.x, position.y);
    }

    public void iconify() {
        glfwIconifyWindow(id);
    }

    public void restore() {
        glfwRestoreWindow(id);
    }

    public void show() {
        glfwShowWindow(id);
    }

    public void hide() {
        glfwHideWindow(id);
    }

    public void open(boolean fullscreen, boolean allowResize, boolean vSync, Vector2i resolution, String[] pathsWindowIcon) {
        if (!isInited) {
            this.fullscreen = fullscreen;

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, allowResize ? GLFW_TRUE : GLFW_FALSE);

            GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (fullscreen) {
                this.resolution = new Vector2i(mode.width(), mode.height());
                resolution = this.resolution;
            } else {
                this.resolution = resolution;
            }

            id = glfwCreateWindow(resolution.x, resolution.y, "LokEngine application", fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

            if (id == NULL) {
                Logger.error("Failed to create the window", "LokEngine_Window");
                return;
            }

            if (!fullscreen)
                glfwSetWindowPos(id, mode.width() / 2 - this.resolution.x / 2, mode.height() / 2 - this.resolution.y / 2);

            glfwMakeContextCurrent(id);
            GL.createCapabilities();
            this.setIcon(pathsWindowIcon);

            glfwSwapInterval(vSync ? 1 : 0);

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);

            vao = new VAO();

            isInited = true;
            camera = new Camera(this);
            keyboard = new Keyboard(this);
            mouse = new Mouse(this);
            canvas = new GUICanvas(new Vector2i(), resolution);
            canvas.properties.window = this;
            canvas.properties.mouseRaycastStatus = new MouseRaycastStatus(mouse);

            try {
                frameBuilder = new FrameBuilder(this);
                frameBuilder.getRenderProperties().init();
            } catch (Exception e) {
                Logger.error("Fail init Frame Builder!", "LokEngine_Window");
                Logger.printException(e);
                return;
            }
            setResizeEvent((window, args) -> {
            });

            glfwShowWindow(id);
        }
    }

    public void open(boolean fullscreen, boolean allowResize, boolean vSync, Vector2i resolution) {
        open(fullscreen, allowResize, vSync, resolution,
                new String[]{
                        "#/resources/textures/EngineIcon16.png",
                        "#/resources/textures/EngineIcon32.png",
                        "#/resources/textures/EngineIcon128.png"});
    }

    public void open(boolean allowResize, Vector2i resolution, boolean fullscreen) {
        open(fullscreen, allowResize, true, resolution);
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

                vao.bind();
                glfwSwapBuffers(id);
                vao.unbind();

            } catch (Exception e) {
                Logger.error("Fail build frame!", "LokEngine_Window");
                Logger.printException(e);
            }
            canvas.properties.mouseRaycastStatus.lastFramePressed = mouse.getPressedStatus();

            glfwPollEvents();
            mouse.update();

            canvas.properties.mouseRaycastStatus.touched = false;
            glViewport(0, 0, resolution.x, resolution.y);
        }
    }

    public void setIcon(String[] paths) {
        GLFWImage.Buffer iconGB = GLFWImage.malloc(paths.length);
        for (String path : paths) {
            try {
                Object[] image = TextureLoader.loadTextureInBuffer(path);
                GLFWImage GLFWimage = GLFWImage.malloc().set(
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

    public void setCursor(String path) {
        GLFWImage GLFWimage;
        try {
            Object[] image = TextureLoader.loadTextureInBuffer(path);
            GLFWimage = GLFWImage.create().set(((BufferedImage) image[1]).getWidth(), ((BufferedImage) image[1]).getHeight(), (ByteBuffer) image[0]);
        } catch (Exception e) {
            Logger.warning("Fail load icon", "LokEngine_Window");
            Logger.printException(e);
            return;
        }

        long cursor = GLFW.glfwCreateCursor(GLFWimage, 0, 0);
        GLFW.glfwSetCursor(id, cursor);
    }
}
