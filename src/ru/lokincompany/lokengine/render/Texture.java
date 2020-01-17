package ru.lokincompany.lokengine.render;

import org.lwjgl.BufferUtils;
import ru.lokincompany.lokengine.tools.Logger;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture implements Saveable {
    private static HashMap<Long, HashMap<String, Texture>> loadedTextures = new HashMap<>();
    public int buffer = -1;
    public int sizeX = -1;
    public int sizeY = -1;
    public long context = -1;
    public String path;

    public Texture(ByteBuffer byteBuffer, Vector2i size) {
        this.buffer = createBuffer(byteBuffer, size.x, size.y);
        this.sizeX = size.x;
        this.sizeY = size.y;
    }

    public Texture(String path) {
        loadFromPath(path);
    }

    public Texture() {
    }

    public static Object[] loadData(String path) throws IOException {
        BufferedImage image;

        if (path.charAt(0) == '#') {
            image = ImageIO.read(Texture.class.getResource(path.substring(1)));
        } else {
            image = ImageIO.read(new File(path));
        }

        int texture_size = image.getWidth() * image.getHeight() * 4;
        int[] pixels = new int[image.getWidth() * image.getHeight()];

        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer textureBuffer = BufferUtils.createByteBuffer(texture_size);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                textureBuffer.put((byte) ((pixel >> 16) & 0xFF));
                textureBuffer.put((byte) ((pixel >> 8) & 0xFF));
                textureBuffer.put((byte) (pixel & 0xFF));
                textureBuffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        textureBuffer.flip();

        return new Object[]{textureBuffer, image};
    }

    public boolean equals(Object obj) {
        return ((Texture) obj).buffer == buffer;
    }

    public void unload() {
        loadedTextures.get(context).remove(path);
        glDeleteTextures(buffer);
    }

    private int createBuffer(ByteBuffer buffer, int x, int y) {
        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x, y, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureID;
    }

    private void loadFromPath(String path) {
        this.context = glfwGetCurrentContext();
        this.path = path;

        ByteBuffer textureBuffer;
        BufferedImage image;

        if (!loadedTextures.containsKey(context))
            loadedTextures.put(context, new HashMap<>());

        if (loadedTextures.get(context).containsKey(path)) {
            Texture loadedTexture = loadedTextures.get(context).get(path);

            this.buffer = loadedTexture.buffer;
            this.sizeX = loadedTexture.sizeX;
            this.sizeY = loadedTexture.sizeY;

            return;
        }

        try {
            Object[] imageData = loadData(path);

            textureBuffer = (ByteBuffer) imageData[0];
            image = (BufferedImage) imageData[1];
        } catch (Exception e) {
            Logger.warning("Fail load texture: " + path + "!", "LokEngine_Texture");
            Logger.printException(e);
            return;
        }

        this.buffer = createBuffer(textureBuffer, image.getWidth(), image.getHeight());
        this.sizeX = image.getWidth();
        this.sizeY = image.getHeight();

        loadedTextures.get(context).put(path, this);
    }

    @Override
    public String save() {
        return path;
    }

    @Override
    public Saveable load(String savedString) {
        loadFromPath(savedString);
        return this;
    }
}
