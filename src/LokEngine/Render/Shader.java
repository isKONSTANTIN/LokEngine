package LokEngine.Render;

import LokEngine.Loaders.ShaderLoader;
import LokEngine.Tools.Logger;
import LokEngine.Tools.Misc;
import LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.opengl.ARBShaderObjects;

public class Shader implements Saveable {

    public int program;
    public String vertPath;
    public String fragPath;

    public Shader(int program, String vertPath, String fragPath){
        this.program = program;
        this.vertPath = vertPath;
        this.fragPath = fragPath;
    }

    public static Shader currentShader;

    public Shader(){ }

    public boolean equals(Object obj){
        Shader objs = (Shader)obj;
        return objs.program == program;
    }

    public static void use(Shader shader){
        ARBShaderObjects.glUseProgramObjectARB(shader.program);
        currentShader = shader;
    }

    public static void unUse(){
        ARBShaderObjects.glUseProgramObjectARB(0);
        currentShader = null;
    }

    @Override
    public String save() {
        return Misc.toBase64(vertPath + "\n" + fragPath);
    }

    @Override
    public Saveable load(String savedString) {
        String[] patches = Misc.fromBase64(savedString).split(System.getProperty("line.separator"));

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
