package ru.lokincompany.lokengine.components.additionalobjects.rigidbody.shapes;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.components.componentstools.ShapeCreator;
import ru.lokincompany.lokengine.tools.base64.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;

public class ArbitraryShape extends Shape {

    public Vector2f[] collidePoints;

    public ArbitraryShape() {
    }

    public ArbitraryShape(Vector2f[] collidePoints, org.jbox2d.collision.shapes.Shape shape) {
        this.collidePoints = collidePoints;
        this.shape = shape;
    }

    @Override
    public String save() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Vector2f vector2f : collidePoints) {
            stringBuilder.append(vector2f.x).append(":").append(vector2f.y).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return Base64.toBase64(stringBuilder.toString());
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        collidePoints = new Vector2f[lines.length];

        for (int i = 0; i < lines.length; i++) {
            String[] vectors = lines[i].split(":");
            collidePoints[i] = new Vector2f(Float.parseFloat(vectors[0]), Float.parseFloat(vectors[1]));
        }

        this.shape = ShapeCreator.CreateArbitraryShape(collidePoints).shape;

        return this;
    }
}
