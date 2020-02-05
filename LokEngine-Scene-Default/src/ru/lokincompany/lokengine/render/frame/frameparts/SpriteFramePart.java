package ru.lokincompany.lokengine.render.frame.frameparts;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.Sprite;
import ru.lokincompany.lokengine.render.VAO;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.tools.color.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class SpriteFramePart extends FramePart {

    public Sprite sprite;
    public Vector4f position = new Vector4f(0, 0, 0, 0);
    public Shader shader;
    public Color color;
    public VAO vao;

    private VBO spriteVBO;

    public SpriteFramePart(Sprite sprite, Shader shader, Color color) {
        super(FramePartType.Scene);
        this.sprite = sprite;
        this.spriteVBO = sprite.vertexVBO;
        this.shader = shader;
        this.color = color;
        this.vao = new VAO();
    }

    @Override
    public void init(RenderProperties renderProperties) {
        vao.bind();

        VBO uvBuffer = sprite.uvVBO != null ? sprite.uvVBO : renderProperties.getUVVBO();

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
    public void partRender(RenderProperties renderProperties) {
        if (shader == null)
            shader = renderProperties.getObjectShader();

        if (renderProperties.getActiveShader() == null || !shader.equals(renderProperties.getActiveShader())) {
            renderProperties.useShader(shader);
        }

        if (!spriteVBO.equals(sprite.vertexVBO)){
            init(renderProperties);
            spriteVBO = sprite.vertexVBO;
        }

        shader.setUniformData("ObjectSize", (float) sprite.size * 2);
        shader.setUniformData("ObjectColor", new Vector4f(color.red, color.green, color.blue, color.alpha));
        shader.setUniformData("ObjectModelMatrix", MatrixTools.createModelMatrix(position.w, new Vector3f(position.x, position.y, position.z)));

        int textureBuffer = sprite.texture.getBuffer() != -1 ? sprite.texture.getBuffer() : renderProperties.getUnknownTexture().getBuffer();

        glBindTexture(GL_TEXTURE_2D, textureBuffer);

        vao.bind();

        glDrawArrays(sprite.renderMode, 0, sprite.vertexVBO.getSize());

        vao.unbind();

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
