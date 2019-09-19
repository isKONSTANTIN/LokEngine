package LokEngine.Loaders;

import LokEngine.Render.Texture;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class TextureLoader {

    private static HashMap<String, Texture> loadedTextures = new HashMap<>();

    public static void unloadTexture(Texture texture){
        glDeleteTextures(texture.buffer);
        texture = null;
    }

    public static Texture loadTexture(String path) {
        if (loadedTextures.containsKey(path)){
            return loadedTextures.get(path);
        }

        BufferedImage image;
        try {
            if (path.charAt(0) == '#'){
                image = ImageIO.read(TextureLoader.class.getResource(path.substring(1)));
            }else{
                image = ImageIO.read(new File(path));
            }
        }catch (Exception e){
            return new Texture(0,0, 0, path);
        }


        int texture_size = image.getWidth() * image.getHeight() * 4;
        int[] pixels = new int[image.getWidth() * image.getHeight()];

        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,image.getWidth());
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

        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer);
        glBindTexture(GL_TEXTURE_2D, 0);

        Texture texture = new Texture(textureID,image.getWidth(), image.getHeight(), path);

        loadedTextures.put(path,texture);

        return texture;
    }

}
