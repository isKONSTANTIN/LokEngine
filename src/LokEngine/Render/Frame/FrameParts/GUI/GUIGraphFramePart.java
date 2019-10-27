package LokEngine.Render.Frame.FrameParts.GUI;

import LokEngine.GUI.GUIObjects.GUIFreeTextDrawer;
import LokEngine.Render.Enums.FramePartType;
import LokEngine.Render.Frame.BuilderProperties;
import LokEngine.Render.Frame.FramePart;
import LokEngine.Tools.Utilities.Color.Color;
import LokEngine.Tools.Utilities.Vector2i;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class GUIGraphFramePart extends FramePart {

    ArrayList<Float> points;
    GUIFreeTextDrawer freeTextDrawer;
    public Vector2i position;
    public Vector2i size;
    public Color color;
    public Color color2;

    public float maxHeight;
    public float minHeight;
    public int maxPoints;

    public GUIGraphFramePart(Vector2i position, Vector2i size, ArrayList<Float> points, float maxHeight, float minHeight, int maxPoints, Color color, Color color2, GUIFreeTextDrawer freeTextDrawer) {
        super(FramePartType.GUI);
        this.points = points;
        this.position = position;
        this.size = size;
        this.color = color;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxPoints = maxPoints;
        this.freeTextDrawer = freeTextDrawer;
        this.color2 = color2;
    }

    @Override
    public void partRender(BuilderProperties builderProperties) {

        glBegin(GL_LINES);
        glColor4f(color2.red, color2.green, color2.blue, color2.alpha);

        for (int i = 0; i <= size.y; i += size.y / 5){
            Vector2i pos = new Vector2i(position.x, position.y + (size.y - i));
            freeTextDrawer.draw(String.valueOf(Math.round(i * (maxHeight / size.y))), new Vector2i(pos.x,pos.y), color2);
            glVertex2f(pos.x, pos.y);
            glVertex2f(pos.x + size.x, pos.y);
        }

        glEnd();

        glBegin(GL_LINE_STRIP);
        glColor4f(color.red, color.green, color.blue, color.alpha);

        for (int i = 0; i < points.size(); i++) {
            glVertex2f(
                    ((float) i / (float) maxPoints * size.x) + position.x, position.y + size.y - size.y * (points.get(i) / (maxHeight + minHeight))
            );
        }

        glEnd();
    }

}
