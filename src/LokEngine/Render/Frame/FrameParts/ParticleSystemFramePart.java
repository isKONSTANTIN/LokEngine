package LokEngine.Render.Frame.FrameParts;

import LokEngine.Components.AdditionalObjects.ParticleSystem.Particle;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Loaders.BufferLoader;
import LokEngine.Loaders.ShaderLoader;
import LokEngine.Render.Camera;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.Render.Texture;
import LokEngine.Tools.DefaultFields;
import LokEngine.Tools.Logger;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class ParticleSystemFramePart extends FramePart {

    public Shader shader;
    Sprite sourceSprite;

    int positionsBuffer;
    int sizesBuffer;
    int count;

    public ParticleSystemFramePart(Sprite sourceSprite, Shader shader) {
        super(FramePartType.Scene);
        this.sourceSprite = sourceSprite;

        this.shader = shader;
    }

    public void setSourceSprite(Sprite sprite){
        this.sourceSprite = sprite;
    }

    public void update(ArrayList<Float> positions, ArrayList<Float> sizes){
        count = sizes.size();

        glDeleteBuffers(positionsBuffer);
        glDeleteBuffers(sizesBuffer);

        if (count > 0){
            positionsBuffer = BufferLoader.load(positions);
            sizesBuffer = BufferLoader.load(sizes);
        }
    }

    @Override
    public void partRender() {
        if (count > 0) {
            if (!Shader.currentShader.equals(shader)){
                Shader.use(shader);
            }
            RuntimeFields.getFrameBuilder().window.getCamera().updateView();

            glEnableVertexAttribArray(0);
            glBindBuffer(GL_ARRAY_BUFFER, sourceSprite.vertexBuffer);
            glVertexAttribPointer(
                    0,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(0, 0);

            glEnableVertexAttribArray(1);
            glBindBuffer(GL_ARRAY_BUFFER, sourceSprite.uvBuffer);
            glVertexAttribPointer(
                    1,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(1, 0);

            glEnableVertexAttribArray(2);
            glBindBuffer(GL_ARRAY_BUFFER, sizesBuffer);
            glVertexAttribPointer(
                    2,
                    1,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(2, 1);

            glEnableVertexAttribArray(3);
            glBindBuffer(GL_ARRAY_BUFFER, positionsBuffer);
            glVertexAttribPointer(
                    3,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(3, 1);

            glBindTexture(GL_TEXTURE_2D, sourceSprite.texture.buffer);

            glDrawArraysInstanced(GL_QUADS, 0, 8, count);

            glBindTexture(GL_TEXTURE_2D, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
            glDisableVertexAttribArray(3);
        }
    }
}
