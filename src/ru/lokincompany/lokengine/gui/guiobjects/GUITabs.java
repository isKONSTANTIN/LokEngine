package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.tools.text.Font;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Color;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUITabs extends GUIObject {
    protected Map<String, GUICanvas> tabs = new HashMap<>();
    protected ArrayList<String> tabsNames = new ArrayList<>();
    protected GUILocationAlgorithm sizeAlgorithm;
    protected GUILocationAlgorithm positionAlgorithm;
    protected GUICanvas activeCanvas;
    protected GUIFreeTextDrawer drawer;

    protected int titleSize;

    public GUIPanel panel;
    public Color textActiveColor;
    public Color textInactiveColor;

    public GUITabs(Vector2i position, Vector2i size, int titleSize, Color textActiveColor, Color textInactiveColor) {
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
    }

    public GUITabs(Vector2i position, Vector2i size, int titleSize) {
        this(position, size, titleSize, Colors.engineMainColor(), Colors.white());
    }

    public GUITabs(Vector2i position, Vector2i size) {
        this(position, size, 12, Colors.engineMainColor(), Colors.white());
    }

    public GUITabs(int titleSize, Color textActiveColor, Color textInactiveColor) {
        this(new Vector2i(), new Vector2i(), titleSize, textActiveColor, textInactiveColor);
    }

    public GUITabs(int titleSize) {
        this(new Vector2i(), new Vector2i(), titleSize, Colors.engineMainColor(), Colors.white());
    }

    public GUITabs() {
        this(new Vector2i(), new Vector2i(), 12, Colors.engineMainColor(), Colors.white());
    }

    public void setActiveTab(String name) {
        activeCanvas = getTab(name);
    }

    public GUICanvas getActiveTab() {
        return activeCanvas;
    }

    public void setTabsFont(Font font) {
        if (font != null) {
            drawer.setFont(font);

            int fontH = font.getFontHeight();

            if (titleSize < fontH) {
                titleSize = fontH;
            }
        }
    }

    public Font getTabsFont() {
        return drawer.getFont();
    }

    public String getActiveTabName() {
        for (Map.Entry<String, GUICanvas> entry : tabs.entrySet()) {
            GUICanvas canvas = entry.getValue();
            if (canvas == activeCanvas)
                return entry.getKey();
        }
        return null;
    }

    public void removeTab(String name) {
        tabs.remove(name);
    }

    public void addTab(String name) {
        GUICanvas canvas = new GUICanvas(new Vector2i(), size);

        canvas.setSize(sizeAlgorithm);
        canvas.setPosition(positionAlgorithm);

        tabs.put(name, canvas);
        tabsNames.add(name);
    }

    public GUICanvas getTab(String name) {
        return tabs.get(name);
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

        int x = 0;
        int gap = titleSize / 2;

        for (String key : tabsNames) {
            int widthText = drawer.getFont().getWidth(key);

            if (x + widthText > size.x) break;
            boolean inField = properties.mouseRaycastStatus.mouse.inField(new Vector2i(properties.globalPosition.x + x, properties.globalPosition.y), new Vector2i(widthText, titleSize));

            if (inField && properties.mouseRaycastStatus.mouse.getPressedStatus() && !properties.mouseRaycastStatus.lastFramePressed) {
                setActiveTab(key);
            }
            drawer.draw(key, new Vector2i(x + position.x, position.y), activeCanvas == tabs.get(key) || inField ? textActiveColor : textInactiveColor);
            x += widthText + gap;
        }
        if (panel != null)
            panel.update(partsBuilder, parentProperties);

        drawer.update(partsBuilder, parentProperties);
        if (activeCanvas != null)
            activeCanvas.update(partsBuilder, properties);
    }
}
