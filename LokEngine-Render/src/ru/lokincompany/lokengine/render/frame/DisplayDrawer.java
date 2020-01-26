package ru.lokincompany.lokengine.render.frame;

import ru.lokincompany.lokengine.render.window.Window;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class DisplayDrawer {

    public static void bindTexture(String uniformName, int textureBuffer, int index, RenderProperties renderProperties) {
        glActiveTexture(GL_TEXTURE0 + index);
        glBindTexture(GL_TEXTURE_2D, textureBuffer);

        if (renderProperties.getActiveShader() != null) {
            renderProperties.getActiveShader().setUniformData(uniformName, index);
        }
    }

    public static void renderScreen(int frameTextureBuffer, Window window) {
        bindTexture("frame", frameTextureBuffer, 0, window.getFrameBuilder().getRenderProperties());
        window.getVAO().bind();

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        window.getFrameBuilder().getRenderProperties().getUVVBO().bind();
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);

        window.getFrameBuilder().getRenderProperties().getVertexScreenBuffer().bind();
        glVertexAttribPointer(
                0,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(0, 0);

        glDrawArrays(GL_QUADS, 0, 8);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        bindTexture("frame", 0, 0, window.getFrameBuilder().getRenderProperties());

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        window.getVAO().unbind();
    }

}
