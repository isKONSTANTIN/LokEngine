package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

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
            titlePanel.setSize(object -> new Vector2i(object.getSize().x, titleText.getSize().y));
            titleText.setPosition(titlePanel.position);
            titleText.setMaxSize(size);
            canvasSize.y = size.y - titleText.getSize().y;
            canvasPosition.y += titleText.getSize().y;
        }
        canvas = new GUICanvas(canvasPosition, canvasSize);
        canvas.setPosition(object -> new Vector2i(this.getPosition().x, this.getPosition().y + (titlePanel != null ? titlePanel.size.y : 0)));

        this.canMove = canMove;
    }

    public GUISubWindow(Vector2i position, Vector2i size, boolean canMove) {
        this(position, size, canMove,  new GUIText("window!"), new GUIPanel());
    }

    public GUISubWindow(Vector2i position, Vector2i size, GUIText titleText, GUIPanel titlePanel) {
        this(position, size, true, titleText, titlePanel);
    }

    public GUISubWindow(Vector2i position, Vector2i size) {
        this(position, size, true);
    }

    public GUISubWindow(boolean canMove, GUIText titleText, GUIPanel titlePanel) {
        this(new Vector2i(), new Vector2i(200,200), canMove, titleText, titlePanel);
    }

    public GUISubWindow(boolean canMove) {
        this(canMove, new GUIText("window!"), new GUIPanel());
    }

    public GUISubWindow(GUIText titleText, GUIPanel titlePanel) {
        this(true, titleText, titlePanel);
    }

    public GUISubWindow() {
        this(new Vector2i(), new Vector2i(200,200));
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
        canvas.setSize(size);

        if (titleText != null) {
            titlePanel.setSize(size);
            titleText.setMaxSize(size);
        }
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);

        if (titleText != null) {
            titlePanel.setPosition(position);
            titleText.setPosition(titlePanel.position);
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
