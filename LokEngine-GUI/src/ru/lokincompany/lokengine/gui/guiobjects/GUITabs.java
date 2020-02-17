package ru.lokincompany.lokengine.gui.guiobjects;

import ru.lokincompany.lokengine.gui.additionalobjects.GUILocationAlgorithm;
import ru.lokincompany.lokengine.gui.additionalobjects.GUIObjectProperties;
import ru.lokincompany.lokengine.gui.canvases.GUICanvas;
import ru.lokincompany.lokengine.render.frame.PartsBuilder;
import ru.lokincompany.lokengine.render.text.Font;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUITabs extends GUIObject {
    protected GUIPanel panel;
    protected GUICanvas activeCanvas;
    protected GUIFreeTextDrawer drawer;

    protected Color tabTextActiveColor;
    protected Color tabTextInactiveColor;

    protected Map<String, GUICanvas> tabs = new HashMap<>();
    protected ArrayList<String> tabsNames = new ArrayList<>();

    protected GUILocationAlgorithm sizeAlgorithm;
    protected GUILocationAlgorithm positionAlgorithm;

    public GUITabs(FontPrefs titleFontPrefs) {
        super(new Vector2i(), new Vector2i(300, 300));

        this.drawer = new GUIFreeTextDrawer(titleFontPrefs);
        this.panel = new GUIPanel()
                .setPosition(object -> getPosition())
                .setSize(object -> new Vector2i(getSize().x, this.getTabsFont().getFontHeight()));

        this.sizeAlgorithm = object -> new Vector2i(getSize().x, getSize().y - this.getTabsFont().getFontHeight());
        this.positionAlgorithm = object -> new Vector2i(getPosition().x, getPosition().y + this.getTabsFont().getFontHeight());

        this.tabTextActiveColor = Colors.engineMainColor();
        this.tabTextInactiveColor = Colors.white();
    }

    public GUITabs() {
        this(FontPrefs.defaultFontPrefs);
    }

    public GUIPanel getGUIPanel() {
        return panel;
    }

    public GUICanvas getActiveTab() {
        return activeCanvas;
    }

    public GUITabs setActiveTab(String name) {
        activeCanvas = getTab(name);
        return this;
    }

    public Font getTabsFont() {
        return drawer.getFont();
    }

    public GUITabs setTabsFont(Font font) {
        drawer.setFont(font);
        return this;
    }

    public String getActiveTabName() {
        for (Map.Entry<String, GUICanvas> entry : tabs.entrySet()) {
            GUICanvas canvas = entry.getValue();
            if (canvas == activeCanvas)
                return entry.getKey();
        }
        return null;
    }

    public GUITabs removeTab(String name) {
        tabs.remove(name);
        return this;
    }

    public GUITabs addTab(String name) {
        GUICanvas canvas = new GUICanvas(new Vector2i(), size);

        canvas.setSize(sizeAlgorithm);
        canvas.setPosition(positionAlgorithm);

        tabs.put(name, canvas);
        tabsNames.add(name);

        return this;
    }

    public GUICanvas getTab(String name) {
        return tabs.get(name);
    }

    @Override
    public GUITabs setPosition(Vector2i position) {
        super.setPosition(position);
        return this;
    }

    @Override
    public GUITabs setSize(Vector2i size) {
        super.setSize(size);
        return this;
    }

    @Override
    public void update(PartsBuilder partsBuilder, GUIObjectProperties parentProperties) {
        super.update(partsBuilder, parentProperties);

        int x = 0;
        int gap = (int) (drawer.getFont().getSpaceSize() * 1.5f);

        for (String key : tabsNames) {
            int widthText = drawer.getFont().getSize(key, null).x;

            if (x + widthText > size.x) break;
            boolean inField = properties.mouseRaycastStatus.mouse.inField(new Vector2i(properties.globalPosition.x + x, properties.globalPosition.y), new Vector2i(widthText, drawer.getFont().getFontHeight()));

            if (inField && properties.mouseRaycastStatus.mouse.getPressedStatus() && !properties.mouseRaycastStatus.lastFramePressed) {
                setActiveTab(key);
            }
            drawer.draw(key, new Vector2i(x + position.x, position.y), activeCanvas == tabs.get(key) || inField ? tabTextActiveColor : tabTextInactiveColor);
            x += widthText + gap;
        }
        if (panel != null)
            panel.update(partsBuilder, parentProperties);

        drawer.update(partsBuilder, parentProperties);
        if (activeCanvas != null)
            activeCanvas.update(partsBuilder, parentProperties);
    }
}
