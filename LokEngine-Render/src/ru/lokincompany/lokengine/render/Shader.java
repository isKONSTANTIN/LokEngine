package ru.lokincompany.lokengine.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.util.vector.*;
import ru.lokincompany.lokengine.tools.Base64;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.MatrixTools;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;
import ru.lokincompany.lokengine.tools.vectori.Vector3i;
import ru.lokincompany.lokengine.tools.vectori.Vector4i;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader implements Saveable {

    public int program;
    public String vertPath;
    public String fragPath;

    HashMap<String, Integer> uniformsName = new HashMap<>();

    public Shader() {
    }

    public Shader(String vertPath, String fragPath) {
        try {
            loadShader(vertPath, fragPath);
        } catch (Throwable e) {
            Logger.warning("Fail load shader!", "LokEngine_Shader");
            Logger.printThrowable(e);
        }
    }

    public abstract void update(Camera activeCamera);

    public void setView(Camera camera){
        setUniformData("View", MatrixTools.createViewMatrix(camera));
    }

    public void setProjection(float width, float height, float projectionFieldOfView){
       setUniformData("Projection", MatrixTools.createOrthoMatrix(width * projectionFieldOfView, height * projectionFieldOfView));
    }

    public void setProjection(float width, float height, Camera activeCamera){
        setProjection(width, height, activeCamera.fieldOfView * 0.002f);
    }

    public boolean equals(Object obj) {
        Shader objs = (Shader) obj;
        return objs.program == program;
    }

    protected int getUniformLocationID(String name) {
        if (!uniformsName.containsKey(name)) {
            int id = glGetUniformLocation(program, name);
            uniformsName.put(name, id);
            return id;
        } else {
            return uniformsName.get(name);
        }
    }

    public void setUniformData(String uniformName, int data) {
        glUniform1i(getUniformLocationID(uniformName), data);
    }

    public void setUniformData(String uniformName, Vector2i data) {
        glUniform2i(getUniformLocationID(uniformName), data.x, data.y);
    }

    public void setUniformData(String uniformName, Vector3i data) {
        glUniform3i(getUniformLocationID(uniformName), data.x, data.y, data.z);
    }

    public void setUniformData(String uniformName, Vector4i data) {
        glUniform4i(getUniformLocationID(uniformName), data.x, data.y, data.z, data.w);
    }

    public void setUniformData(String uniformName, float data) {
        glUniform1f(getUniformLocationID(uniformName), data);
    }

    public void setUniformData(String uniformName, Vector2f data) {
        glUniform2f(getUniformLocationID(uniformName), data.x, data.y);
    }

    public void setUniformData(String uniformName, Vector3f data) {
        glUniform3f(getUniformLocationID(uniformName), data.x, data.y, data.z);
    }

    public void setUniformData(String uniformName, Vector4f data) {
        glUniform4f(getUniformLocationID(uniformName), data.x, data.y, data.z, data.w);
    }

    public void setUniformData(String uniformName, Matrix2f data) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(8);
        data.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix2fv(getUniformLocationID(uniformName), false, matrixBuffer);
    }

    public void setUniformData(String uniformName, Matrix3f data) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(12);
        data.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix3fv(getUniformLocationID(uniformName), false, matrixBuffer);
    }

    public void setUniformData(String uniformName, Matrix4f data) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        data.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4fv(getUniformLocationID(uniformName), false, matrixBuffer);
    }

    private String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        InputStream in;

        if (filename.charAt(0) == '#') {
            in = Shader.class.getResourceAsStream(filename.substring(1));
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

    private int loadPartShader(String filename, int shaderType) throws Exception {
        int shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

        if (shader == 0)
            return 0;

        ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
        ARBShaderObjects.glCompileShaderARB(shader);

        if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE)
            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

        return shader;
    }

    private void loadShader(String vertPath, String fragPath) throws Exception {
        int vertShader = loadPartShader(vertPath, ARBVertexShader.GL_VERTEX_SHADER_ARB);
        int fragShader = loadPartShader(fragPath, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        int program = ARBShaderObjects.glCreateProgramObjectARB();

        ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);

        ARBShaderObjects.glLinkProgramARB(program);
        ARBShaderObjects.glValidateProgramARB(program);

        this.program = program;
        this.vertPath = vertPath;
        this.fragPath = fragPath;
    }

    @Override
    public String save() {
        return Base64.toBase64(vertPath + "\n" + fragPath);
    }

    @Override
    public Saveable load(String savedString) {
        String[] patches = Base64.fromBase64(savedString).split("\n");

        try {
            loadShader(patches[0], patches[1]);
        } catch (Throwable e) {
            Logger.warning("Fail load shader from save!", "LokEngine_Shader");
            Logger.printThrowable(e);
        }
        return this;
    }
}
