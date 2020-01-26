package ru.lokincompany.lokengine.sceneenvironment.plateenvironment;

import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.enums.InteractionType;

public class PlateAirHandler extends PlateHandler {

    @Override
    public void register(int plateID, PlateScene scene) {

    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public void randomTickHandle(int xWorld, int yWorld) {

    }

    @Override
    public void createHandle(int xWorld, int yWorld) {

    }

    @Override
    public void neighboringInteractionHandle(int myXWorld, int myYWorld, int nearbyXWorld, int nearbyYWorld, InteractionType type) {

    }
}
