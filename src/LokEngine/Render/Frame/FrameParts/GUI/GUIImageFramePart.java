package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Utilities.Vector2i;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class GUIImageFramePart extends FramePart {

    public Texture texture;
    public Vector2i position;
    public Vector2i size;

    public GUIImageFramePart(Vector2i position, Vector2i size, String path) {
        super(FramePartType.GUI);
        try {
            this.texture = TextureLoader.loadTexture(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.position = position;
        if (size.x <= 0 || size.y <= 0){
            size = new Vector2i(texture.sizeX,texture.sizeY);
        }
        this.size = size;
    }

    @Override
    public void partRender(){
        glBindTexture(GL_TEXTURE_2D,texture.buffer);
        glBegin(GL_POLYGON);
        glColor4d(1,1,1,1);

        glTexCoord2f(0,0);
        glVertex3f(position.x,position.y,0);
        glTexCoord2f(1,0);
        glVertex3f(size.x + position.x,position.y,0);
        glTexCoord2f(1,1);
        glVertex3f(size.x + position.x,size.y + position.y,0);
        glTexCoord2f(0,1);
        glVertex3f(position.x,size.y + position.y,0);

        glEnd();
        glBindTexture(GL_TEXTURE_2D,0);
    }
}
