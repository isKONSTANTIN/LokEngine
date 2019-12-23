package ru.lokincompany.lokengine.tools;

public class ApplicationRuntime {
    private float deltaTime;
    private int fps;
    private long startEngineTime;
    private float speedEngine = 1f;
    private long lastUpdateTime;
    private long lastFPSUpdateTime;

    public float getDeltaTime() {
        return deltaTime / 16.66666f;
    }

    public int getFps() {
        return fps;
    }

    public float getSpeedEngine() {
        return speedEngine;
    }

    public void setSpeedEngine(float speed) {
        if (speed >= 0) {
            speedEngine = speed;
        } else {
            throw new IllegalArgumentException("Speed cannot be less than zero!");
        }
    }

    public long getEngineRunTime() {
        return System.nanoTime() - startEngineTime;
    }

    public void init() {
        lastUpdateTime = System.nanoTime();
        lastFPSUpdateTime = System.nanoTime();
        startEngineTime = System.nanoTime();
    }

    public void update() {
        long timeNow = System.nanoTime();

        deltaTime = (timeNow - lastUpdateTime) / 1000000f;
        lastUpdateTime = timeNow;

        if (timeNow - lastFPSUpdateTime >= 1000000000) {
            lastFPSUpdateTime = timeNow;
            fps = Math.round(1000f / deltaTime);
        }
    }

}
