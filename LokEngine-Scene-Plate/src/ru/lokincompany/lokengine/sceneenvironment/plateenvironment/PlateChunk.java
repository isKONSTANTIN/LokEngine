package ru.lokincompany.lokengine.sceneenvironment.plateenvironment;

import ru.lokincompany.lokengine.render.frame.frameparts.plate.PlateChunkRenderData;

public abstract class PlateChunk {
    public int xPosition;
    public int yPosition;
    public boolean platesChanged;
    public boolean generated;
    public PlateChunkRenderData renderData = new PlateChunkRenderData();
    public PlateScene scene;
    protected int[][] plates = new int[16][16];

    public abstract boolean generate(int chunkID);

    public void update() {
    }

    public void updateRender(int blockSize) {
        if (platesChanged)
            renderData.update(this, scene, blockSize);
        platesChanged = false;
    }

    public int[][] getPlates() {
        return plates;
    }

    public void setPlate(int id, int x, int y) {
        if (x < 0 || x > 15) return;
        if (y < 0 || y > 15) return;

        if (plates[x][y] != id)
            platesChanged = true;

        plates[x][y] = id;
        scene.getPlate(id).createHandle(xPosition * 16 + x, yPosition * 16 + y);
    }

    public int getPlate(int x, int y) {
        if (x < 0 || x > 15) return -1;
        if (y < 0 || y > 15) return -1;

        return plates[x][y];
    }
}
