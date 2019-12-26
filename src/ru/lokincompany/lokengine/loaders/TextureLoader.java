package ru.lokincompany.lokengine.loaders;

import org.lwjgl.BufferUtils;
import ru.lokincompany.lokengine.render.GLFW;
import ru.lokincompany.lokengine.render.Texture;
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

public class TextureLoader {

    private static HashMap<Long, HashMap<String, Texture>> loadedTextures = new HashMap<>();

    public static void unloadTexture(Texture texture) {
        if (!GLFW.isInited()) return;
        long context = glfwGetCurrentContext();

        loadedTextures.get(context).remove(texture.path);

        glDeleteTextures(texture.buffer);
    }

    public static Object[] loadTextureInBuffer(String path) throws IOException {
        if (!GLFW.isInited()) return null;

        BufferedImage image;

        if (path.charAt(0) == '#') {
            image = ImageIO.read(TextureLoader.class.getResource(path.substring(1)));
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

    public static Texture loadTexture(ByteBuffer buffer, Vector2i size, String path, long context) {
        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);

        Texture texture = new Texture(textureID, size.x, size.y, path);

        return texture;
    }

    public static Texture loadTexture(ByteBuffer buffer, Vector2i size) {
        return loadTexture(buffer, size, "", glfwGetCurrentContext());
    }

    public static Texture loadTexture(String path) {
        if (!GLFW.isInited()) return new Texture(-1, 0, 0, path);
        long context = glfwGetCurrentContext();

        if (!loadedTextures.containsKey(context)) {
            loadedTextures.put(context, new HashMap<>());
        }

        if (loadedTextures.get(context).containsKey(path)) {
            return loadedTextures.get(context).get(path);
        }

        ByteBuffer textureBuffer;
        BufferedImage image;

        try {
            Object[] imageData = loadTextureInBuffer(path);

            textureBuffer = (ByteBuffer) imageData[0];
            image = (BufferedImage) imageData[1];

        } catch (Exception e) {
            return new Texture(-1, 100, 100, path);
        }

        Texture texture = loadTexture(textureBuffer, new Vector2i(image.getWidth(), image.getHeight()), path, context);

        loadedTextures.get(context).put(path, texture);

        return texture;
    }

}