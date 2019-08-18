package LokEngine.Tools;

import LokEngine.GUI.Canvas;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.Render.Shader;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.Utilities.MouseStatus;
import org.lwjgl.input.Mouse;

public class RuntimeFields {
    private static FrameBuilder frameBuilder;
    private static Scene scene;
    private static Canvas canvas;
    private static MouseStatus mouseStatus;
    private static float deltaTime;
    private static int fps;

    private static int fcpu;
    private static long lastUpdateTime;
    private static long lastFPSUpdateTime;

    public static FrameBuilder getFrameBuilder(){ return frameBuilder; }
    public static Scene getScene(){ return scene; }
    public static Canvas getCanvas(){ return canvas; }
    public static MouseStatus getMouseStatus(){ return mouseStatus; }
    public static float getDeltaTime(){ return deltaTime; }
    public static int getFps(){ return fps; }

    public static void init(FrameBuilder frameBuilder, Scene scene, Canvas canvas, MouseStatus mouseStatus){
        RuntimeFields.frameBuilder = frameBuilder;
        RuntimeFields.scene = scene;
        RuntimeFields.canvas = canvas;
        RuntimeFields.mouseStatus = mouseStatus;
    }

    public static void update(){
        mouseStatus.mousePosition.x = Mouse.getX();
        mouseStatus.mousePosition.y = Mouse.getY();
        mouseStatus.mousePressed = Mouse.isButtonDown(0);

        deltaTime = (System.nanoTime() - lastUpdateTime) / 10000000f;
        lastUpdateTime = System.nanoTime();

        if ((lastUpdateTime - lastFPSUpdateTime) / 1000000 >= 1000){
            fps = fcpu;
            fcpu = 0;
            lastFPSUpdateTime = lastUpdateTime;
        }

        fcpu++;
    }

}
