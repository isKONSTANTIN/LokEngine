package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.rigidbody.shapes;

import ru.lokincompany.lokengine.tools.saveworker.Saveable;

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