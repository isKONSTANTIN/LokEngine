package ru.lokincompany.lokengine.render.frame.frameparts;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import ru.lokincompany.lokengine.loaders.MatrixLoader;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.VAO;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.BuilderProperties;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.Sprite;
import ru.lokincompany.lokengine.tools.color.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class SpriteFramePart extends FramePart {

    public Sprite sprite;
    public Vector4f position = new Vector4f(0, 0, 0, 0);
    public Shader shader;
    public Color color;
    public VAO vao;

    public SpriteFramePart(Sprite sprite, Shader shader, Color color) {
        super(FramePartType.Scene);
        this.sprite = sprite;
        this.shader = shader;
        this.color = color;
        this.vao = new VAO();
    }

    @Override
    public void init(BuilderProperties builderProperties) {
        vao.bind();

        VBO uvBuffer = sprite.uvVBO != null ? sprite.uvVBO : builderProperties.getUVVBO();

        glEnableVertexAttribArray(0);
        sprite.vertexVBO.bind();
        glVertexAttribPointer(
                0,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(0, 0);

        glEnableVertexAttribArray(1);
        uvBuffer.bind();
        glVertexAttribPointer(
                1,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(1, 0);

        vao.unbind();
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {
        if (shader == null)
            shader = builderProperties.getObjectShader();

        if (builderProperties.getActiveShader() == null || !shader.equals(builderProperties.getActiveShader())) {
            builderProperties.useShader(shader);
        }

        shader.setUniformData("ObjectSize", (float) sprite.size * 2);
        shader.setUniformData("ObjectColor", new Vector4f(color.red, color.green, color.blue, color.alpha));
        shader.setUniformData("ObjectModelMatrix", MatrixLoader.createModelMatrix(position.w, new Vector3f(position.x, position.y, position.z)));

        int textureBuffer = sprite.texture.buffer != -1 ? sprite.texture.buffer : builderProperties.getUnknownTexture().buffer;

        glBindTexture(GL_TEXTURE_2D, textureBuffer);

        vao.bind();

        glDrawArrays(GL_QUADS, 0, sprite.vertexVBO.getSize());

        vao.unbind();

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
