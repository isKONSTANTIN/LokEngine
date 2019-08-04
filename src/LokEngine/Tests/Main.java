package LokEngine.Tests;

import LokEngine.Application;
import LokEngine.Components.AdditionalObjects.Rigidbody;
import LokEngine.Components.ComponentsTools.ShapeCreator;
import LokEngine.Components.RigidbodyComponent;
import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.GUIObjects.GUIObject;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.Render.Frame.FrameParts.PostProcessingActions.BlurAction;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Main {
    public static void main(String[] args){
        new LokEngineTest();
    }
}

class LokEngineTest extends Application {

    int idObject = 0;

    @Override
    public void Update(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            RuntimeFields.scene.getObjectByID(idObject).position.y+=0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            RuntimeFields.scene.getObjectByID(idObject).position.y-=0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            RuntimeFields.scene.getObjectByID(idObject).position.x-=0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            RuntimeFields.scene.getObjectByID(idObject).position.x+=0.01f;
        }

        appWin.getCamera().position = RuntimeFields.scene.getObjectByID(idObject).position;

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            appWin.close();
        }
    }

    @Override
    public void Init(){
        SceneObject so = new SceneObject();
        so.components.add(new SpriteComponent("#/resources/textures/unk1nown.png"));
        so.components.add(new RigidbodyComponent(ShapeCreator.CreateBoxShape(new Vector2f(200,200))));
        idObject = RuntimeFields.scene.addObject(so);

        SceneObject so2 = new SceneObject();
        so2.components.add(new SpriteComponent("#/resources/textures/unkn1own.png"));
        Rigidbody rb = new Rigidbody();
        rb.isStatic = true;
        so2.components.add(new RigidbodyComponent(ShapeCreator.CreateBoxShape(new Vector2f(200,200)), rb));
        RuntimeFields.scene.addObject(so2);
    }

    LokEngineTest(){
        start(false,new Vector2i(512,512));
    }
}
