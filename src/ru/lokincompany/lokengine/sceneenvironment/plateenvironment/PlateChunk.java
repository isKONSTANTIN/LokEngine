package ru.lokincompany.lokengine.sceneenvironment.plateenvironment;

import ru.lokincompany.lokengine.render.frame.frameparts.plate.PlateChunkRenderData;
import ru.lokincompany.lokengine.tests.plate.PlateSceneTest;

public abstract class PlateChunk {
    protected int[][] plates = new int[16][16];
    public int xPosition;
    public int yPosition;
    public boolean platesChanged;
    public boolean generated;
    public PlateChunkRenderData renderData = new PlateChunkRenderData();

    public abstract boolean generate(int chunkID, PlateScene scene);

    public void update(PlateScene scene){
        if (!generated)
            return;

        int[] xsPlates = new int[scene.randomUpdateIterations];
        int[] ysPlates = new int[scene.randomUpdateIterations];

        synchronized (scene.random){
            for (int i = 0; i < scene.randomUpdateIterations; i++){
                xsPlates[i] = scene.random.nextInt(16);
                ysPlates[i] =  scene.random.nextInt(16);
            }
        }

        for (int i = 0; i < scene.randomUpdateIterations; i++){
            scene.getPlate(plates[xsPlates[i]][ysPlates[i]]).randomTickHandle(16 * xPosition + xsPlates[i], 16 * yPosition + ysPlates[i]);
        }
    }

    public void updateRender(PlateScene scene, int blockSize){
        if (platesChanged)
            renderData.update(this, scene, blockSize);
        platesChanged = false;
    }

    public int[][] getPlates() {
        return plates;
    }

    public void setPlate(int id, int x, int y){
        if (x < 0 || x > 15) return;
        if (y < 0 || y > 15) return;

        if (plates[x][y] != id)
            platesChanged = true;

        plates[x][y] = id;
    }

    public int getPlate(int x, int y){
        if (x < 0 || x > 15) return -1;
        if (y < 0 || y > 15) return -1;

        return plates[x][y];
    }
}
