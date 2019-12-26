package ru.lokincompany.lokengine.sceneenvironment.components.componentstools;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.rigidbody.shapes.ArbitraryShape;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.rigidbody.shapes.BoxShape;
import ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.rigidbody.shapes.CircleShape;

public class ShapeCreator {

    public static ArbitraryShape CreateArbitraryShape(Vector2f[] collidePoints) {
        PolygonShape polyShape = new PolygonShape();
        int length = collidePoints.length;
        Vec2[] b2Points = new Vec2[length];

        for (int i = 0; i < length; i++) {
            b2Points[i] = new Vec2((collidePoints[i].x - 3.55f) * 0.005f, (collidePoints[i].y - 3.55f) * 0.005f);
        }

        polyShape.set(b2Points, length);

        return new ArbitraryShape(collidePoints, polyShape);
    }

    public static BoxShape CreateBoxShape(Vector2f collideSize) {
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox((collideSize.x - 3.55f) * 0.005f, (collideSize.y - 3.55f) * 0.005f);

        return new BoxShape(collideSize, polyShape);
    }

    public static CircleShape CreateCircleShape(float radius) {
        org.jbox2d.collision.shapes.CircleShape shape = new org.jbox2d.collision.shapes.CircleShape();
        shape.m_radius = (radius - 3.55f) * 0.005f;

        return new CircleShape(radius, shape);
    }

}
