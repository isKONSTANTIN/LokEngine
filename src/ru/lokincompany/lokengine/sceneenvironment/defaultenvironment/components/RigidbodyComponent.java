package ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.applications.ApplicationRuntime;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.PostUpdateEvent;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.rigidbody.Rigidbody;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.rigidbody.shapes.Shape;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.saveworker.SubclassSaver;

public class RigidbodyComponent extends Component implements Saveable {

    public Shape polygons;
    Rigidbody body;
    private boolean bodyInited = false;

    public RigidbodyComponent() {
    }

    public RigidbodyComponent(Shape polygons) {
        this.polygons = polygons;
        this.body = new Rigidbody();
    }

    public RigidbodyComponent(Shape polygons, Rigidbody rigidbody) {
        this.polygons = polygons;
        this.body = rigidbody;
    }

    @Override
    public void update(SceneObject source, ApplicationRuntime applicationRuntime, PartsBuilder partsBuilder) {
        if (!bodyInited) {
            initBody(source, polygons);
            bodyInited = true;
        }
        Vector2f b2posU = new Vector2f(body.b2body.getPosition().x / 9.5f, body.b2body.getPosition().y / 9.5f);

        if (!b2posU.equals(source.position) || source.rollRotation != (float) MatrixTools.radiansToDegrees(body.b2body.getAngle())) {
            body.b2body.setTransform(new Vec2(source.position.x * 9.5f, source.position.y * 9.5f), (float) MatrixTools.degressToRadians(source.rollRotation));
            body.b2body.setAwake(true);
        }

        source.scene.addPostUpdateEvent(new PostUpdateEvent() {
            @Override
            public void postUpdate() {
                Vec2 b2posP = body.b2body.getPosition();
                source.position = new Vector2f(b2posP.x / 9.5f, b2posP.y / 9.5f);
                source.rollRotation = (float) MatrixTools.radiansToDegrees(body.b2body.getAngle());
            }
        });
    }

    private void initBody(SceneObject object, Shape shape) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(object.position.x * 9.5f, object.position.y * 9.5f);
        bodyDef.angle = (float) MatrixTools.degressToRadians(object.rollRotation);
        bodyDef.type = body.isStatic ? BodyType.STATIC : BodyType.DYNAMIC;

        Body body = object.scene.b2World.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = this.body.isSensor;
        if (this.body.objectData != null)
            fixtureDef.setUserData(this.body.objectData);
        fixtureDef.shape = shape.shape;
        fixtureDef.density = this.body.density;
        fixtureDef.friction = this.body.friction;

        body.createFixture(fixtureDef);

        this.body.b2body = body;
    }

    public void addForce(Vector2f forceToCenter) {
        body.b2body.applyForceToCenter(new Vec2(forceToCenter.x, forceToCenter.y));
    }

    public void addForce(Vector2f force, Vector2f point) {
        body.b2body.applyForce(new Vec2(force.x, force.y), new Vec2(point.x, point.y));
    }

    public void addAngularForce(float force) {
        body.b2body.applyAngularImpulse(force);
    }

    public void setAngularSpeed(float speed) {
        body.b2body.setAngularVelocity(speed);
    }

    public Rigidbody getRigidbody() {
        return body;
    }

    @Override
    public String save() {
        SubclassSaver subclassSaver = new SubclassSaver(polygons);

        return Base64.toBase64(subclassSaver.save() + "\n" + body.save());
    }

    @Override
    public Saveable load(String savedString) {
        String[] lines = Base64.fromBase64(savedString).split("\n");

        SubclassSaver subclassSaver = new SubclassSaver();

        polygons = (Shape) (((SubclassSaver) subclassSaver.load(lines[0])).saveableObject);
        body = (Rigidbody) (new Rigidbody().load(lines[1]));

        return this;
    }
}
