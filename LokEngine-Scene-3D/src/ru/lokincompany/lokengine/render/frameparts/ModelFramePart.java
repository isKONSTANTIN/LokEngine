package ru.lokincompany.lokengine.render.frameparts;

import org.lwjgl.opengl.GL15C;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.render.Shader;
import ru.lokincompany.lokengine.render.VAO;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.render.camera.Camera;
import ru.lokincompany.lokengine.render.enums.FramePartType;
import ru.lokincompany.lokengine.render.frame.FramePart;
import ru.lokincompany.lokengine.render.frame.RenderProperties;
import ru.lokincompany.lokengine.render.model.Material;
import ru.lokincompany.lokengine.render.model.Mesh;
import ru.lokincompany.lokengine.render.model.Model;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.MatrixTools;

import static org.lwjgl.opengl.ARBInstancedArrays.glVertexAttribDivisorARB;
import static org.lwjgl.opengl.ARBShaderObjects.glUniformMatrix4fvARB;
import static org.lwjgl.opengl.ARBShaderObjects.nglUniform3fvARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.ARBVertexProgram.glVertexAttribPointerARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class ModelFramePart extends FramePart {
    public Model model;
    Shader shader;
    VBO noneVBO = new VBO(new float[]{0});
    SceneObject object;
    public ModelFramePart(Model model, SceneObject object) {
        super(FramePartType.Scene);
        this.model = model;
        this.object = object;
    }

    @Override
    public void partRender(RenderProperties renderProperties) {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        shader.update(renderProperties.getBuilderWindow().getActiveCamera());
        shader.setUniformData("ObjectModelMatrix", MatrixTools.createModelMatrix(object.position, object.rotation));

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        for (int i = 0; i < model.meshes.size(); i++) {
            Mesh mesh = model.meshes.get(i);

            glBindBuffer(GL_ARRAY_BUFFER, mesh.vertexArrayBuffer);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, mesh.normalArrayBuffer != 0 ? mesh.normalArrayBuffer : noneVBO.getID());
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            Material material = model.materials.get(mesh.mesh.mMaterialIndex());
            nglUniform3fv(shader.getUniformLocationID("AmbientColor"), 1, material.mAmbientColor.address());
            nglUniform3fv(shader.getUniformLocationID("DiffuseColor"), 1, material.mDiffuseColor.address());
            nglUniform3fv(shader.getUniformLocationID("SpecularColor"), 1, material.mSpecularColor.address());

            shader.setUniformData("CamPosition", renderProperties.getBuilderWindow().getActiveCamera().getPosition());
            shader.setUniformData("Textured", material.texture != null);

            if (material.texture != null){
                mesh.uvTextureVBO.bind();
                glVertexAttribPointer(
                        1,
                        2,
                        GL_FLOAT,
                        false,
                        0,
                        0);

                glBindTexture(GL_TEXTURE_2D, material.texture.getBuffer());
            }else{
                noneVBO.bind();
                glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

                glBindTexture(GL_TEXTURE_2D, 0);
            }

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.elementArrayBuffer);
            glDrawElements(GL_TRIANGLES, mesh.elementCount, GL_UNSIGNED_INT, 0);
        }
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
    }

    @Override
    public void init(RenderProperties renderProperties) {
        shader = new Shader("#/resources/shaders/model/ModelVertShader.glsl", "#/resources/shaders/model/ModelFragShader.glsl") {
            @Override
            public void update(Camera activeCamera) {
                renderProperties.useShader(this);
                setView(activeCamera);
                setProjection(activeCamera);
            }
        };
    }
}
