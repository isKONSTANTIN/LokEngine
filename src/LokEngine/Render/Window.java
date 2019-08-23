package LokEngine.Render;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.util.glu.GLU.*;

public class Window {

    private Camera camera;
    private Vector2i resolution;
    private boolean fullscreen = false;
    private boolean isOpened = false;

    public boolean isFullscreen(){return fullscreen;}
    public boolean isOpened(){
        return isOpened;
    }
    public Vector2i getResolution(){
        return resolution;
    }
    public Camera getCamera(){
        return camera;
    }
    public String getTitle(){
        return Display.getTitle();
    }
    public void setTitle(String title){
        Display.setTitle(title);
    }

    public void open(boolean fullscreen, boolean vSync, Vector2i resolution) throws LWJGLException {
        if (!isOpened){
            this.fullscreen = fullscreen;

            if (fullscreen){
                Display.setFullscreen(true);
                this.resolution = new Vector2i(Display.getWidth(),Display.getHeight());
            }else {
                this.resolution = resolution;
                Display.setDisplayMode(new DisplayMode(resolution.x, resolution.y));
            }

            Display.create();
            Display.setVSyncEnabled(vSync);

            isOpened = true;
            camera = new Camera();

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            GL30.glBindVertexArray(GL30.glGenVertexArrays());
        }
    }

    public void close(){
        Display.destroy();
        isOpened = false;
    }

    public void update(){
        if (isOpened)
            Display.update();
    }

    public void setDrawMode(DrawMode dm){
        if (dm == DrawMode.Display || dm == DrawMode.RawGUI){
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            gluOrtho2D(0.0f, resolution.x, resolution.y, 0.0f);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            glDisable(GL_DEPTH_TEST);
            glDisable(GL_MULTISAMPLE);
            glEnable(GL_TEXTURE_2D);

            if (dm == DrawMode.Display){
                Shader.use(DefaultFields.displayShader);
            }else{
                Shader.unUse();
            }

        }else if (dm == DrawMode.Scene){
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
