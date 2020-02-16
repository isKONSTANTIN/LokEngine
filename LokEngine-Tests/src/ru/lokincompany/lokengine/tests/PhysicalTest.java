package ru.lokincompany.lokengine.tests;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokengine.applications.ApplicationDefault;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.RigidbodyComponent;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.SpriteComponent;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.additionalobjects.rigidbody.Rigidbody;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.componentstools.ShapeCreator;

public class PhysicalTest extends ApplicationDefault {

    public static void main(String[] args) {
        new PhysicalTest().start(false, true, true, 16);
    }

    @Override
    protected void initEvent() {
        for (int i = 0; i < 30; i++) {
            SceneObject object = new SceneObject();
            object.components.add(new SpriteComponent("#/resources/textures/EngineIcon32.png"));
            object.components.add(new RigidbodyComponent(ShapeCreator.CreateCircleShape(31), new Rigidbody(false, 1)));
            scene.addObject(object);
        }

        SceneObject plate = new SceneObject();
        plate.components.add(new RigidbodyComponent(ShapeCreator.CreateBoxShape(new Vector2f(5000, 10)), new Rigidbody(true, 1)));
        plate.position.y -= 0.1f;
        scene.addObject(plate);
    }
}
