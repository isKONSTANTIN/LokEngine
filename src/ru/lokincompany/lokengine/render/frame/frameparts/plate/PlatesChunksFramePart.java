package ru.lokincompany.lokengine.render.frame.frameparts.plate;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.VAO;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateChunk;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateScene;

import java.util.Map;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class PlatesChunksFramePart extends FramePart {
    Vector<PlateChunk> chunks;
    PlateScene scene;
    Shader shader;
    VAO vao;
    VBO vertexVBO;
    public int blockSize;

    public PlatesChunksFramePart(Vector<PlateChunk> chunks, PlateScene scene, int blockSize) {
        super(FramePartType.Scene);
        this.chunks = chunks;
        this.scene = scene;
        this.blockSize = blockSize;

        try {
            this.shader = new Shader("#/resources/shaders/PlatesVertShader.glsl", "#/resources/shaders/PlatesFragShader.glsl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        renderProperties.useShader(shader);
        renderProperties.getBuilderWindow().getCamera().updateView(shader);
        renderProperties.getBuilderWindow().getCamera().setFieldOfView(renderProperties.getBuilderWindow().getCamera().fieldOfView, shader);

        vao.bind();

        for (PlateChunk chunk : chunks){
            chunk.updateRender(scene, blockSize);

            shader.setUniformData("ChuckPosition", new Vector2f(chunk.xPosition * blockSize * 0.005f * 16, chunk.yPosition * blockSize * 0.005f * 16));

            for(Map.Entry<Integer, VBO> entry : chunk.renderData.positions.entrySet()) {
                int key = entry.getKey();
                VBO value = entry.getValue();
                int count = chunk.renderData.counts.get(key);

                value.bind();
                glVertexAttribPointer(
                        2,
                        2,
                        GL_FLOAT,
                        false,
                        0,
                        0);
                glVertexAttribDivisor(2, 1);

                glBindTexture(GL_TEXTURE_2D, chunk.renderData.textures.get(key).buffer);

                glDrawArraysInstanced(GL_QUADS, 0, vertexVBO.getSize(), count);
            }
        }

        vao.unbind();
    }

    @Override
    public void init(RenderProperties renderProperties) {
        renderProperties.useShader(shader);
        shader.setUniformData("ObjectModelMatrix", MatrixTools.createModelMatrix(0, new Vector3f(0, 0, 0)));

        vao = new VAO();
        vertexVBO = new VBO(new float[]
                {
                        -blockSize * 0.005f / 2f, -blockSize * 0.005f / 2f,
                        -blockSize * 0.005f / 2f, blockSize * 0.005f / 2f,
                        blockSize * 0.005f / 2f, blockSize * 0.005f / 2f,
                        blockSize * 0.005f / 2f, -blockSize * 0.005f / 2f
                }
        );

        vao.bind();

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        vertexVBO.bind();
        glVertexAttribPointer(
                0,
                2,
                GL_FLOAT,
                false,
                0,
                0);
        glVertexAttribDivisor(0, 0);

        renderProperties.getBuilderWindow().getFrameBuilder().getRenderProperties().getUVVBO().bind();
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
}
