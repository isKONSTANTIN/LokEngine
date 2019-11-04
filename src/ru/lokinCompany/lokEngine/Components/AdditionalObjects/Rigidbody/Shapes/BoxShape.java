package ru.lokinCompany.lokEngine.Components.AdditionalObjects.Rigidbody.Shapes;

import ru.lokinCompany.lokEngine.Components.ComponentsTools.ShapeCreator;
import ru.lokinCompany.lokEngine.Tools.Base64.Base64;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.util.vector.Vector2f;

public class BoxShape extends Shape {

    public Vector2f collideSize;

    public BoxShape() {
    }

    public BoxShape(Vector2f collideSize, org.jbox2d.collision.shapes.Shape shape) {
        this.collideSize = collideSize;
        this.shape = shape;
    }

    @Override
    public String save() {
        return Base64.toBase64(collideSize.x + "\n" + collideSize.y);
    }

    @Override
    public Saveable load(String savedString) {
        String[] stringVectors = Base64.fromBase64(savedString).split("\n");
        this.collideSize = new Vector2f(Float.valueOf(stringVectors[0]), Float.valueOf(stringVectors[1]));
        this.shape = ShapeCreator.CreateBoxShape(collideSize).shape;

        return this;
    }
}
