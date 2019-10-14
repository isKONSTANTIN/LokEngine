package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Render.Texture;
import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.SaveWorker.Saveable;
import LokEngine.Tools.Utilities.Vector4i;
import org.lwjgl.util.vector.Vector2f;

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
        Vector2f fistPoint   = new Vector2f((float) startPosition.x / (float) texture.sizeX, (float) startPosition.w / (float) texture.sizeY);
        Vector2f secondPoint = new Vector2f((float) startPosition.x / (float) texture.sizeX, (float) startPosition.y / (float) texture.sizeY);
        Vector2f thirdPoint  = new Vector2f((float) startPosition.z / (float) texture.sizeX, (float) startPosition.y / (float) texture.sizeY);
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

        for (int i = 0; i < positions.size(); i++) {
            uvBuffers.add(
                    BufferLoader.load(new float[]{
                            (float) positions.get(i).x / (float) texture.sizeX, (float) positions.get(i).w / (float) texture.sizeY,
                            (float) positions.get(i).x / (float) texture.sizeX, (float) positions.get(i).y / (float) texture.sizeY,
                            (float) positions.get(i).z / (float) texture.sizeX, (float) positions.get(i).y / (float) texture.sizeY,
                            (float) positions.get(i).z / (float) texture.sizeX, (float) positions.get(i).w / (float) texture.sizeY
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
        return new Vector4i(
                Integer.valueOf(vectorParts[0]),
                Integer.valueOf(vectorParts[1]),
                Integer.valueOf(vectorParts[2]),
                Integer.valueOf(vectorParts[3])
        );
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = Base64.fromBase64(savedString).split(";");
        String[] vectors = data[0].split("\n");
        this.countSprites = Integer.valueOf(data[2]);

        positions = new ArrayList<>();

        for (String stringVector : vectors) {
            if (!stringVector.equals(""))
                positions.add(parseVector(stringVector));
        }
        startPosition = parseVector(data[1]);

        return this;
    }
}
