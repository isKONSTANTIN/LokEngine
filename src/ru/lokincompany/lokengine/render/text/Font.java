package ru.lokincompany.lokengine.render.text;

import org.lwjgl.opengl.GL11;
import ru.lokincompany.lokengine.loaders.TextureLoader;
import ru.lokincompany.lokengine.render.Texture;
import ru.lokincompany.lokengine.tools.color.Color;
import ru.lokincompany.lokengine.tools.color.Colors;
import ru.lokincompany.lokengine.tools.vectori.Vector2i;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class Font {

    private HashMap<Character, Glyph> glyphs;
    private Texture texture;
    private int fontHeight;
    private float spaceSize;

    public Font(Texture texture, HashMap<Character, Glyph> glyphs, int fontHeight) {
        this.texture = texture;
        this.glyphs = glyphs;
        this.fontHeight = fontHeight;
    }

    public HashMap<Character, Glyph> getGlyphs() {
        return glyphs;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public float getSpaceSize(){
        return spaceSize;
    }

    public Vector2i getSize(String text, Vector2i maxSize){
        Vector2i result = new Vector2i();

        int drawX = 0;
        int drawY = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                drawY += fontHeight;
                result.y = Math.max(drawY, result.y);
                drawX = 0;
                continue;
            }
            if (ch == '\r') continue;

            Glyph g = glyphs.get(ch);

            if (g == null) {
                drawX += spaceSize;
                result.x = Math.max(drawX, result.x);
                continue;
            }

            if (maxSize != null) {
                if (maxSize.x > 0 && drawX + g.width > maxSize.x) {
                    if (maxSize.y > 0 && drawY + fontHeight + g.height > maxSize.y)
                        break;
                    drawX = 0;
                    drawY += fontHeight;
                }
            }

            int width = drawX + g.width;
            int height = drawY + g.height;

            result.x = Math.max(width, result.x);
            result.y = Math.max(height, result.y);

            spaceSize += g.width;
            spaceSize /= 2f;
            drawX += g.width;
        }

        return result;
    }

    public void drawText(String text, Vector2i position, Vector2i maxSize, TextColorShader shader) {
        int drawX = position.x;
        int drawY = position.y;

        GL11.glBindTexture(GL_TEXTURE_2D, texture.buffer);
        glBegin(GL_QUADS);

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                drawY += fontHeight;
                drawX = position.x;
                continue;
            }
            if (ch == '\r') continue;

            Glyph g = glyphs.get(ch);

            if (g == null) {
                drawX += spaceSize;
                continue;
            }

            if (maxSize != null) {
                if (maxSize.x > 0 && drawX + g.width > maxSize.x + position.x) {
                    if (maxSize.y > 0 && drawY + fontHeight + g.height > maxSize.y + position.y)
                        break;
                    drawX = position.x;
                    drawY += fontHeight;
                }
            }

            int width = drawX + g.width;
            int height = drawY + g.height;

            float glTexX = g.x / (float) texture.sizeX;
            float glTexY = g.y / (float) texture.sizeY;
            float glTexWidth = (g.x + g.width) / (float) texture.sizeX;
            float glTexHeight = (g.y + g.height) / (float) texture.sizeY;

            Color color = shader.getColor(new Vector2i(drawX - position.x, drawY - position.y));
            glColor4d(color.red, color.green, color.blue, color.alpha);

            glTexCoord2f(glTexX, glTexHeight);
            glVertex3f(drawX, drawY, 0);

            glTexCoord2f(glTexWidth, glTexHeight);
            glVertex3f(width, drawY, 0);

            glTexCoord2f(glTexWidth, glTexY);
            glVertex3f(width, height, 0);

            glTexCoord2f(glTexX, glTexY);
            glVertex3f(drawX, height, 0);

            spaceSize += g.width;
            spaceSize /= 2f;
            drawX += g.width;
        }
        glEnd();
        GL11.glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void drawText(String text, Vector2i position, Vector2i maxSize, Color color) {
        drawText(text, position, maxSize, charPos -> color);
    }

    public void drawText(String text, Vector2i position, Vector2i maxSize) {
        drawText(text, position, maxSize, Colors.white());
    }

    public void drawText(String text, Vector2i position, Color color) {
        drawText(text, position, null, color);
    }

    public void drawText(String text, Vector2i position) {
        drawText(text, position, Colors.white());
    }

    public void dispose() {
        TextureLoader.unloadTexture(texture);
    }
}