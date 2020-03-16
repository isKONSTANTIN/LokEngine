package ru.lokincompany.lokengine.render.model;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AITexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public class Model implements Saveable {

    public AIScene scene;
    public List<Mesh> meshes;
    public List<Material> materials;

    public Model(String path) {
        AIScene scene;

        scene = aiImportFile(path, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate);

        if (scene == null) {
            throw new IllegalStateException(aiGetErrorString());
        }

        this.scene = scene;

        int meshCount = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();
        meshes = new ArrayList<>();
        for (int i = 0; i < meshCount; ++i) {
            meshes.add(new Mesh(AIMesh.create(meshesBuffer.get(i))));
        }

        int materialCount = scene.mNumMaterials();
        PointerBuffer materialsBuffer = scene.mMaterials();
        materials = new ArrayList<>();
        for (int i = 0; i < materialCount; ++i) {
            materials.add(new Material(path, AIMaterial.create(materialsBuffer.get(i))));
        }
    }

    @Override
    public String save() {
        return null;
    }

    @Override
    public Saveable load(String savedString) {
        return null;
    }

    public void free() {
        aiReleaseImport(scene);
        scene = null;
        meshes = null;
        materials = null;
    }
}