package LokEngine.Render.Frame.FrameParts;

import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.Tools.MatrixCreator;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class SpriteFramePart extends FramePart {

    public Sprite sprite;
    public Vector4f position = new Vector4f(0, 0, 0, 0);
    public Shader shader;

    public SpriteFramePart(Sprite sprite, Shader shader) {
        super(FramePartType.Scene);
        this.sprite = sprite;
        this.shader = shader;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        if (shader == null)
            shader = builderProperties.getObjectShader();

        if (!shader.equals(builderProperties.getActiveShader())) {
            builderProperties.useShader(shader);
        }

        int uvBuffer = sprite.uvBuffer != -1 ? sprite.uvBuffer : builderProperties.getUVBuffer();
        int textureBuffer = sprite.texture.buffer != -1 ? sprite.texture.buffer : builderProperties.getUnknownTexture().buffer;

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, sprite.vertexBuffer);
        glVertexAttribPointer(
                0,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(0, 0);

        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, uvBuffer);
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);
        glUniform1f(glGetUniformLocation(shader.program, "ObjectSize"), (float) sprite.size * 2);

        MatrixCreator.PutMatrixInShader(shader, "ObjectModelMatrix", MatrixCreator.CreateModelMatrix(position.w, new Vector3f(position.x, position.y, position.z)));

        glBindTexture(GL_TEXTURE_2D, textureBuffer);

        glDrawArrays(GL_QUADS, 0, 8);

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

}
