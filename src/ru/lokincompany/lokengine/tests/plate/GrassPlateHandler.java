package ru.lokincompany.lokengine.tests.plate;

import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateHandler;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.PlateScene;
import ru.lokincompany.lokengine.sceneenvironment.plateenvironment.enums.InteractionType;
import ru.lokincompany.lokengine.tools.Logger;

public class GrassPlateHandler extends PlateHandler {
    PlateScene scene;

    @Override
    public void register(int plateID, PlateScene scene) {
        this.scene = scene;
    }

    @Override
    public Texture getTexture() {
        return TextureLoader.loadTexture("");
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
