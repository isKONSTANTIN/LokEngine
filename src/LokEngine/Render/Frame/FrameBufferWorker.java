package LokEngine.Render.Frame;

import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_BINDING;

public class FrameBufferWorker {
    private int frameBuffer;
    private int texture;
    private int lastBuffer;

    public FrameBufferWorker(Vector2i resolution) {
        initialiseFrameBuffer(resolution.x,resolution.y);
    }

    public void cleanUp() {
        GL30.glDeleteFramebuffers(frameBuffer);
        GL11.glDeleteTextures(texture);
    }

    public void bindFrameBuffer() {
        bindFrameBuffer(frameBuffer);
    }

    public void unbindCurrentFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, lastBuffer);
    }

    public int getTexture() {//get the resulting texture
        return texture;
    }

    private void initialiseFrameBuffer(int x, int y) {
        frameBuffer = GL30.glGenFramebuffers();
        lastBuffer = glGetInteger(GL_FRAMEBUFFER_BINDING);

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

        texture = createTextureAttachment(x,y);
        unbindCurrentFrameBuffer();
    }

    private void bindFrameBuffer(int frameBuffer){
        lastBuffer = glGetInteger(GL_FRAMEBUFFER_BINDING);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
    }

    private int createTextureAttachment(int width, int height) {
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, texture, 0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return texture;
    }

}
