package ru.lokincompany.lokengine.tests;

import ru.lokincompany.lokengine.applications.ApplicationDefault;
import ru.lokincompany.lokengine.render.postprocessing.actions.blur.BlurTuning;
import ru.lokincompany.lokengine.render.postprocessing.workers.bloom.BloomActionWorker;
import ru.lokincompany.lokengine.render.postprocessing.workers.bloom.BloomSettings;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.SceneObject;
import ru.lokincompany.lokengine.sceneenvironment.defaultenvironment.components.SpriteComponent;
import ru.lokincompany.lokengine.tools.color.Colors;

public class BloomTest extends ApplicationDefault {

    public static void main(String[] args) {
        new BloomTest().start(false, true, true, 16);
    }

    @Override
    protected void initEvent() {
        window.setCloseEvent((window, args) -> close());
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();

        SceneObject sceneObject = new SceneObject();
        sceneObject.components.add(new SpriteComponent("#/resources/textures/EngineIcon128.png"));
        scene.addObject(sceneObject);

        BloomSettings settings = new BloomSettings(new BlurTuning(0.6, 50, 0.15), 0.6f);
        window.getFrameBuilder().getPostProcessingActionWorker(BloomActionWorker.class).setBloomSettings(settings);
    }

}
