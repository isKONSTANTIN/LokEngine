package ru.lokinCompany.lokEngine.Render.Frame;

import ru.lokinCompany.lokEngine.Render.Window.Window;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class DisplayDrawer {

    public static void bindTexture(String uniformName, int textureBuffer, int index, BuilderProperties builderProperties) {
        glActiveTexture(GL_TEXTURE0 + index);
        glBindTexture(GL_TEXTURE_2D, textureBuffer);

        if (builderProperties.getActiveShader() != null){
            builderProperties.getActiveShader().setUniformData(uniformName, index);
        }
    }

    public static void renderScreen(int frameTextureBuffer, Window window) {
        bindTexture("frame",frameTextureBuffer, 0,window.getFrameBuilder().getBuilderProperties());

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, window.getFrameBuilder().getBuilderProperties().getUVBuffer());
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);

        glBindBuffer(GL_ARRAY_BUFFER, window.getFrameBuilder().getBuilderProperties().getVertexScreenBuffer());
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
        bindTexture("frame",0, 0,window.getFrameBuilder().getBuilderProperties());

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

}
