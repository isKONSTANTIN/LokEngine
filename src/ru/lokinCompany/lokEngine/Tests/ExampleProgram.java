package ru.lokinCompany.lokEngine.Tests;

import ru.lokinCompany.lokEngine.Applications.ApplicationDefault;
import ru.lokinCompany.lokEngine.GUI.AdditionalObjects.GUIPositions.GUIPosition;
import ru.lokinCompany.lokEngine.GUI.GUIObjects.GUIText;
import ru.lokinCompany.lokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.lokEngine.Tools.Utilities.Vector2i;

public class ExampleProgram extends ApplicationDefault {
    public static void main(String[] args) { new ExampleProgram().start(false,true); }

    @Override
    protected void initEvent() {
        window.setCloseEvent((window, args) -> close());
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();
        window.getCanvas().addObject(new GUIText(new Vector2i(),"It's simple program on LokEngine!"), GUIPosition.Center);
    }
}
