package ru.lokinCompany.lokEngine.GUI.AdditionalObjects;

import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIObject;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public interface GUILocationAlgorithm {
    Vector2i calculate(GUIObject object);
}
