package ru.lokincompany.lokengine.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.lokincompany.lokengine.tools.executorservices.EngineExecutors;
import ru.lokincompany.lokengine.tools.saveworker.Saveable;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.Future;

public class Texture implements Saveable {
    private static HashMap<Long, HashMap<String, Texture>> loadedTextures = new HashMap<>();
    public long context = -1;
    public String path;
    private int buffer = -1;
    private int sizeX = 100;
    private int sizeY = 100;
    private Future<Object[]> loadedData;
    private boolean prepared;

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
            try {
                image = ImageIO.read(Texture.class.getResource(path.substring(1)));
            }catch (Exception e){
                image = ImageIO.read(new File(path.substring(2)));
            }
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

    public int getSizeX() {
        prepareBufferData();
        return sizeX;
    }

    public int getSizeY() {
        prepareBufferData();
        return sizeY;
    }

    public int getBuffer() {
        prepareBufferData();
        return buffer;
    }

    public boolean equals(Object obj) {
        return ((Texture) obj).buffer == buffer;
    }

    public void unload() {
        loadedTextures.get(context).remove(path);
        GL11.glDeleteTextures(buffer);
    }

    private int createBuffer(ByteBuffer buffer, int x, int y) {
        int textureID = GL11.glGenTextures();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, x, y, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return textureID;
    }

    private void loadFromPath(String path) {
        this.context = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
        this.path = path;

        if (!loadedTextures.containsKey(context))
            loadedTextures.put(context, new HashMap<>());

        if (loadedTextures.get(context).containsKey(path)) {
            Texture loadedTexture = loadedTextures.get(context).get(path);

            this.buffer = loadedTexture.buffer;
            this.sizeX = loadedTexture.sizeX;
            this.sizeY = loadedTexture.sizeY;

            return;
        }

        loadedData = EngineExecutors.fastTasksExecutor.submit(() -> loadData(path));
    }

    private void prepareBufferData() {
        if (prepared) return;
        prepared = true;

        ByteBuffer textureBuffer;
        BufferedImage image;
        Object[] imageData;

        try {
            imageData = loadedData.get();
        } catch (Throwable e) {
            return;
        }

        textureBuffer = (ByteBuffer) imageData[0];
        image = (BufferedImage) imageData[1];

        this.buffer = createBuffer(textureBuffer, image.getWidth(), image.getHeight());
        this.sizeX = image.getWidth();
        this.sizeY = image.getHeight();

        loadedTextures.get(context).put(path, this);
        loadedData = null;
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