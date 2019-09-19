package LokEngine.GUI.GUIObjects;

import LokEngine.GUI.Canvases.GUICanvas;
import LokEngine.Render.Frame.PartsBuilder;
import LokEngine.Tools.Misc;
import LokEngine.Tools.RuntimeFields;
import LokEngine.Tools.Utilities.Vector2i;

public class GUISubWindow extends GUIObject {

    public GUICanvas canvas;
    public GUIText titleText;
    public boolean canMove;
    GUIPanel titlePanel;
    Vector2i lastMousePos;
    boolean lastMFS;

    public GUISubWindow(Vector2i position, Vector2i size, boolean canMove, GUIText titleText, GUIPanel titlePanel) {
        super(position, size);
        Vector2i canvasSize = new Vector2i(size.x, size.y);
        if (titleText != null){
            this.titleText = titleText;
            this.titlePanel = titlePanel;

            titlePanel.setPosition(new Vector2i(position.x,position.y - titleText.getSize().y));
            titlePanel.setSize(new Vector2i(size.x, titleText.getSize().y));
            titleText.setPosition(titlePanel.position);
            canvasSize.y = size.y - titleText.getSize().y;
        }
        canvas = new GUICanvas(position, canvasSize);
        this.canMove = canMove;
    }

    public GUISubWindow(Vector2i position, Vector2i size, boolean canMove) {
        this(position,size,canMove,null,null);
    }

    public GUISubWindow(Vector2i position, Vector2i size, GUIText titleText, GUIPanel titlePanel) {
        this(position,size,false,titleText,titlePanel);
    }

    public GUISubWindow(Vector2i position, Vector2i size) {
        this( position, size,false);
    }

    @Override
    public void setSize(Vector2i size){
        super.setSize(size);
        canvas.setSize(size);
    }

    @Override
    public void setPosition(Vector2i position){
        super.setPosition(position);

        if (titleText != null) {
            titlePanel.setPosition(new Vector2i(position.x,position.y - titleText.getSize().y));
            titleText.setPosition(titlePanel.position);
        }
        canvas.setPosition(position);
    }

    @Override
    public void update(PartsBuilder partsBuilder, Vector2i globalSourcePos){
        Vector2i mousePos = RuntimeFields.getMouseStatus().getMousePosition();

        if (canMove && RuntimeFields.getMouseStatus().getPressedStatus() && lastMFS){
            setPosition(new Vector2i(position.x + (mousePos.x - lastMousePos.x), position.y - (mousePos.y - lastMousePos.y)));
        }

        Vector2i myGlobalSourcePos = new Vector2i(globalSourcePos.x + position.x, globalSourcePos.y + position.y);

        if (canMove && titleText != null){
            lastMFS =  Misc.mouseInField(new Vector2i(myGlobalSourcePos.x, myGlobalSourcePos.y - titlePanel.size.y), new Vector2i(size.x, titlePanel.size.y));
        }else if (canMove){
            lastMFS =  Misc.mouseInField(new Vector2i(myGlobalSourcePos.x, myGlobalSourcePos.y), new Vector2i(size.x, 5));
        }

        if (canMove)
            lastMousePos = mousePos;

        canvas.update(partsBuilder, myGlobalSourcePos);
        if (titleText != null){
            titlePanel.update(partsBuilder, myGlobalSourcePos);
            titleText.update(partsBuilder, myGlobalSourcePos);
        }
    }
}
