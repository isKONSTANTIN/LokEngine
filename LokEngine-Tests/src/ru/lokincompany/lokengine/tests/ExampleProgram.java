package ru.lokincompany.lokengine.tests;

import ru.lokincompany.lokengine.applications.ApplicationGUIOnly;
import ru.lokincompany.lokengine.gui.additionalobjects.guipositions.GUIPosition;
import ru.lokincompany.lokengine.gui.guiobjects.GUIText;
import ru.lokincompany.lokengine.tools.FontPrefs;
import ru.lokincompany.lokengine.tools.TextColorShader;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;

public class ExampleProgram extends ApplicationGUIOnly {
    public static void main(String[] args) {
        new ExampleProgram().start(false, true);
    }

    @Override
    protected void initEvent() {
        window.setCloseEvent((window, args) -> close());
        window.getFrameBuilder().backgroundColor = Colors.engineBackgroundColor();

        TextColorShader textColorShader = charPos ->
                new Color(1,1,1, (float)(Math.sin(applicationRuntime.getEngineRunTime() / 1000000000f - charPos.x / 100f) + 1) / 2f);

        canvas.addObject(
                new GUIText(new FontPrefs().setShader(textColorShader)).setText("It's simple program on LokEngine!"),
                GUIPosition.Center
        );
    }
}
