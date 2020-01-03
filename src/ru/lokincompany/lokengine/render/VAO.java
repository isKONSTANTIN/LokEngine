package ru.lokincompany.lokengine.render;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VAO {
    int vaoID;

    public VAO(){
        vaoID = glGenVertexArrays();
    }

    public void bind(){
        glBindVertexArray(vaoID);
    }

    public void unbind(){
        glBindVertexArray(0);
    }

    public int getID(){
        return vaoID;
    }

}
