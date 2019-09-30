package LokEngine.Render;

import LokEngine.Loaders.ShaderLoader;
import LokEngine.Tools.Base64.Base64;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Saveable;

public class Shader implements Saveable {

    public int program;
    public String vertPath;
    public String fragPath;

    public Shader(){}

    public Shader(int program, String vertPath, String fragPath){
        this.program = program;
        this.vertPath = vertPath;
        this.fragPath = fragPath;
    }

    public boolean equals(Object obj){
        Shader objs = (Shader)obj;
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
            Logger.warning("Fail load shader from save!","LokEngine_Shader");
            Logger.printException(e);
        }
        return this;
    }
}
