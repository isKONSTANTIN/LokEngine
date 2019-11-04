package ru.lokinCompany.LokEngine.Tools.Text;


import ru.lokinCompany.LokEngine.Loaders.TextureLoader;
import ru.lokinCompany.LokEngine.Render.Texture;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Color;
import ru.lokinCompany.LokEngine.Tools.Utilities.Color.Colors;
import ru.lokinCompany.LokEngine.Tools.Utilities.Vector2i;
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

    public void drawText(String text, int x, int y, TextColorShader shader) {
        int textHeight = getHeight(text);

        int drawX = x;
        int drawY = y;

        if (textHeight > fontHeight) {
            drawY += textHeight - fontHeight;
        }

        GL11.glBindTexture(GL_TEXTURE_2D, texture.buffer);
        glBegin(GL_QUADS);

        Color lastColor = Colors.black();

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

            int width = drawX + g.width;
            int height = drawY + g.height;

            float glTexX = g.x / (float) texture.sizeX;
            float glTexY = g.y / (float) texture.sizeY;
            float glTexWidth = (g.x + g.width) / (float) texture.sizeX;
            float glTexHeight = (g.y + g.height) / (float) texture.sizeY;

            Color color = shader.getColor(new Vector2i(drawX - x, drawY - y));
            glColor4d(color.red, color.green, color.blue, color.alpha);

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

    public void drawText(String text, int x, int y, Color color) {
        drawText(text, x, y, charPos -> color);
    }

    public void drawText(String text, int x, int y) {
        drawText(text, x, y, new Color(1, 1, 1, 1));
    }

    public void dispose() {
        TextureLoader.unloadTexture(texture);
    }
}