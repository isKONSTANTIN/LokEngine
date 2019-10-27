package LokEngine.Loaders;

import LokEngine.Render.Texture;
import LokEngine.Tools.Text.Font;
import LokEngine.Tools.Text.Glyph;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class FontLoader {

    private static HashMap<Long, HashMap<java.awt.Font, Font>> createdFonts = new HashMap<>();

    public static Font createFont(java.awt.Font font, boolean antiAlias, String additionalSymbols) {
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        String symbols = russianAlphabet.toUpperCase() + russianAlphabet + additionalSymbols;

        return createBasicFont(font, antiAlias, symbols);
    }

    public static Font createFont(java.awt.Font font, boolean antiAlias) {
        return createFont(font, antiAlias, "");
    }

    public static Font createBasicFont(java.awt.Font font, boolean antiAlias, String symbols) {
        long context = glfwGetCurrentContext();

        if (!createdFonts.containsKey(context)) {
            createdFonts.put(context, new HashMap<>());
        }

        if (createdFonts.get(context).containsKey(font)) {
            return createdFonts.get(context).get(font);
        }

        HashMap<Character, Glyph> glyphs = new HashMap<>();
        HashMap<Character, BufferedImage> bufferedImages = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            stringBuilder.append((char) i);
        }

        symbols = stringBuilder.append(symbols).toString();

        char[] chars = symbols.toCharArray();

        int imageWidth = 0;
        int imageHeight = 0;

        for (char c : chars) {
            if (c == 127) {
                continue;
            }
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                continue;
            }

            bufferedImages.put(c, ch);
            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int x = 0;

        for (char c : chars) {
            if (c == 127) {
                continue;
            }
            if (!bufferedImages.containsKey(c)) continue;

            BufferedImage charImage = bufferedImages.get(c);
            int charHeight = charImage.getHeight();

            Glyph glyph = new Glyph(charImage.getWidth(), charHeight, x, image.getHeight() - charHeight, 0f);
            g.drawImage(charImage, x, 0, null);
            x += glyph.width;
            glyphs.put(c, glyph);
        }

        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i * width + j];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);

        Font loadedFont = new Font(new Texture(textureID, width, height, null), glyphs, imageHeight);
        createdFonts.get(context).put(font, loadedFont);

        return loadedFont;
    }

    private static BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        if (antiAlias)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        if (charWidth == 0) {
            return null;
        }

        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();

        return image;
    }

}
