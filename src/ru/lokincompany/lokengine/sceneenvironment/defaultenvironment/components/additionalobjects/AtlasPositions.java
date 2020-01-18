package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.render.VBO;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector4i;

import java.util.ArrayList;

public class AtlasPositions implements Saveable {

    public ArrayList<Vector4i> positions = new ArrayList<>();
    public Vector4i startPosition;
    public int countSprites;

    public AtlasPositions() {
    }

    public AtlasPositions(Vector4i startPosition, int countSprites) {
        this.startPosition = startPosition;
        this.countSprites = countSprites;
    }

    public AtlasPositions(Vector4i startPosition) {
        this.startPosition = startPosition;
    }

    public AtlasPositions addNewPosition(Vector4i position) {
        positions.add(position);

        return this;
    }

    public ArrayList<VBO> build(Texture texture) {
        Vector2f fistPoint = new Vector2f((float) startPosition.x / (float) texture.getSizeX(), (float) startPosition.w / (float) texture.getSizeY());
        Vector2f secondPoint = new Vector2f((float) startPosition.x / (float) texture.getSizeX(), (float) startPosition.y / (float) texture.getSizeY());
        Vector2f thirdPoint = new Vector2f((float) startPosition.z / (float) texture.getSizeX(), (float) startPosition.y / (float) texture.getSizeY());
        Vector2f fourthPoint = new Vector2f((float) startPosition.z / (float) texture.getSizeX(), (float) startPosition.w / (float) texture.getSizeY());

        ArrayList<VBO> uvVBOs = new ArrayList<>();

        for (int i = 0; i < countSprites; i++) {
            float offset = thirdPoint.x * i;
            uvVBOs.add(
                    new VBO(new float[]{
                            fistPoint.x + offset, fistPoint.y,
                            secondPoint.x + offset, secondPoint.y,
                            thirdPoint.x + offset, thirdPoint.y,
                            fourthPoint.x + offset, fourthPoint.y
                    })
            );
        }

        for (Vector4i position : positions) {
            uvVBOs.add(
                    new VBO(new float[]{
                            (float) position.x / (float) texture.getSizeX(), (float) position.w / (float) texture.getSizeY(),
                            (float) position.x / (float) texture.getSizeX(), (float) position.y / (float) texture.getSizeY(),
                            (float) position.z / (float) texture.getSizeX(), (float) position.y / (float) texture.getSizeY(),
                            (float) position.z / (float) texture.getSizeX(), (float) position.w / (float) texture.getSizeY()
                    })
            );
        }

        return uvVBOs;
    }

    @Override
    public String save() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Vector4i pos : positions) {
            stringBuilder
                    .append(pos.x).append(",")
                    .append(pos.y).append(",")
                    .append(pos.z).append(",")
                    .append(pos.w).append("\n");
        }

        stringBuilder.append(";")
                .append(startPosition.x).append(",")
                .append(startPosition.y).append(",")
                .append(startPosition.z).append(",")
                .append(startPosition.w);

        stringBuilder.append(";").append(countSprites);

        return Base64.toBase64(stringBuilder.toString());
    }

    private Vector4i parseVector(String data) {
        String[] vectorParts = data.split(",");
        if (vectorParts.length < 4) return null;
        return new Vector4i(
                Integer.parseInt(vectorParts[0]),
                Integer.parseInt(vectorParts[1]),
                Integer.parseInt(vectorParts[2]),
                Integer.parseInt(vectorParts[3])
        );
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split(";");
        String[] vectors = data[0].split("\n");
        this.countSprites = Integer.parseInt(data[2]);

        positions = new ArrayList<>();

        for (String stringVector : vectors) {
            if (!stringVector.equals(""))
                positions.add(parseVector(stringVector));
        }
        startPosition = parseVector(data[1]);

        return this;
    }
}
