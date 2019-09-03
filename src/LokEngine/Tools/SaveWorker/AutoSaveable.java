package LokEngine.Tools.SaveWorker;

import LokEngine.Tools.Logger;
import LokEngine.Tools.Misc;

import java.io.*;
import java.lang.reflect.Field;

public interface AutoSaveable extends Serializable, Saveable {

    default String save() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();

            return Misc.bytesToBase64(baos.toByteArray());
        } catch (IOException e) {
            Logger.warning("Fail auto save object!", "LokEngine_AutoSaveable");
            Logger.printException(e);
        }
        return null;
    }

    default Saveable load(String savedString) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(Misc.bytesFromBase64(savedString)));

            Object loadedObject = ois.readObject();
            ois.close();

            Field[] loadedObjectFields = loadedObject.getClass().getDeclaredFields();

            for (Field loadedField : loadedObjectFields){
                loadedField.setAccessible(true);
                loadedField.set(this, loadedField.get(loadedObject));
                loadedField.setAccessible(false);
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            Logger.warning("Fail auto load object!", "LokEngine_AutoSaveable");
            Logger.printException(e);
        }

        return this;
    }
}
