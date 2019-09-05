package LokEngine.Tools.Scripting;

import LokEngine.Tools.SaveWorker.AutoSaveable;

public interface Script extends AutoSaveable {
    void execute();
}
