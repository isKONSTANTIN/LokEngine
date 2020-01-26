package ru.lokincompany.lokengine.render.postprocessing.workers.bloom;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.render.enums.DrawMode;
import ru.lokincompany.lokengine.render.frame.FrameBufferWorker;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurAction;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurTuning;
import ru.lokincompany.lokengine.render.window.Window;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class BloomSettings {

    public float brightnessLimit;
    BlurTuning blurTuning;
    FrameBufferWorker frameBufferWorker;

    public BloomSettings(BlurTuning blurTuning, float brightnessLimit) {
        this.blurTuning = blurTuning;
        this.brightnessLimit = brightnessLimit;
    }

    public BloomSettings(BlurTuning blurTuning) {
        this(blurTuning, 0.9f);
    }

    public BloomSettings() {
        this(new BlurTuning(0.2, 10, 0), 0.9f);
    }

    int getBlurTexture(Window window) {
        if (frameBufferWorker == null)
            frameBufferWorker = new FrameBufferWorker(window.getResolution());
        frameBufferWorker.bindFrameBuffer(DrawMode.RawGUI, window.getFrameBuilder().getRenderProperties());
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        new BlurAction(new Vector2i(), frameBufferWorker.getResolution(), blurTuning).apply();

        frameBufferWorker.unbindCurrentFrameBuffer();
        return frameBufferWorker.getTextureBuffer();
    }

}
