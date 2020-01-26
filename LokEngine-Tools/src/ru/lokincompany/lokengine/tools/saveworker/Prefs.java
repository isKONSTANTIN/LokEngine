package ru.lokincompany.lokengine.tools.saveworker;

import ru.lokincompany.lokengine.tools.Logger;

import java.io.IOException;
import java.util.HashMap;

public class Prefs {

    public static String filePath = "Prefs.save";
    private static HashMap<String, String> data = new HashMap<>();

    public static synchronized void save() {
        if (data.size() > 0) {
            try {
                FileWorker fileWorker = new FileWorker(filePath);
                fileWorker.openWrite();

                StringBuilder stringBuilder = new StringBuilder();

                for (HashMap.Entry<String, String> entry : data.entrySet()) {
                    stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
                }

                if (stringBuilder.length() > 0)
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                fileWorker.write(stringBuilder.toString());

                fileWorker.close();
            } catch (IOException e) {
                Logger.warning("Fail save prefs fields!", "LokEngine_Prefs");
                Logger.printException(e);
            }
        }
    }

    public static synchronized void writeField(String nameField, String dataField) {
        data.put(nameField, dataField);
    }

    public static synchronized String getField(String nameField) {
        if (data.containsKey(nameField)) {
            return data.get(nameField);
        }

        return null;
    }

    public static synchronized String getField(String nameField, String defautValue) {
        String fieldData = getField(nameField);
        return fieldData != null ? fieldData : defautValue;
    }

    public static synchronized void init() {
        try {
            FileWorker fileWorker = new FileWorker(filePath);
            if (fileWorker.file.exists()) {
                fileWorker.openRead();
                String[] lines = fileWorker.read().split("\n");

                for (String line : lines) {
                    String[] dataLine = line.split(":");
                    if (dataLine.length > 1)
                        data.put(dataLine[0], dataLine[1]);
                }
            }
            fileWorker.close();
        } catch (IOException e) {
            Logger.warning("Fail init prefs fields!", "LokEngine_Prefs");
            Logger.printException(e);
        }
    }


}
