package ru.lokincompany.lokengine.gui.additionalobjects;

import ru.lokincompany.lokengine.gui.guiobjects.GUIObject;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

public interface GUILocationAlgorithm {
    Vector2i calculate(GUIObject object);
}
