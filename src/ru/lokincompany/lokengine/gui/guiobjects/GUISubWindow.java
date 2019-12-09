package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public class GUISubWindow extends GUIObject {

    public GUICanvas canvas;
    public GUIText titleText;
    public boolean canMove;
    protected GUIPanel titlePanel;
    protected Vector2i lastMousePos;
    private boolean lastMFS;

    public GUISubWindow(Vector2i position, Vector2i size, boolean canMove, GUIText titleText, GUIPanel titlePanel) {
        super(position, size);
        Vector2i canvasSize = new Vector2i(size.x, size.y);
        Vector2i canvasPosition = new Vector2i(position.x, position.y);

        if (titleText != null) {
            this.titleText = titleText;
            this.titlePanel = titlePanel;

            titlePanel.setPosition(new Vector2i(position.x, position.y));
            titlePanel.setSize(new Vector2i(size.x, titleText.getSize().y));
            titleText.setPosition(titlePanel.position);
            canvasSize.y = size.y - titleText.getSize().y;
            canvasPosition.y += titleText.getSize().y;
        }
        canvas = new GUICanvas(canvasPosition, canvasSize);
        this.canMove = canMove;
    }

    public GUISubWindow(Vector2i position, Vector2i size, boolean canMove) {
        this(position, size, canMove, null, null);
    }

    public GUISubWindow(Vector2i position, Vector2i size, GUIText titleText, GUIPanel titlePanel) {
        this(position, size, false, titleText, titlePanel);
    }

    public GUISubWindow(Vector2i position, Vector2i size) {
        this(position, size, false);
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        canvas.setSize(size);
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);

        if (titleText != null) {
            titlePanel.setPosition(position);
            titleText.setPosition(titlePanel.position);
            canvas.setPosition(new Vector2i(position.x, position.y + titlePanel.size.y));
        } else {
            canvas.setPosition(position);
        }

    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        Vector2i mousePos = parentProperties.window.getMouse().getMousePosition();

        if (canMove && parentProperties.window.getMouse().getPressedStatus() && lastMFS) {
            setPosition(new Vector2i(position.x + (mousePos.x - lastMousePos.x), position.y + (mousePos.y - lastMousePos.y)));
        }

        super.update(partsBuilder, parentProperties);
        lastMFS = parentProperties.window.getMouse().inField(properties.globalPosition, canMove && titleText != null ? new Vector2i(size.x, titlePanel.size.y) : new Vector2i(size.x, 5));

        if (canMove)
            lastMousePos = mousePos;

        if (titleText != null) {
            titlePanel.update(partsBuilder, parentProperties);
            titleText.update(partsBuilder, properties);
        }

        canvas.update(partsBuilder, parentProperties);
    }
}
