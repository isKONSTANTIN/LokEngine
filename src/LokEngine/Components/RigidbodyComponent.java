package LokEngine.Components;

import LokEngine.Components.AdditionalObjects.Rigidbody;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.SceneEnvironment.PostUpdateEvent;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.MatrixCreator;
import LokEngine.Tools.RuntimeFields;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector2f;

public class RigidbodyComponent extends Component {

    Shape polygons;
    Rigidbody body;
    private boolean bodyInited = false;

    @Override
    public String getName(){
        return "Rigidbody Component";
    }

    @Override
    public void update(SceneObject source){

        if (!bodyInited){
            initBody(source,polygons);
            bodyInited = true;
        }
        Vector2f b2posU = new Vector2f(body.b2body.getPosition().x / 9.5f, body.b2body.getPosition().y / 9.5f);

        if (!b2posU.equals(source.position) || source.rollRotation != (float)MatrixCreator.RadiansToDegrees(body.b2body.getAngle())){
            body.b2body.setTransform(new Vec2(source.position.x * 9.5f, source.position.y * 9.5f),(float)MatrixCreator.DegressToRadians(source.rollRotation));
            body.b2body.setAwake(true);
        }

        source.scene.addPostUpdateEvent(new PostUpdateEvent(){
            @Override
            public void postUpdate(){
                Vec2 b2posP = body.b2body.getPosition();
                source.position = new Vector2f(b2posP.x / 9.5f, b2posP.y / 9.5f);
                source.rollRotation = (float)MatrixCreator.RadiansToDegrees(body.b2body.getAngle());
            }
        });
    }

    private void initBody(SceneObject object, Shape shape){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(object.position.x * 9.5f, object.position.y * 9.5f);
        bodyDef.angle = (float)MatrixCreator.DegressToRadians(object.rollRotation);

        bodyDef.type = body.isStatic ? BodyType.STATIC : BodyType.DYNAMIC;

        Body body = RuntimeFields.scene.b2World.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.body.density;
        fixtureDef.friction = this.body.friction;

        body.createFixture(fixtureDef);

        this.body.b2body = body;
    }

    public RigidbodyComponent(Shape polygons) {
        this.polygons = polygons;
        this.body = new Rigidbody();
    }

    public RigidbodyComponent(Shape polygons, Rigidbody rigidbody) {
        this.polygons = polygons;
        this.body = rigidbody;
    }

    public void addForce(Vector2f forceToCenter){
        body.b2body.applyForceToCenter(new Vec2(forceToCenter.x,forceToCenter.y));
    }

    public void addForce(Vector2f force, Vector2f point){
        body.b2body.applyForce(new Vec2(force.x,force.y),new Vec2(point.x,point.y));
    }

    public void addAngularForce(float force){
        body.b2body.applyAngularImpulse(force);
    }

    public void setAngularSpeed(float speed){
        body.b2body.setAngularVelocity(speed);
    }

    public Rigidbody getRigidbody(){
        return body;
    }
}
