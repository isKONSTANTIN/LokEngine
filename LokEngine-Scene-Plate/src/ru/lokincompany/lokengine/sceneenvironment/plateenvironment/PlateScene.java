package ru.lokincompany.lokengine.sceneenvironment.plateenvironment;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.plate.PlatesChunksFramePart;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.opensimplexnoise.DefaultStringToLongTransformer;
import ru.lokincompany.lokengine.tools.opensimplexnoise.OpenSimplexNoise2D;
import ru.lokincompany.lokengine.tools.opensimplexnoise.StringToLongTransformer;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PlateScene {
    public final Random random;
    public final OpenSimplexNoise2D noise;
    final Vector<PlateHandler> handlers;
    final ConcurrentHashMap<String, PlateChunk> plateChunksByPos;
    final ConcurrentHashMap<Integer, PlateChunk> plateChunksByID;
    PlatesChunksFramePart framePart;

    public PlateScene(long seed, int blockSize) {
        handlers = new Vector<>();
        plateChunksByPos = new ConcurrentHashMap<>();
        plateChunksByID = new ConcurrentHashMap<>();
        random = new Random(seed);
        noise = new OpenSimplexNoise2D(seed);

        framePart = new PlatesChunksFramePart(plateChunksByPos, this, blockSize);

        registerPlate(new PlateAirHandler());
    }

    public PlateScene(String seed, StringToLongTransformer stringToLongTransformer, int blockSize) {
        this(stringToLongTransformer.transform(seed), blockSize);
    }

    public PlateScene(String seed, int blockSize) {
        this(seed, DefaultStringToLongTransformer.get(), blockSize);
    }

    public int registerPlate(PlateHandler plate) {
        int newPlateID = handlers.size();
        plate.register(newPlateID, this);

        handlers.add(plate);
        return newPlateID;
    }

    public int loadChunk(PlateChunk chunk, Vector2i position) {
        int chunkID = plateChunksByID.size();

        plateChunksByID.put(chunkID, chunk);
        plateChunksByPos.put(position.x + ":" + position.y, chunk);
        chunk.xPosition = position.x;
        chunk.yPosition = position.y;

        try {
            chunk.scene = this;
            chunk.generated = chunk.generate(chunkID);
        } catch (Exception e) {
            Logger.warning("Fail to generate " + chunkID + " chunk (" + position.x + ";" + position.y + ")");
            Logger.printException(e);
        }

        return chunkID;
    }

    public PlateHandler getPlate(int id) {
        if (handlers.size() <= id) return null;

        return handlers.get(id);
    }

    public int getPlateID(Vector2i globalPos) {
        int xChuck = globalPos.x >> 4;
        int yChunk = globalPos.y >> 4;

        PlateChunk chunk = plateChunksByPos.get(xChuck+ ":" + yChunk);
        if (chunk == null) return -1;

        int xBlock = globalPos.x & 15;
        int yBlock = globalPos.y & 15;

        return chunk.getPlate(xBlock, yBlock);
    }

    public void setPlate(Vector2i globalPos, int plateID) {
        int xChuck = globalPos.x >> 4;
        int yChunk = globalPos.y >> 4;

        PlateChunk chunk = plateChunksByPos.get(xChuck+ ":" + yChunk);
        if (chunk == null) return;

        int xBlock = globalPos.x & 15;
        int yBlock = globalPos.y & 15;

        chunk.setPlate(plateID, xBlock, yBlock);
    }

    public PlateChunk getChunk(Vector2i pos) {
        return plateChunksByPos.get(pos.x + ":" + pos.y);
    }

    public PlateChunk getChunk(int id) {
        return plateChunksByID.get(id);
    }

    public int getRegisteredPlatesCount() {
        return handlers.size();
    }

    public void update(ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        for (Map.Entry<String, PlateChunk> chunkEntry : plateChunksByPos.entrySet()) {
            PlateChunk chunk = chunkEntry.getValue();

            chunk.update();
        }

        partsBuilder.addPart(framePart);
    }

}
