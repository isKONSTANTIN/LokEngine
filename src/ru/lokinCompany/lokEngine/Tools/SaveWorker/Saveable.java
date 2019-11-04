package ru.lokinCompany.lokEngine.Tools.SaveWorker;

public interface Saveable {
    String save();

    Saveable load(String savedString);
}
