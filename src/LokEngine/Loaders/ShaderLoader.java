package LokEngine.Loaders;

import LokEngine.Render.Shader;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL11.GL_FALSE;

public class ShaderLoader {

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    static String readFileAsString(String filename) throws Exception {

        StringBuilder source = new StringBuilder();
        InputStream in;

        if (filename.charAt(0) == '#') {
            in = ShaderLoader.class.getResourceAsStream(filename.substring(1));
        } else {
            in = new FileInputStream(filename);
        }

        Exception exception = null;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Exception innerExc = null;
            try {
                String line;
                while ((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            } catch (Exception exc) {
                exception = exc;
            } finally {
                try {
                    reader.close();
                } catch (Exception exc) {
                    if (innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }

            if (innerExc != null)
                throw innerExc;
        } catch (Exception exc) {
            exception = exc;
        } finally {
            try {
                in.close();
            } catch (Exception exc) {
                if (exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }

            if (exception != null)
                throw exception;
        }

        return source.toString();
    }

    private static int loadPartShader(String filename, int shaderType) throws Exception {
        int shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

        if (shader == 0)
            return 0;

        ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
        ARBShaderObjects.glCompileShaderARB(shader);

        if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE)
            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

        return shader;
    }

    public static Shader loadShader(String vertPath, String fragPath) throws Exception {

        String patches = vertPath + ":" + fragPath;

        int vertShader = loadPartShader(vertPath, ARBVertexShader.GL_VERTEX_SHADER_ARB);
        int fragShader = loadPartShader(fragPath, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        int program = ARBShaderObjects.glCreateProgramObjectARB();

        ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);

        ARBShaderObjects.glLinkProgramARB(program);
        ARBShaderObjects.glValidateProgramARB(program);

        Shader newShader = new Shader(program, vertPath, fragPath);

        return newShader;
    }
}
