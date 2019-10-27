package LokEngine.Tools.Text;


import LokEngine.Loaders.TextureLoader;
import LokEngine.Render.Texture;
import LokEngine.Tools.Utilities.Color.Color;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class Font {

    private HashMap<Character, Glyph> glyphs;
    private Texture texture;
    private int fontHeight;

    public HashMap<Character, Glyph> getGlyphs() {
        return glyphs;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public Font(Texture texture, HashMap<Character, Glyph> glyphs, int fontHeight) {
        this.texture = texture;
        this.glyphs = glyphs;
        this.fontHeight = fontHeight;
    }

    public int getWidth(CharSequence text) {
        int width = 0;
        int lineWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Glyph g = glyphs.get(c);
            if (g == null) continue;
            if (c == '\r') continue;

            if (c == '\n') {
                width = Math.max(width, lineWidth);
                lineWidth = 0;
                continue;
            }

            lineWidth += g.width;
        }
        width = Math.max(width, lineWidth);
        return width;
    }

    public int getHeight(CharSequence text) {
        int height = 0;
        int lineHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Glyph g = glyphs.get(c);
            if (g == null) continue;
            if (c == '\r') continue;
            if (c == '\n') {
                height += lineHeight;
                lineHeight = 0;
                continue;
            }

            lineHeight = Math.max(lineHeight, g.height);
        }
        height += lineHeight;
        return height;
    }

    public void drawText(String text, int x, int y, Color color) {
        int textHeight = getHeight(text);

        float drawX = x;
        float drawY = y;

        if (textHeight > fontHeight) {
            drawY += textHeight - fontHeight;
        }

        GL11.glBindTexture(GL_TEXTURE_2D, texture.buffer);
        glBegin(GL_QUADS);
        glColor4d(color.red, color.green, color.blue, color.alpha);

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                drawY += fontHeight;
                drawX = x;
                continue;
            }
            if (ch == '\r') continue;

            Glyph g = glyphs.get(ch);

            if (g == null) continue;

            float width = drawX + g.width;
            float height = drawY + g.height;

            float glTexX = g.x / (float) texture.sizeX;
            float glTexY = g.y / (float) texture.sizeY;
            float glTexWidth = (g.x + g.width) / (float) texture.sizeX;
            float glTexHeight = (g.y + g.height) / (float) texture.sizeY;

            glTexCoord2f(glTexX, glTexHeight);
            glVertex3f(drawX, drawY, 0);

            glTexCoord2f(glTexWidth, glTexHeight);
            glVertex3f(width, drawY, 0);

            glTexCoord2f(glTexWidth, glTexY);
            glVertex3f(width, height, 0);

            glTexCoord2f(glTexX, glTexY);
            glVertex3f(drawX, height, 0);

            drawX += g.width;
        }
        glEnd();
        GL11.glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void drawText(String text, int x, int y) {
        drawText(text, x, y, new Color(1, 1, 1, 1));
    }

    public void dispose() {
        TextureLoader.unloadTexture(texture);
    }
}