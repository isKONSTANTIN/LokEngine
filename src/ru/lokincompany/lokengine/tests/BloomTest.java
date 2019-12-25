package ru.lokincompany.lokengine.tests;

import ru.lokincompany.lokengine.applications.applications.ApplicationDefault;
import ru.lokincompany.lokengine.components.SpriteComponent;
import ru.lokincompany.lokengine.render.postprocessing.workers.BloomActionWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.BloomSettings;
import ru.lokincompany.lokengine.sceneenvironment.SceneObject;
import ru.lokincompany.lokengine.tools.utilities.BlurTuning;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

public class BloomTest extends ApplicationDefault {

    public static void main(String[] args) {
        new BloomTest().start(false, true);
    }

    @Override
    protected void initEvent() {
        window.setCloseEvent((window, args) -> close());
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();

        SceneObject sceneObject = new SceneObject();
        sceneObject.components.add(new SpriteComponent("#/resources/textures/EngineIcon128.png"));
        scene.addObject(sceneObject);

        BloomSettings settings = new BloomSettings(new BlurTuning(0.6, 50, 0.15), 1, 1, 0.6f);
        window.getFrameBuilder().getPostProcessingActionWorker(BloomActionWorker.class).setBloomSettings(settings);
    }

}
