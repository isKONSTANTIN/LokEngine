package ru.lokinCompany.lokEngine.Components.AdditionalObjects.Rigidbody.Shapes;

import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public class Shape implements Saveable {
    public org.jbox2d.collision.shapes.Shape shape;

    @Override
    public String save() {
        return "";
    }

    @Override
    public Saveable load(String savedString) {
        return this;
    }
}