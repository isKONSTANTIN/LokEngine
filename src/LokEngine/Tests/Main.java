package LokEngine.Tests;

import LokEngine.Application;
import LokEngine.Components.AdditionalObjects.Rigidbody;
import LokEngine.Components.ComponentsTools.ShapeCreator;
import LokEngine.Components.RigidbodyComponent;
import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUIText;
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

    @Override
    public void Update(){
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            appWin.close();
        }
    }

    @Override
    public void Init(){
        RuntimeFields.canvas.putObject(
                new GUIPanel(
                        new Vector2i(0,0),
                        new Vector2i(appWin.getResolution().x / 4,appWin.getResolution().y),
                        new ColorRGB(209, 226, 255,255 / 3),
                        new BlurTuning()
                ),
                "PanelTest");

        RuntimeFields.canvas.putObject(
                new GUIText(new Vector2i(0,0), "test"),
                "TestText"
        );
    }

    LokEngineTest(){
        start(false,new Vector2i(512,512));
    }
}
