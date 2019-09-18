package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Color;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class GUIGraphFramePart extends FramePart {

    ArrayList<Float> points;
    public Vector2i position;
    public Vector2i size;
    public Color color;

    public float maxHeight;
    public float minHeight;
    public int maxPoints;

    public GUIGraphFramePart(Vector2i position, Vector2i size, ArrayList<Float> points, float maxHeight, float minHeight, int maxPoints, Color color) {
        super(FramePartType.GUI);
        this.points = points;
        this.position = position;
        this.size = size;
        this.color = color;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxPoints = maxPoints;
    }

    @Override
    public void partRender() {
        glBegin(GL_LINE_STRIP);
        glColor4f(color.red, color.green, color.blue, color.alpha);

        for (int i = 0; i < points.size(); i++){
            glVertex2f(
                    ((float)i / (float)maxPoints * size.x) + position.x,position.y + size.y - size.y * (points.get(i) / (maxHeight + minHeight))
            );
        }

        glEnd();
    }

}
