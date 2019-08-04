package LokEngine.Render.Frame;

import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.ByteBuffer;

public class FrameBufferWorker {
    private int frameBuffer;
    private int texture;
    private int depthTexture;

    public FrameBufferWorker(Vector2i resolution) {
        initialiseFrameBuffer(resolution.x,resolution.y);
    }

    public void cleanUp() {//call when closing the game
        GL30.glDeleteFramebuffers(frameBuffer);
        GL11.glDeleteTextures(texture);
        GL11.glDeleteTextures(depthTexture);
    }

    public void bindFrameBuffer() {//call before rendering to this FBO
        bindFrameBuffer(frameBuffer);
    }

    public void unbindCurrentFrameBuffer() {//call to switch to default frame buffer
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public int getTexture() {//get the resulting texture
        return texture;
    }

    public int getDepthTexture(){//get the resulting depth texture
        return depthTexture;
    }

    private void initialiseFrameBuffer(int x, int y) {
        frameBuffer = createFrameBuffer();
        texture = createTextureAttachment(x,y);
        depthTexture = createDepthTextureAttachment(x,y);
        unbindCurrentFrameBuffer();
    }

    private void bindFrameBuffer(int frameBuffer){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
    }

    private int createFrameBuffer() {
        int frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

        return frameBuffer;
    }

    private int createTextureAttachment( int width, int height) {
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height,
                0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
                texture, 0);
        return texture;
    }

    private int createDepthTextureAttachment(int width, int height){
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, width, height,
                0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
                texture, 0);
        return texture;
    }

    private int createDepthBufferAttachment(int width, int height) {
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width,
                height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
                GL30.GL_RENDERBUFFER, depthBuffer);
        return depthBuffer;
    }

}
