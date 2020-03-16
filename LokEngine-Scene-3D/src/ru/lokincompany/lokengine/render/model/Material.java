package ru.lokincompany.lokengine.render.model;

import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.Assimp;
import ru.lokincompany.lokengine.render.Texture;

import java.io.File;
import java.nio.Buffer;
import java.nio.IntBuffer;

import static org.lwjgl.assimp.Assimp.*;

public class Material {

    public AIMaterial mMaterial;
    public AIColor4D mAmbientColor;
    public AIColor4D mDiffuseColor;
    public AIColor4D mSpecularColor;
    public Texture texture;

    public Material(String sourcePath, AIMaterial material) {
        mMaterial = material;

        mAmbientColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_AMBIENT,
                aiTextureType_NONE, 0, mAmbientColor) != 0) {
            throw new IllegalStateException(aiGetErrorString());
        }
        mDiffuseColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_DIFFUSE,
                aiTextureType_NONE, 0, mDiffuseColor) != 0) {
            throw new IllegalStateException(aiGetErrorString());
        }
        mSpecularColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_SPECULAR,
                aiTextureType_NONE, 0, mSpecularColor) != 0) {
            throw new IllegalStateException(aiGetErrorString());
        }
        AIString aiString = AIString.create();
        Assimp.aiGetMaterialTexture(material, aiTextureType_DIFFUSE, 0, aiString, (IntBuffer) null, null, null, null, null, null);

        String textPath = aiString.dataString();

        if (textPath.length() > 0){
            texture = new Texture((new File(sourcePath).getAbsoluteFile().getParent() + "/" + textPath));
        }

    }
}