package LokEngine.Tools;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.Render.Frame.FrameBuilder;
import LokEngine.SceneEnvironment.Scene;
import LokEngine.Tools.Utilities.MouseStatus;
import org.lwjgl.input.Mouse;

public class RuntimeFields {
    private static FrameBuilder frameBuilder;
    private static Scene scene;
    private static GUICanvas canvas;
    private static MouseStatus mouseStatus;
    private static float deltaTime;
    private static int fps;

    private static float speedEngine = 1f;
    private static long lastUpdateTime;
    private static long lastFPSUpdateTime;

    public static FrameBuilder getFrameBuilder(){ return frameBuilder; }
    public static Scene getScene(){ return scene; }
    public static GUICanvas getCanvas(){ return canvas; }
    public static MouseStatus getMouseStatus(){ return mouseStatus; }
    public static float getDeltaTime(){ return deltaTime / 16.66666f; }
    public static int getFps(){ return fps; }

    public static float getSpeedEngine(){ return speedEngine; }

    public static void setSpeedEngine(float speed){
        if (speed >= 0){
            speedEngine = speed;
        }else{
            throw new IllegalArgumentException("Speed cannot be less than zero!");
        }
    }

    public static void init(FrameBuilder frameBuilder, Scene scene, GUICanvas canvas, MouseStatus mouseStatus){
        RuntimeFields.frameBuilder = frameBuilder;
        RuntimeFields.scene = scene;
        RuntimeFields.canvas = canvas;
        RuntimeFields.mouseStatus = mouseStatus;
    }

    public static void update(){
        if (mouseStatus != null){
            mouseStatus.mousePosition.x = Mouse.getX();
            mouseStatus.mousePosition.y = Mouse.getY();
            mouseStatus.mousePressed = Mouse.isButtonDown(0);
        }

        long timeNow = System.nanoTime();

        deltaTime = (timeNow - lastUpdateTime) / 1000000f;
        lastUpdateTime = timeNow;

        if (timeNow - lastFPSUpdateTime >= 1000000000){
            lastFPSUpdateTime = timeNow;
            fps = Math.round(1000f / deltaTime);
        }
    }

}
