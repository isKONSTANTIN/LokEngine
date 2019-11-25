package ru.lokinCompany.lokEngine.Tests;

import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.Components.SpriteComponent;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Workers.BloomActionWorker;
import ru.lokinCompany.lokEngine.Render.Frame.FrameParts.PostProcessing.Workers.BloomSettings;
import ru.lokinCompany.lokEngine.SceneEnvironment.SceneObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.BlurTuning;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;

public class BloomTest extends ApplicationDefault {

    public static void main(String[] args) { new BloomTest().start(false,true); }

    @Override
    protected void initEvent() {
        window.setCloseEvent((window, args) -> close());
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();

        SceneObject sceneObject = new SceneObject();
        sceneObject.components.add(new SpriteComponent("#/resources/textures/EngineIcon128.png"));
        scene.addObject(sceneObject);

        BloomSettings settings = new BloomSettings(new BlurTuning(0.6,50,0.15),1,1,0.6f);
        ((BloomActionWorker)window.getFrameBuilder().getPostProcessingActionWorker("Bloom Action Worker")).setBloomSettings(settings);
    }

}
