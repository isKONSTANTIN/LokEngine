package LokEngine.Render.Frame.FrameParts.PostProcessing.Actions;

import LokEngine.Tools.Utilities.BlurTuning;
import LokEngine.Tools.Utilities.Vector2i;

import static org.lwjgl.opengl.GL11.*;

public class BlurAction extends PostProcessingAction {

    public BlurTuning blurTuning;

    public BlurAction(Vector2i position, Vector2i size) {
        this(position, size, new BlurTuning());

    }

    public BlurAction(Vector2i position, Vector2i size, BlurTuning blurTuning) {
        super(position, size);
        this.blurTuning = blurTuning;
    }

    @Override
    public void apply() {
        glBegin(GL_POLYGON);

        glColor4d(blurTuning.strength / 10d, blurTuning.samples / 1000d, blurTuning.bokeh, 1);
        glVertex3f(position.x, position.y, 0);
        glVertex3f(size.x + position.x, position.y, 0);
        glVertex3f(size.x + position.x, size.y + position.y, 0);
        glVertex3f(position.x, size.y + position.y, 0);

        glEnd();
    }
}
