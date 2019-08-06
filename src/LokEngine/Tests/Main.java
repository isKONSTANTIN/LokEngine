package LokEngine.Tests;

import LokEngine.Application;
import LokEngine.GUI.GUIObjects.GUIPanel;
import LokEngine.GUI.GUIObjects.GUIText;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.ColorRGB;
import LokEngine.Tools.Utilities.Vector2i;
import org.lwjgl.input.Keyboard;

public class Main {
    public static void main(String[] args){
        new LokEngineTest();
    }
}

class LokEngineTest extends Application {
    @Override
    public void Update(){
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            window.close();
        }
    }

    @Override
    public void Init(){
        RuntimeFields.canvas.putObject(
                new GUIText(new Vector2i(0,0), "test")
        );

        RuntimeFields.canvas.putObject(
                new GUIPanel(
                        new Vector2i(0,0),
                        new Vector2i(window.getResolution().x / 4, window.getResolution().y),
                        new ColorRGB(209, 226, 255,255 / 3),
                        new BlurTuning()
                ));
    }

    LokEngineTest(){
        start();
    }
}
