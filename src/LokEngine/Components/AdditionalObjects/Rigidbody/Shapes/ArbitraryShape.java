package LokEngine.Components.AdditionalObjects.Rigidbody.Shapes;

import LokEngine.Components.ComponentsTools.ShapeCreator;
import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector2f;

public class ArbitraryShape extends Shape {

    public Vector2f[] collidePoints;

    public ArbitraryShape(){}

    public ArbitraryShape(Vector2f[] collidePoints, org.jbox2d.collision.shapes.Shape shape){
        this.collidePoints = collidePoints;
        this.shape = shape;
    }

    @Override
    public String save() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Vector2f vector2f : collidePoints){
            stringBuilder.append(vector2f.x).append(":").append(vector2f.y).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        return Base64.toBase64(stringBuilder.toString());
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        collidePoints = new Vector2f[lines.length];

        for (int i = 0; i < lines.length; i++){
            String[] vectors = lines[i].split(":");
            collidePoints[i].x = Float.valueOf(vectors[0]);
            collidePoints[i].y = Float.valueOf(vectors[1]);
        }

        this.shape = ShapeCreator.CreateArbitraryShape(collidePoints).shape;

        return this;
    }
}
