package ru.lokinCompany.lokEngine.Components.AdditionalObjects.Rigidbody.Shapes;

import ru.lokinCompany.lokEngine.Components.ComponentsTools.ShapeCreator;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public class CircleShape extends Shape {

    public float radius;

    public CircleShape() {
    }

    public CircleShape(float radius, org.jbox2d.collision.shapes.Shape shape) {
        this.radius = radius;
        this.shape = shape;
    }

    @Override
    public String save() {
        return String.valueOf(radius);
    }

    @Override
    public Saveable load(String savedString) {
        this.radius = Float.parseFloat(savedString);
        this.shape = ShapeCreator.CreateCircleShape(radius).shape;
        return this;
    }
}
