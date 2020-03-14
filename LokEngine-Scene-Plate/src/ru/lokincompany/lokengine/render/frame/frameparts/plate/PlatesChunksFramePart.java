package ru.lokincompany.lokengine.render.frame.frameparts.plate;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.VAO;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.camera.Camera;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateChunk;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateScene;
import ru.lokincompany.lokengine.tools.MatrixTools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlatesChunksFramePart extends FramePart {
    public int blockSize;
    ConcurrentHashMap<String, PlateChunk> chunks;
    PlateScene scene;
    Shader shader;
    VAO vao;
    VBO vertexVBO;

    public PlatesChunksFramePart(ConcurrentHashMap<String, PlateChunk> chunks, PlateScene scene, int blockSize) {
        super(FramePartType.Scene);
        this.chunks = chunks;
        this.scene = scene;
        this.blockSize = blockSize;

        try {
            this.shader = new Shader("#/resources/shaders/plates/PlatesVertShader.glsl", "#/resources/shaders/plates/PlatesFragShader.glsl") {
                @Override
                public void update(Camera activeCamera) {
                    RenderProperties renderProperties = activeCamera.getWindow().getFrameBuilder().getRenderProperties();
                    renderProperties.useShader(this);

                    setView(activeCamera);
                    setProjection(activeCamera);
                }
            };
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        shader.update(renderProperties.getBuilderWindow().getActiveCamera());

        vao.bind();

        for (Map.Entry<String, PlateChunk> chunkEntry : chunks.entrySet()) {
            PlateChunk chunk = chunkEntry.getValue();

            chunk.updateRender(blockSize);

            shader.setUniformData("ChuckPosition", new Vector2f(chunk.xPosition * blockSize * 0.005f * 16, chunk.yPosition * blockSize * 0.005f * 16));

            for (Map.Entry<Integer, VBO> PositionEntry : chunk.renderData.positions.entrySet()) {
                int key = PositionEntry.getKey();
                VBO value = PositionEntry.getValue();
                int count = chunk.renderData.counts.get(key);

                value.bind();
                GL20.glVertexAttribPointer(
                        2,
                        2,
                        GL11.GL_FLOAT,
                        false,
                        0,
                        0);
                GL33.glVertexAttribDivisor(2, 1);

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, chunk.renderData.textures.get(key).getBuffer());

                GL31.glDrawArraysInstanced(GL11.GL_QUADS, 0, vertexVBO.getSize(), count);
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

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        vertexVBO.bind();
        GL20.glVertexAttribPointer(
                0,
                2,
                GL11.GL_FLOAT,
                false,
                0,
                0);
        GL33.glVertexAttribDivisor(0, 0);

        renderProperties.getBuilderWindow().getFrameBuilder().getRenderProperties().getUVVBO().bind();
        GL20.glVertexAttribPointer(
                1,
                2,
                GL11.GL_FLOAT,
                false,
                0,
                0);
        GL33.glVertexAttribDivisor(1, 0);

        vao.unbind();

    }
}
