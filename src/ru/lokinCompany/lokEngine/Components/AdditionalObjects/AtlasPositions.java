package ru.lokinCompany.lokEngine.Components.AdditionalObjects;

import org.lwjgl.util.vector.Vector2f;
import ru.lokinCompany.lokEngine.Loaders.BufferLoader;
import ru.lokinCompany.lokEngine.Render.Texture;
import ru.lokinCompany.lokEngine.Tools.Base64.Base64;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector4i;

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

    public ArrayList<Integer> build(Texture texture) {
        Vector2f fistPoint = new Vector2f((float) startPosition.x / (float) texture.sizeX, (float) startPosition.w / (float) texture.sizeY);
        Vector2f secondPoint = new Vector2f((float) startPosition.x / (float) texture.sizeX, (float) startPosition.y / (float) texture.sizeY);
        Vector2f thirdPoint = new Vector2f((float) startPosition.z / (float) texture.sizeX, (float) startPosition.y / (float) texture.sizeY);
        Vector2f fourthPoint = new Vector2f((float) startPosition.z / (float) texture.sizeX, (float) startPosition.w / (float) texture.sizeY);

        ArrayList<Integer> uvBuffers = new ArrayList<>();

        for (int i = 0; i < countSprites; i++) {
            float offset = thirdPoint.x * i;
            uvBuffers.add(
                    BufferLoader.load(new float[]{
                            fistPoint.x + offset, fistPoint.y,
                            secondPoint.x + offset, secondPoint.y,
                            thirdPoint.x + offset, thirdPoint.y,
                            fourthPoint.x + offset, fourthPoint.y
                    })
            );
        }

        for (Vector4i position : positions) {
            uvBuffers.add(
                    BufferLoader.load(new float[]{
                            (float) position.x / (float) texture.sizeX, (float) position.w / (float) texture.sizeY,
                            (float) position.x / (float) texture.sizeX, (float) position.y / (float) texture.sizeY,
                            (float) position.z / (float) texture.sizeX, (float) position.y / (float) texture.sizeY,
                            (float) position.z / (float) texture.sizeX, (float) position.w / (float) texture.sizeY
                    })
            );
        }

        return uvBuffers;
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
