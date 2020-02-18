package ru.lokincompany.lokengine.render.frame.frameparts;

import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.Sprite;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class ParticleSystemFramePart extends FramePart {
    public Shader shader;
    Sprite sourceSprite;

    VBO positionsVBO;
    VBO sizesVBO;
    int count;

    public ParticleSystemFramePart(Sprite sourceSprite, Shader shader) {
        super(FramePartType.Scene);

        this.sourceSprite = sourceSprite;
        this.shader = shader;

        positionsVBO = new VBO();
        sizesVBO = new VBO();
    }

    public void setSourceSprite(Sprite sprite) {
        this.sourceSprite = sprite;
    }

    public void update(ArrayList<Float> positions, ArrayList<Float> sizes) {
        positionsVBO.createNew();
        sizesVBO.createNew();

        if (sizes.size() > 0) {
            positionsVBO.putData(positions);
            positionsVBO.putData(sizes);
        }
    }

    @Override
    public void init(RenderProperties renderProperties) {

    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        if (count > 0) {
            if (shader == null)
                shader = renderProperties.getParticlesShader();

            if (renderProperties.getActiveShader() == null || !renderProperties.getActiveShader().equals(shader)) {
                renderProperties.useShader(shader);
            }

            VBO uvBuffer = sourceSprite.uvVBO != null ? sourceSprite.uvVBO : renderProperties.getUVVBO();
            int textureBuffer = sourceSprite.texture.getBuffer() != -1 ? sourceSprite.texture.getBuffer() : renderProperties.getUnknownTexture().getBuffer();

            glEnableVertexAttribArray(0);
            sourceSprite.vertexVBO.bind();
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

            glEnableVertexAttribArray(2);
            sizesVBO.bind();
            glVertexAttribPointer(
                    2,
                    1,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(2, 1);

            glEnableVertexAttribArray(3);
            positionsVBO.bind();
            glVertexAttribPointer(
                    3,
                    2,
                    GL_FLOAT,
                    false,
                    0,
                    0);
            glVertexAttribDivisor(3, 1);

            glBindTexture(GL_TEXTURE_2D, textureBuffer);

            glDrawArraysInstanced(sourceSprite.renderMode, 0, sourceSprite.vertexVBO.getSize(), count);

            glBindTexture(GL_TEXTURE_2D, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
            glDisableVertexAttribArray(3);
        }
    }
}
