package LokEngine.Tests;

import LokEngine.Application;
import LokEngine.Components.AdditionalObjects.Animation;
import LokEngine.Components.AdditionalObjects.AtlasPositions;
import LokEngine.Components.AdditionalObjects.Rigidbody;
import LokEngine.Components.AnimationComponent;
import LokEngine.Components.ComponentsTools.ShapeCreator;
import LokEngine.Components.RigidbodyComponent;
import LokEngine.Components.SoundComponent;
import LokEngine.Components.SpriteComponent;
import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.GUI.Canvases.GUIGridCanvas;
import LokEngine.GUI.GUIObjects.*;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.SceneEnvironment.SceneObject;
import LokEngine.Tools.Logger;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.*;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Main {
    public static void main(String[] args){
        new LokEngineTest();
    }
}

class LokEngineTest extends Application {

    int idObject = 0;
    int idWallObject = 0;
    int gridObject = 0;
    Scene scene;
    GUICanvas canvas;

    SoundComponent soundComponent;

    @Override
    public void Update(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            scene.getObjectByID(idObject).position.y += 0.01f * RuntimeFields.getDeltaTime();
        }else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            scene.getObjectByID(idObject).position.y -= 0.01f * RuntimeFields.getDeltaTime();
        }else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            scene.getObjectByID(idObject).position.x -= 0.01f * RuntimeFields.getDeltaTime();
            ((AnimationComponent)scene.getObjectByID(idObject).components.get(0)).getActiveAnimation().speedAnimation = -1;
        }else if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            scene.getObjectByID(idObject).position.x += 0.01f * RuntimeFields.getDeltaTime();
            ((AnimationComponent)scene.getObjectByID(idObject).components.get(0)).getActiveAnimation().speedAnimation = 1;
        }else{
            ((AnimationComponent)scene.getObjectByID(idObject).components.get(0)).getActiveAnimation().speedAnimation = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)){
            RuntimeFields.setSpeedEngine(RuntimeFields.getSpeedEngine() == 0 ? 1 : RuntimeFields.getSpeedEngine());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)){
            RuntimeFields.setSpeedEngine(RuntimeFields.getSpeedEngine() == 1 ? 0 : RuntimeFields.getSpeedEngine());
        }
        window.getCamera().position = scene.getObjectByID(idObject).position;
        scene.getObjectByID(idWallObject).position = new Vector2f(window.getCamera().position.x / 1.2f, window.getCamera().position.y / 1.05f);

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            close();
        }

        System.gc();
        ((GUIText)((GUIGridCanvas)canvas.getObject(gridObject)).getObject(0)).updateText("Free Memory: " + String.valueOf(Runtime.getRuntime().freeMemory() / 1000000) + " MB");
        ((GUIText)((GUIGridCanvas)canvas.getObject(gridObject)).getObject(1)).updateText("FPS: " + RuntimeFields.getFps());
        ((GUIText)((GUIGridCanvas)canvas.getObject(gridObject)).getObject(2)).updateText("X: " + Math.round(window.getCamera().position.x * 10f) / 10f);
        ((GUIText)((GUIGridCanvas)canvas.getObject(gridObject)).getObject(3)).updateText("Y: " + Math.round(window.getCamera().position.y * 10f) / 10f );
    }

    @Override
    public void Init(){
        scene = RuntimeFields.getScene();
        canvas = RuntimeFields.getCanvas();

        SceneObject so = new SceneObject();
        AnimationComponent animationComponent = new AnimationComponent();

        animationComponent.addAnimation(
                new Animation(
                        "#/resources/textures/testAnim.png",
                        new AtlasPositions(new Vector4i(0,0,100,100),32)
                ),
                "testAnim"
        );
        so.position.y = 0.2f;
        so.components.add(animationComponent);
        so.components.add(new RigidbodyComponent(ShapeCreator.CreateBoxShape(new Vector2f(100,100))));
        idObject = scene.addObject(so);

        SceneObject so2 = new SceneObject();
        so2.components.add(new SpriteComponent("#/resources/textures/unkn1own.png"));
        Rigidbody rb = new Rigidbody();
        rb.isStatic = true;
        so2.components.add(new RigidbodyComponent(ShapeCreator.CreateBoxShape(new Vector2f(200,200)), rb));

        soundComponent = new SoundComponent("#/resources/test.wav");
        soundComponent.play();
        soundComponent.setLooping(true);
        so.components.add(soundComponent);
        scene.addObject(so2);

        SceneObject so3 = new SceneObject();
        SpriteComponent sp = new SpriteComponent("#/resources/textures/testWallpaper.jpg");
        so3.components.add(sp);

        idWallObject = scene.addObject(so3);

        BlurTuning bt = new BlurTuning(0.1,5,0.6);

        Vector2i panelSize = new Vector2i(150, window.getResolution().y);
        canvas.putObject(
                new GUIPanel(
                        new Vector2i(0,0),
                        panelSize,
                        new ColorRGB(200, 200, 200,100),
                        bt
                )
        );

        GUIGridCanvas guiGridCanvas = new GUIGridCanvas(new Vector2i(0,0),new Vector2i(panelSize.x,panelSize.x),4,1);

        gridObject = canvas.putObject(guiGridCanvas);

        guiGridCanvas.putObject(new GUIText(new Vector2i(0,0),"",new ColorRGB(255,255,255),0,12));
        guiGridCanvas.putObject(new GUIText(new Vector2i(0,0),"",new ColorRGB(255,255,255),0,12));
        guiGridCanvas.putObject(new GUIText(new Vector2i(0,0),"",new ColorRGB(255,255,255),0,12));
        guiGridCanvas.putObject(new GUIText(new Vector2i(0,0),"",new ColorRGB(255,255,255),0,12));
    }

    LokEngineTest(){
        Logger.debugMessages = true;
        start();
    }
}
