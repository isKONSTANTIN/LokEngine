package LokEngine.Render.Frame.FrameParts;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Render.Shader;
import LokEngine.Components.AdditionalObjects.Sprite;
import LokEngine.Tools.MatrixCreator;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class SpriteFramePart extends FramePart {

    private Sprite sprite;
    public Vector4f position = new Vector4f(0,0,0,0);

    public SpriteFramePart(Sprite sprite) {
        super(FramePartType.Scene);
        this.sprite = sprite;
    }

    @Override
    public void partRender() {
        if (!sprite.shader.equals(Shader.currentShader)) {
            Shader.use(sprite.shader);
        }
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
        glBindBuffer(GL_ARRAY_BUFFER, sprite.uvBuffer);
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);
        glUniform1f(glGetUniformLocation(sprite.shader.program, "ObjectSize"), (float) sprite.size * 2);

        MatrixCreator.PutMatrixInShader(sprite.shader,"ObjectModelMatrix",MatrixCreator.CreateModelMatrix(position.w,new Vector3f(position.x,position.y,position.z)));

        glBindTexture(GL_TEXTURE_2D, sprite.texture.buffer);

        glDrawArrays(GL_QUADS, 0, 8);

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

}
