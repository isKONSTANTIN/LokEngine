package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.text.Font;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUIMenu extends GUIObject {
    protected Map<String, GUIObject> items = new HashMap<>();
    protected ArrayList<String> itemsNames = new ArrayList<>();
    protected GUILocationAlgorithm sizeAlgorithm;
    protected GUILocationAlgorithm positionAlgorithm;
    protected GUIObject activeItem;
    protected GUIFreeTextDrawer drawer;

    protected int titleSize;

    public GUIPanel panel;
    public Color textActiveColor;
    public Color textInactiveColor;

    public GUIMenu(Vector2i position, Vector2i size, int titleSize, Color textActiveColor, Color textInactiveColor) {
        super(position, size);
        this.titleSize = titleSize;
        this.drawer = new GUIFreeTextDrawer("", 0, titleSize, true);
        this.panel = new GUIPanel(position, new Vector2i(size.x, titleSize));
        panel.setSize(object -> new Vector2i(getSize().x, this.titleSize));
        panel.setPosition(object -> getPosition());
        this.sizeAlgorithm = object -> new Vector2i(getSize().x, getSize().y - this.titleSize);
        this.positionAlgorithm = object -> new Vector2i(getPosition().x, getPosition().y + this.titleSize);

        this.textActiveColor = textActiveColor;
        this.textInactiveColor = textInactiveColor;
        this.touchable = true;
    }

    public GUIMenu(Vector2i position, Vector2i size, int titleSize) {
        this(position, size, titleSize, Colors.engineMainColor(), Colors.white());
    }

    public GUIMenu(Vector2i position, Vector2i size) {
        this(position, size, 12, Colors.engineMainColor(), Colors.white());
    }

    public void showItem(String name, Vector2i position) {
        if (activeItem != null)
            hideActiveItem();
        activeItem = getItem(name);
        activeItem.hidden = false;
        activeItem.active = true;
        if (position != null)
            activeItem.setPosition(position);
    }

    public void showItem(String name) {
        showItem(name, null);
    }

    public void hideActiveItem() {
        if (activeItem != null){
            activeItem.hidden = true;
            activeItem = null;
        }
    }

    public GUIObject getActiveItem() {
        return activeItem;
    }

    public void setPointsFont(Font font) {
        if (font != null) {
            drawer.setFont(font);

            int fontH = font.getFontHeight();

            if (titleSize < fontH) {
                titleSize = fontH;
            }
        }
    }

    public Font getPointsFont() {
        return drawer.getFont();
    }

    public String getActiveItemName() {
        for (Map.Entry<String, GUIObject> entry : items.entrySet()) {
            GUIObject item = entry.getValue();
            if (item == activeItem)
                return entry.getKey();
        }
        return null;
    }

    public void removePoint(String name) {
        items.remove(name);
    }

    public void addPoint(String name, GUIObject object) {
        items.put(name, object);
        itemsNames.add(name);
        object.hidden = true;
    }

    public GUIObject getItem(String name) {
        return items.get(name);
    }

    @Override
    public void setPosition(Vector2i position) {
        super.setPosition(position);
    }

    @Override
    public void setSize(Vector2i size) {
        super.setSize(size);
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (activeItem != null && !activeItem.active){
            hideActiveItem();
        }

        int x = 0;
        int gap = titleSize / 2;

        for (String key : itemsNames) {
            int widthText = drawer.getFont().getWidth(key);

            if (x + widthText > size.x) break;
            Vector2i itemPos = new Vector2i(properties.globalPosition.x + x, properties.globalPosition.y);
            boolean inField = properties.mouseRaycastStatus.mouse.inField(itemPos, new Vector2i(widthText, titleSize));

            if (inField && properties.mouseRaycastStatus.mouse.getPressedStatus() && !properties.mouseRaycastStatus.lastFramePressed) {
                showItem(key, properties.mouseRaycastStatus.mouse.getMousePosition());
            }
            drawer.draw(key, new Vector2i(x + position.x, position.y), activeItem == items.get(key) || inField ? textActiveColor : textInactiveColor);
            x += widthText + gap;
        }
        if (panel != null)
            panel.update(partsBuilder, parentProperties);

        drawer.update(partsBuilder, parentProperties);
    }
}
