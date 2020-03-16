package ru.lokincompany.lokengine.render.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.tools.Logger;

import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.GL15.*;

public class Mesh {

    public AIMesh mesh;
    public int vertexArrayBuffer;
    public int normalArrayBuffer;
    public int elementArrayBuffer;
    public int elementCount;
    public VBO uvTextureVBO;

    public Mesh(AIMesh mesh) {
        this.mesh = mesh;

        vertexArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexArrayBuffer);
        AIVector3D.Buffer vertices = mesh.mVertices();
        nglBufferData(GL_ARRAY_BUFFER, AIVector3D.SIZEOF * vertices.remaining(), vertices.address(), GL_STATIC_DRAW);

        AIVector3D.Buffer normals = mesh.mNormals();

        if (normals != null) {
            normalArrayBuffer = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, normalArrayBuffer);
            nglBufferData(GL_ARRAY_BUFFER, AIVector3D.SIZEOF * normals.remaining(), normals.address(), GL_STATIC_DRAW);
        }

        AIVector3D.Buffer aiVertices = mesh.mTextureCoords(0);
        if (aiVertices != null){
            ArrayList<Float> points = new ArrayList<>();

            while (aiVertices.remaining() > 0) {
                AIVector3D aiVertex = aiVertices.get();
                points.add(aiVertex.x());
                points.add(aiVertex.y());
            }
            uvTextureVBO = new VBO(points);
        }

        int faceCount = mesh.mNumFaces();
        elementCount = faceCount * 3;
        IntBuffer elementArrayBufferData = BufferUtils.createIntBuffer(elementCount);
        AIFace.Buffer facesBuffer = mesh.mFaces();
        for (int i = 0; i < faceCount; ++i) {
            AIFace face = facesBuffer.get(i);
            elementArrayBufferData.put(face.mIndices());
        }
        elementArrayBufferData.flip();

        elementArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArrayBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementArrayBufferData, GL_STATIC_DRAW);
    }
}
