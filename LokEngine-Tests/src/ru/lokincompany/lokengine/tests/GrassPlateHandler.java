package ru.lokincompany.lokengine.tests;

import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateHandler;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateScene;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.enums.InteractionType;

public class GrassPlateHandler extends PlateHandler {
    PlateScene scene;

    @Override
    public void register(int plateID, PlateScene scene) {
        this.scene = scene;
    }

    @Override
    public Texture getTexture() {
        return new Texture("#/resources/textures/grass.png");
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
