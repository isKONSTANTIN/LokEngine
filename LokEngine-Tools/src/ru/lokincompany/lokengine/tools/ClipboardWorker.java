package ru.lokincompany.lokengine.tools;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ClipboardWorker implements ClipboardOwner {
    private static ClipboardWorker owner = new ClipboardWorker();

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable transferable) {

    }

    public static void setClipboardContents(String string){
        StringSelection stringSelection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, owner);
    }

    public static String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException | IOException ex){
                Logger.warning("Fail to get clipboard content", "ClipboardWorker");
                Logger.printThrowable(ex);
            }
        }
        return result;
    }
}
