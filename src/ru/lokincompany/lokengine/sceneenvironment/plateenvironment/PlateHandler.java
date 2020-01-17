package ru.lokincompany.lokengine.sceneenvironment.plateenvironment;

import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.enums.InteractionType;

public abstract class PlateHandler {
    public abstract void register(int plateID, PlateScene scene);

    public abstract Texture getTexture();

    public abstract void randomTickHandle(int xWorld, int yWorld);

    public abstract void createHandle(int xWorld, int yWorld);

    public abstract void neighboringInteractionHandle(int myXWorld, int myYWorld, int nearbyXWorld, int nearbyYWorld, InteractionType type);
}
