package ru.lokinCompany.lokEngine.Render.Frame;

import ru.lokinCompany.lokEngine.Render.Enums.DrawMode;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.ARBFramebufferObject.GL_DEPTH_STENCIL;
import static org.lwjgl.opengl.ARBFramebufferObject.GL_UNSIGNED_INT_24_8;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_BINDING;

public class FrameBufferWorker {
    private int frameBuffer;
    private int texture;
    private int lastBuffer;
    private Vector2i lastView = new Vector2i();
    private Vector2i sourceResolution;
    private Vector2i bufferResolution;
    private int depth;
    private DrawMode activeDrawMode;
    private BuilderProperties activeProperties;

    public FrameBufferWorker(Vector2i resolution) {
        sourceResolution = resolution;
        bufferResolution = new Vector2i(resolution.x, resolution.y);

        initialiseFrameBuffer(bufferResolution.x, bufferResolution.y);
    }

    public void cleanUp() {
        GL30.glDeleteFramebuffers(frameBuffer);
        GL11.glDeleteTextures(texture);
    }

    public Vector2i getResolution(){
        return bufferResolution;
    }

    public void setResolution(Vector2i resolution){
        sourceResolution = resolution;
        bufferResolution.x = resolution.x;
        bufferResolution.y = resolution.y;

        cleanUp();
        initialiseFrameBuffer(bufferResolution.x, bufferResolution.y);
    }

    public void bindFrameBuffer(DrawMode drawMode, BuilderProperties properties) {
        if (!sourceResolution.equals(bufferResolution)){
            setResolution(sourceResolution);
        }
        bindFrameBuffer(frameBuffer);
        activeDrawMode = drawMode;
        activeProperties = properties;
        properties.setDrawMode(drawMode, bufferResolution);
    }

    public void unbindCurrentFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, lastBuffer);
        activeProperties.setDrawMode(activeDrawMode, lastView);
    }

    public int getTexture() {
        return texture;
    }

    public int getDepth() {
        return depth;
    }

    private void initialiseFrameBuffer(int x, int y) {
        frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

        texture = createTextureAttachment(x, y);
        depth = createDepthAttachment(x, y);

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, lastBuffer);
    }

    private void bindFrameBuffer(int frameBuffer) {
        lastBuffer = glGetInteger(GL_FRAMEBUFFER_BINDING);
        int[] view = new int[4];
        glGetIntegerv(GL_VIEWPORT, view);
        lastView.x = view[2];
        lastView.y = view[3];
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
    }

    private int createDepthAttachment(int width, int height) {
        int depth = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depth);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH24_STENCIL8, width, height, 0, GL_DEPTH_STENCIL, GL_UNSIGNED_INT_24_8, (ByteBuffer) null);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, depth, 0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return depth;
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
