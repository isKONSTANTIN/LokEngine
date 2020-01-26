package ru.lokincompany.lokengine.tools.saveworker;

public interface Saveable {
    String save();

    Saveable load(String savedString);
}
