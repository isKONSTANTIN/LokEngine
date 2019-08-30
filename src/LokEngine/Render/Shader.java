package LokEngine.Render;

import LokEngine.Loaders.ShaderLoader;
import LokEngine.Tools.Logger;
import LokEngine.Tools.SaveWorker.Saveable;
import org.lwjgl.opengl.ARBShaderObjects;

public class Shader implements Saveable {

    public int program;
    public Shader(int program){
        this.program = program;
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
        String[] patches = ShaderLoader.getPatches(this);

        return patches[0] + "\n" + patches[1];
    }

    @Override
    public Saveable load(String savedString) {
        String[] patches = savedString.split(System.getProperty("line.separator"));

        try {
            this.program = ShaderLoader.loadShader(patches[0],patches[1]).program;
        } catch (Exception e) {
            Logger.warning("Fail load shader from save!","LokEngine_Shader");
            Logger.printException(e);
        }
        return this;
    }
}
