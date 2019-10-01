package LokEngine.Render.Frame;

import LokEngine.Render.Enums.DrawMode;
import LokEngine.Render.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class DisplayDrawer {

    public static void bindTexture(String uniformName, int textureBuffer, int index, BuilderProperties builderProperties) {
        glUniform1i(glGetUniformLocation(builderProperties.getActiveShader().program, uniformName), index);

        glActiveTexture(GL_TEXTURE0 + index);
        glBindTexture(GL_TEXTURE_2D, textureBuffer);
    }

    public static int blurPostProcess(Window win, int postFrame, int originalFrame, FrameBufferWorker blurSceneFrameWorker1, FrameBufferWorker blurSceneFrameWorker2, FrameBufferWorker blurSceneFrameWorker3) {
        BuilderProperties builderProperties = win.getFrameBuilder().getBuilderProperties();
        blurSceneFrameWorker1.bindFrameBuffer();
        win.setDrawMode(DrawMode.Display);
        builderProperties.useShader(builderProperties.getPostProcessingShader());
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, builderProperties);
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUniform2f(GL20.glGetUniformLocation(builderProperties.getActiveShader().program, "direction"), 0, 1);
        DisplayDrawer.renderScreen(originalFrame, win);

        blurSceneFrameWorker1.unbindCurrentFrameBuffer();
        blurSceneFrameWorker2.bindFrameBuffer();
        win.setDrawMode(DrawMode.Display);
        builderProperties.useShader(builderProperties.getPostProcessingShader());
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, builderProperties);
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUniform2f(GL20.glGetUniformLocation(builderProperties.getActiveShader().program, "direction"), 0.866f / ((float) win.getResolution().x / (float) win.getResolution().y), 0.5f);
        DisplayDrawer.renderScreen(blurSceneFrameWorker1.getTexture(), win);

        blurSceneFrameWorker2.unbindCurrentFrameBuffer();
        blurSceneFrameWorker3.bindFrameBuffer();
        win.setDrawMode(DrawMode.Display);
        builderProperties.useShader(builderProperties.getPostProcessingShader());
        DisplayDrawer.bindTexture("postFrame", postFrame, 1, builderProperties);
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUniform2f(GL20.glGetUniformLocation(builderProperties.getActiveShader().program, "direction"), 0.866f / ((float) win.getResolution().x / (float) win.getResolution().y), -0.5f);
        DisplayDrawer.renderScreen(blurSceneFrameWorker2.getTexture(), win);

        blurSceneFrameWorker3.unbindCurrentFrameBuffer();

        return blurSceneFrameWorker3.getTexture();
    }

    public static void renderScreen(int frameTextureBuffer, Window window) {
        bindTexture("frame", frameTextureBuffer, 0, window.getFrameBuilder().getBuilderProperties());

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

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

}
