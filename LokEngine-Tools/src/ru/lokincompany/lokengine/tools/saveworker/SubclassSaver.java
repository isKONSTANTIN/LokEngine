package ru.lokincompany.lokengine.tools.saveworker;

public class SubclassSaver implements Saveable {

    public Saveable saveableObject;
    String className;

    public SubclassSaver() {
    }

    public SubclassSaver(Saveable saveableObject) {
        this.saveableObject = saveableObject;
        className = saveableObject.getClass().getName();
    }

    @Override
    public String save() {
        return className + ":" + saveableObject.save();
    }

    @Override
    public Saveable load(String savedString) {
        String[] data = savedString.split(":");

        try {
            saveableObject = ((Saveable) Class.forName(data[0]).newInstance());

            if (data.length > 1) {
                saveableObject.load(data[1]);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }
}
