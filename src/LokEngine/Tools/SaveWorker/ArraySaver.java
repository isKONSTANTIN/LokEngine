package LokEngine.Tools.SaveWorker;

import LokEngine.Tools.Logger;
import LokEngine.Tools.Misc;

import java.util.ArrayList;

public class ArraySaver implements Saveable {

    public ArrayList<Saveable> arrayList = new ArrayList<>();
    public Class typeClass;

    public ArraySaver(){
        typeClass = this.getClass();
    }

    public ArraySaver(Class typeClass){
        this.typeClass = typeClass;
    }

    @Override
    public String save() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(typeClass.getName());

        for (int i = 0; i < arrayList.size(); i++){
            stringBuilder.append("\n");
            Saveable saveable = arrayList.get(i);
            stringBuilder
                    .append("ELM.")
                    .append(i)
                    .append("\n")
                    .append(saveable.save())
                    .append("\nEND.")
                    .append(i);
        }

        return Misc.toBase64(stringBuilder.toString());
    }

    @Override
    public Saveable load(String savedString) {
        String[] elements = Misc.fromBase64(savedString).split("ELM.");
        try {
            typeClass = Class.forName(elements[0].substring(0,elements[0].length()-1));
        } catch (ClassNotFoundException e) {
            Logger.warning("Fail load type class!", "LokEngine_ArraySaver");
            Logger.printException(e);
            return this;
        }
        for (int i = 1; i < elements.length; i++){
            String[] lines = elements[i].split(System.getProperty("line.separator"));
            if (lines[0].equals(lines[lines.length-1].substring(4))){
                StringBuilder elementSaved = new StringBuilder();

                for (int lineIndex = 1; lineIndex < lines.length - 1; lineIndex++){
                    if (lineIndex != 1) elementSaved.append("\n");
                    elementSaved.append(lines[lineIndex]);
                }

                try {
                    arrayList.add(((Saveable)typeClass.newInstance()).load(elementSaved.toString()));
                } catch (InstantiationException | IllegalAccessException e) {
                    Logger.warning("Fail add loaded object!", "LokEngine_ArraySaver");
                    Logger.printException(e);
                }
            }
        }

        return this;
    }
}
