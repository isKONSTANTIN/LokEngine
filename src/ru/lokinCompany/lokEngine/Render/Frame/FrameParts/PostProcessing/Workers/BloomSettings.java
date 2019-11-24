package ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Workers;

import org.lwjgl.opengl.GL11;
import ru.lokinCompany.lokEngine.Render.Enums.DrawMode;
import ru.lokinCompany.lokEngine.Render.Frame.FrameBufferWorker;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Actions.BlurAction;
import ru.lokinCompany.lokEngine.Render.Window.Window;
import ru.lokinCompany.lokEngine.Tools.Utilities.BlurTuning;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class BloomSettings {

    BlurTuning blurTuning;
    FrameBufferWorker frameBufferWorker;

    public float gamma;
    public float exposure;
    public float brightnessLimit;

    public BloomSettings(BlurTuning blurTuning, float gamma, float exposure, float brightnessLimit){
       this.blurTuning = blurTuning;
        this.gamma = gamma;
        this.exposure = exposure;
        this.brightnessLimit = brightnessLimit;
    }

    public BloomSettings(BlurTuning blurTuning){ this(blurTuning, 1, 1, 0.9f); }

    public BloomSettings(){ this(new BlurTuning(0.2,10,0), 1, 1, 0.9f); }
    int getBlurTexture(Window window){
        if (frameBufferWorker == null)
            frameBufferWorker = new FrameBufferWorker(window.getResolution());
        frameBufferWorker.bindFrameBuffer(DrawMode.RawGUI,window.getFrameBuilder().getBuilderProperties());
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        new BlurAction(new Vector2i(), frameBufferWorker.getResolution(), blurTuning).apply();

        frameBufferWorker.unbindCurrentFrameBuffer();
        return frameBufferWorker.getTexture();
    }

}
