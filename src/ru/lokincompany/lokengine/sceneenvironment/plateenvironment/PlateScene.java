package ru.lokincompany.lokengine.sceneenvironment.plateenvironment;

import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.frame.frameparts.plate.PlatesChunksFramePart;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.opensimplexnoise.DefaultStringToLongTransformer;
import ru.lokincompany.lokengine.tools.opensimplexnoise.OpenSimplexNoise2D;
import ru.lokincompany.lokengine.tools.opensimplexnoise.StringToLongTransformer;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.Random;
import java.util.Vector;

public class PlateScene {
    final Vector<PlateHandler> handlers;
    final Vector<PlateChunk> plateChunks;

    PlatesChunksFramePart framePart;

    public final Random random;
    public final OpenSimplexNoise2D noise;

    public int randomUpdateIterations = 1;

    public PlateScene(long seed, int blockSize){
        handlers = new Vector<>();
        plateChunks = new Vector<>();
        random = new Random(seed);
        noise = new OpenSimplexNoise2D(seed);

        framePart = new PlatesChunksFramePart(plateChunks, this, blockSize);

        registerPlate(new PlateAirHandler());
    }

    public PlateScene(String seed, StringToLongTransformer stringToLongTransformer, int blockSize){
        this(stringToLongTransformer.transform(seed), blockSize);
    }

    public PlateScene(String seed, int blockSize){
        this(seed, DefaultStringToLongTransformer.get(), blockSize);
    }

    public int registerPlate(PlateHandler plate){
        int newPlateID = handlers.size();
        plate.register(newPlateID, this);

        handlers.add(plate);
        return newPlateID;
    }

    public int loadChunk(PlateChunk chunk, Vector2i position){
        int chunkID = plateChunks.size();

        plateChunks.add(chunk);
        chunk.xPosition = position.x;
        chunk.yPosition = position.y;

        try {
            chunk.generated = chunk.generate(chunkID, this);
        } catch (Exception e){
            Logger.warning("Fail to generate " + chunkID + " chunk (" + position.x + ";" + position.y + ")");
            Logger.printException(e);
        }

        return chunkID;
    }

    public PlateHandler getPlate(int id){
        return handlers.get(id);
    }

    public PlateChunk getChunk(int id){
        return plateChunks.get(id);
    }

    public int getRegisteredPlatesCount(){
        return handlers.size();
    }

    public void update(ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder){
        for (PlateChunk chunk : plateChunks){
            chunk.update(this);
        }

        partsBuilder.addPart(framePart);
    }

}
