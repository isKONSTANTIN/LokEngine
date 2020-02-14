package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUIMenu extends GUIObject {
    public GUIPanel panel;
    protected Color textActiveColor;
    protected Color textInactiveColor;
    protected Map<String, GUIObject> items = new HashMap<>();
    protected ArrayList<String> itemsNames = new ArrayList<>();
    protected GUILocationAlgorithm sizeAlgorithm;
    protected GUILocationAlgorithm positionAlgorithm;
    protected GUIObject activeItem;
    protected GUIFreeTextDrawer drawer;

    public GUIMenu(FontPrefs fontPrefs) {
        this.drawer = new GUIFreeTextDrawer(fontPrefs);
        setPanel(new GUIPanel());

        this.sizeAlgorithm = object -> new Vector2i(getSize().x, getSize().y - (int)(this.drawer.getFont().getFontHeight() * 1.2f));
        this.positionAlgorithm = object -> new Vector2i(getPosition().x, getPosition().y + (int)(this.drawer.getFont().getFontHeight() * 1.2f));

        this.textActiveColor = Colors.engineMainColor();
        this.textInactiveColor = Colors.white();

        this.touchable = true;
    }

    public GUIMenu showItem(String name, Vector2i position) {
        if (activeItem != null)
            hideActiveItem();
        activeItem = getItem(name);
        activeItem.hidden = false;
        activeItem.active = true;
        if (position != null)
            activeItem.setPosition(position);
        return this;
    }

    public GUIMenu showItem(String name) {
        showItem(name, null);
        return this;
    }

    public GUIMenu hideActiveItem() {
        if (activeItem != null) {
            activeItem.hidden = true;
            activeItem = null;
        }
        return this;
    }

    public GUIObject getActiveItem() {
        return activeItem;
    }

    public Font getPointsFont() {
        return drawer.getFont();
    }

    public GUIMenu setPointsFont(Font font) {
        drawer.setFont(font);
        return this;
    }

    public String getActiveItemName() {
        for (Map.Entry<String, GUIObject> entry : items.entrySet()) {
            GUIObject item = entry.getValue();
            if (item == activeItem)
                return entry.getKey();
        }
        return null;
    }

    public GUIMenu removePoint(String name) {
        items.remove(name);
        return this;
    }

    public GUIMenu addPoint(String name, GUIObject object) {
        items.put(name, object);
        itemsNames.add(name);
        object.hidden = true;

        return this;
    }

    public GUIObject getItem(String name) {
        return items.get(name);
    }

    public GUIMenu setTextActiveColor(Color textActiveColor) {
        this.textActiveColor = textActiveColor;
        return this;
    }

    public GUIMenu setTextInactiveColor(Color textInactiveColor) {
        this.textInactiveColor = textInactiveColor;
        return this;
    }

    public GUIPanel getPanel() {
        return panel;
    }

    public void setPanel(GUIPanel panel) {
        this.panel = panel
                .setPosition(object -> getPosition())
                .setSize(object -> new Vector2i(getSize().x, (int)(this.drawer.getFont().getFontHeight() * 1.2f)));
    }

    public Color getTextActiveColor() {
        return textActiveColor;
    }

    public Color getTextInactiveColor() {
        return textInactiveColor;
    }

    public GUIFreeTextDrawer getDrawer() {
        return drawer;
    }

    @Override
    public GUIMenu setPosition(Vector2i position) {
        super.setPosition(position);
        return this;
    }

    @Override
    public GUIMenu setSize(Vector2i size) {
        super.setSize(size);
        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        if (activeItem != null && !activeItem.active) {
            hideActiveItem();
        }

        int gap = panel.size.y / 2;
        int x = gap / 2;

        for (String key : itemsNames) {
            Vector2i textSize = drawer.getFont().getSize(key, null);

            int widthText = textSize.x;
            int heightText = textSize.y;

            if (x + widthText > size.x) break;
            Vector2i itemPos = new Vector2i(properties.globalPosition.x + x, properties.globalPosition.y);
            boolean inField = properties.mouseRaycastStatus.mouse.inField(itemPos, new Vector2i(widthText, panel.size.y));

            if (inField && properties.mouseRaycastStatus.mouse.getPressedStatus() && !properties.mouseRaycastStatus.lastFramePressed)
                showItem(key, properties.mouseRaycastStatus.mouse.getMousePosition());
            else
                properties.mouseRaycastStatus.touched = false;
            drawer.draw(key, new Vector2i(x + position.x, position.y + (panel.size.y - heightText) / 2), activeItem == items.get(key) || inField ? textActiveColor : textInactiveColor);
            x += widthText + gap;
        }
        if (panel != null)
            panel.update(partsBuilder, parentProperties);

        drawer.update(partsBuilder, parentProperties);
    }
}
