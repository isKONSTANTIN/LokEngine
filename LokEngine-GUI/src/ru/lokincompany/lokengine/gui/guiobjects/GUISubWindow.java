package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

public class GUISubWindow extends GUIObject {

    protected GUICanvas canvas;
    protected GUIText titleText;
    protected GUIPanel titlePanel;
    protected Vector2i lastMousePos;

    protected boolean canMove;
    private boolean lastMFS;

    public GUISubWindow(GUIText titleText, GUIPanel titlePanel) {
        super(new Vector2i(), new Vector2i(200,200));
        Vector2i canvasSize = new Vector2i(size.x, size.y);
        Vector2i canvasPosition = new Vector2i(position.x, position.y);

        if (titleText != null) {
            this.titleText = titleText;
            this.titlePanel = titlePanel;

            titlePanel.setPosition(new Vector2i(position.x, position.y));
            titlePanel.setSize(object -> new Vector2i(getSize().x, titleText.getSize().y));
            titleText.setPosition(titlePanel.position);
            titleText.setMaxSize(size);

            canvasSize.y = size.y - titleText.getSize().y;
            canvasPosition.y += titleText.getSize().y;
        }
        canvas = new GUICanvas(canvasPosition, canvasSize);
        canvas.addObject(new GUIPanel().setSize(object -> canvas.getSize()));
        canvas.setPosition(object -> new Vector2i(this.getPosition().x, this.getPosition().y + (titlePanel != null ? titlePanel.size.y : 0)));

        canMove = true;
    }

    public GUISubWindow(){
        this(new GUIText().setText("Window!"), new GUIPanel().setColor(Colors.engineBrightBackgroundColor()));
    }

    public GUICanvas getCanvas() {
        return canvas;
    }

    public GUIText getTitleText() {
        return titleText;
    }

    public GUIPanel getTitlePanel() {
        return titlePanel;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public GUISubWindow setCanMove(boolean canMove) {
        this.canMove = canMove;
        return this;
    }

    @Override
    public GUISubWindow setSize(Vector2i size) {
        super.setSize(size);
        canvas.setSize(size);

        if (titleText != null) {
            titlePanel.setSize(size);
            titleText.setMaxSize(size);
        }
        return this;
    }

    @Override
    public GUISubWindow setPosition(Vector2i position) {
        super.setPosition(position);

        if (titleText != null) {
            titlePanel.setPosition(position);
            titleText.setPosition(titlePanel.position);
        }
        return this;
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
            titleText.update(partsBuilder, parentProperties);
        }

        canvas.update(partsBuilder, parentProperties);
    }
}
