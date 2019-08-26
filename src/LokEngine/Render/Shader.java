package LokEngine.Render;

import org.lwjgl.opengl.ARBShaderObjects;

public class Shader {

    public int program;
    public Shader(int program){
        this.program = program;
    }

    public static Shader currentShader;

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
}
