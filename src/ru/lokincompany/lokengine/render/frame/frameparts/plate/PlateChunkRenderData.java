package ru.lokincompany.lokengine.render.frame.frameparts.plate;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateChunk;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateScene;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlateChunkRenderData {
    HashMap<Integer, VBO> positions = new HashMap<>();
    HashMap<Integer, Texture> textures = new HashMap<>();
    HashMap<Integer, Integer> counts = new HashMap<>();

    public void update(PlateChunk chunk, PlateScene scene, int blockSize){
        clearPositions();
        textures.clear();

        HashMap<Integer, ArrayList<Float>> positionsArrays = new HashMap<>();

        for (int y = 0; y < 16; y++){
            for (int x = 0; x < 16; x++){
                int plate = chunk.getPlate(x, y);
                if (plate == 0) continue;

                Texture texture = scene.getPlate(plate).getTexture();
                if (!positions.containsKey(plate)){
                    positions.put(plate, new VBO());
                    counts.put(plate, 0);
                    textures.put(plate, texture);

                    positionsArrays.put(plate, new ArrayList<>());
                }

                positionsArrays.get(plate).add(blockSize * 0.005f * x);
                positionsArrays.get(plate).add(blockSize * 0.005f * y);
                counts.put(plate, counts.get(plate)+1);
            }
        }

        for(Map.Entry<Integer, VBO> entry : positions.entrySet()) {
            int key = entry.getKey();
            VBO value = entry.getValue();

            value.putData(positionsArrays.get(key));
        }
    }

    private void clearPositions(){
        for(Map.Entry<Integer, VBO> entry : positions.entrySet()) {
            VBO value = entry.getValue();
            value.unload();
        }
        positions.clear();
    }
}
