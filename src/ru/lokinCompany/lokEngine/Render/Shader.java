package ru.lokinCompany.lokEngine.Render;

import ru.lokinCompany.lokEngine.Loaders.ShaderLoader;
import ru.lokinCompany.lokEngine.Tools.Base64.Base64;
import ru.lokinCompany.lokEngine.Tools.Logger;
import ru.lokinCompany.lokEngine.Tools.SaveWorker.Saveable;

public class Shader implements Saveable {

    public int program;
    public String vertPath;
    public String fragPath;

    public Shader() {
    }

    public Shader(int program, String vertPath, String fragPath) {
        this.program = program;
        this.vertPath = vertPath;
        this.fragPath = fragPath;
    }

    public boolean equals(Object obj) {
        Shader objs = (Shader) obj;
        return objs.program == program;
    }

    @Override
    public String save() {
        return Base64.toBase64(vertPath + "\n" + fragPath);
    }

    @Override
    public Saveable load(String savedString) {
        String[] patches = Base64.fromBase64(savedString).split("\n");

        try {
            Shader loadedShader = ShaderLoader.loadShader(patches[0], patches[1]);
            this.program = loadedShader.program;
            this.vertPath = loadedShader.vertPath;
            this.fragPath = loadedShader.fragPath;
        } catch (Exception e) {
            Logger.warning("Fail load shader from save!", "LokEngine_Shader");
            Logger.printException(e);
        }
        return this;
    }
}
