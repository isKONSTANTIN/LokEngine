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

    public static float speedEngine = 1f;
    private static long lastUpdateTime;
    private static long lastFPSUpdateTime;

    public static FrameBuilder getFrameBuilder(){ return frameBuilder; }
    public static Scene getScene(){ return scene; }
    public static Canvas getCanvas(){ return canvas; }
    public static MouseStatus getMouseStatus(){ return mouseStatus; }
    public static float getFixedDeltaTime(){ return deltaTime; }
    public static float getDeltaTime(){ return deltaTime / 16.66666f * speedEngine; }
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

        long timeNow = System.nanoTime();

        deltaTime = (timeNow - lastUpdateTime) / 1000000f;
        lastUpdateTime = timeNow;

        if (timeNow - lastFPSUpdateTime >= 1000000000){
            lastFPSUpdateTime = timeNow;
            fps = Math.round(1000f / deltaTime);
        }
    }

}
