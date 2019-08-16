package LokEngine.Components.AdditionalObjects;

import LokEngine.Loaders.BufferLoader;
import LokEngine.Render.Texture;
import LokEngine.Tools.Utilities.Vector2i;
import LokEngine.Tools.Utilities.Vector4i;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;

public class AtlasPositions {

    public ArrayList<Vector4i> positions = new ArrayList<>();
    public Vector4i startPosition;
    public int countSprites;

    public AtlasPositions(Vector4i startPosition, int countSprites){
        this.startPosition = startPosition;
        this.countSprites = countSprites;
    }

    public AtlasPositions(Vector4i startPosition){
        this.startPosition = startPosition;
    }

    public AtlasPositions addNewPosition(Vector4i position){
        positions.add(position);

        return this;
    }

    public ArrayList<Integer> build(Texture texture){
        Vector2f fistPoint   = new Vector2f((float)startPosition.z / (float)texture.sizeX, (float)startPosition.w / (float)texture.sizeY);
        Vector2f secondPoint = new Vector2f((float)startPosition.z / (float)texture.sizeX, (float)startPosition.y / (float)texture.sizeY);
        Vector2f thirdPoint  = new Vector2f((float)startPosition.x / (float)texture.sizeX, (float)startPosition.y / (float)texture.sizeY);
        Vector2f fourthPoint = new Vector2f((float)startPosition.x / (float)texture.sizeX, (float)startPosition.w / (float)texture.sizeY);

        ArrayList<Integer> uvBuffers = new ArrayList<>();

        for (int i = 0; i < countSprites; i++){
            float offset = fistPoint.x * i;
            uvBuffers.add(
                    BufferLoader.load(new float[]{
                            fistPoint.x + offset, fistPoint.y,
                            secondPoint.x + offset, secondPoint.y,
                            thirdPoint.x + offset, thirdPoint.y,
                            fourthPoint.x + offset, fourthPoint.y
                    })
            );
        }

        for (int i = 0; i < positions.size(); i++){
            uvBuffers.add(
                    BufferLoader.load(new float[]{
                            (float)positions.get(i).z / (float)texture.sizeX, (float)positions.get(i).w / (float)texture.sizeY,
                            (float)positions.get(i).z / (float)texture.sizeX, (float)positions.get(i).y / (float)texture.sizeY,
                            (float)positions.get(i).x / (float)texture.sizeX, (float)positions.get(i).y / (float)texture.sizeY,
                            (float)positions.get(i).x / (float)texture.sizeX, (float)positions.get(i).w / (float)texture.sizeY
                    })
            );
        }

        return uvBuffers;
    }

}
