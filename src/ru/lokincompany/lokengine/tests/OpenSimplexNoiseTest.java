package ru.lokincompany.lokengine.tests;

import org.lwjgl.BufferUtils;
import ru.lokincompany.lokengine.applications.applications.ApplicationGUIOnly;
import ru.lokincompany.lokengine.gui.guiobjects.GUIImage;
import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.tools.opensimplexnoise.OpenSimplexNoise2D;
import ru.lokincompany.lokengine.tools.utilities.Vector2i;

import java.nio.ByteBuffer;

public class OpenSimplexNoiseTest extends ApplicationGUIOnly {

    public static void main(String[] args) {
        new OpenSimplexNoiseTest();
    }

    @Override
    protected void initEvent(){

        OpenSimplexNoise2D noise = new OpenSimplexNoise2D("LokEngine");

        Vector2i imageSize = window.getResolution();

        byte[] pixels = new byte[imageSize.x * imageSize.y];

        for (int y = 0; y < imageSize.y; y++) {
            for (int x = 0; x < imageSize.x; x++) {
                double n = noise.get(x / 50d, y / 50d);
                pixels[y * imageSize.x + x] = (byte)(((n + 1d) / 2d) * 255d);
            }
        }
        int texture_size = imageSize.x * imageSize.y * 4;

        ByteBuffer textureBuffer = BufferUtils.createByteBuffer(texture_size);

        for (int y = 0; y < imageSize.y; y++) {
            for (int x = 0; x < imageSize.x; x++) {
                int pixel = pixels[y * imageSize.x + x];
                textureBuffer.put((byte) pixel);
                textureBuffer.put((byte) pixel);
                textureBuffer.put((byte) pixel);
                textureBuffer.put((byte) 255);
            }
        }
        textureBuffer.flip();

        GUIImage image = new GUIImage(new Vector2i(), imageSize);
        image.setTexture(TextureLoader.loadTexture(textureBuffer, imageSize));
        window.getCanvas().addObject(image);
    }

    OpenSimplexNoiseTest(){
        start();
    }

}
