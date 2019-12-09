package ru.lokincompany.lokengine.tests;

import ru.lokincompany.lokengine.applications.applications.ApplicationDefault;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;
import ru.lokincompany.lokengine.tools.utilities.color.Colors;

public class ExampleProgram extends ApplicationDefault {
    public static void main(String[] args) { new ExampleProgram().start(false,true); }

    @Override
    protected void initEvent() {
        window.setCloseEvent((window, args) -> close());
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();
        window.getCanvas().addObject(new GUIText(new Vector2i(),"It's simple program on LokEngine!"), GUIPosition.Center);
    }
}
