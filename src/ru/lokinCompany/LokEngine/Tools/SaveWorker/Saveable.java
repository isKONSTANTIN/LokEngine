package ru.lokinCompany.LokEngine.Tools.SaveWorker;

public interface Saveable {
    String save();

    Saveable load(String savedString);
}
