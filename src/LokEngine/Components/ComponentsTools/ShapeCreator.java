package LokEngine.Components.ComponentsTools;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;

public class ShapeCreator {

    public static PolygonShape CreateArbitraryShape(Vector2f[] collidePoints) {
        PolygonShape polyShape = new PolygonShape();
        int length = collidePoints.length;
        Vec2[] b2Points = new Vec2[length];

        for (int i = 0; i < length; i++) {
            b2Points[i] = new Vec2((collidePoints[i].x - 3.55f) * 0.005f,(collidePoints[i].y - 3.55f) * 0.005f);
        }

        polyShape.set(b2Points, length);

        return polyShape;
    }

    public static  PolygonShape CreateBoxShape(Vector2f collideSize) {
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox((collideSize.x - 3.55f) * 0.005f, (collideSize.y - 3.55f) * 0.005f);

        return polyShape;
    }

    public static CircleShape CreateCircleShape(float radius) {
        CircleShape shape = new CircleShape();
        shape.m_radius = (radius - 3.55f) * 0.005f;

        return shape;
    }

}
