package com.unciv.app.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.unciv.ui.components.FontFamilyData;
import com.unciv.ui.components.FontImplementation;
import com.unciv.ui.components.Fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.unciv.utils.StreamUtil.distinctByKey;

public class DesktopFont implements FontImplementation {

    private Font font;
    private FontMetrics metric;

    @Override
    public void setFontFamily(FontFamilyData fontFamilyData, int size) {
        // Mod font
        if (fontFamilyData.getFilePath() != null) {
            this.font = createFontFromFile(fontFamilyData.getFilePath(), size);
        }
        // System font
        else {
            this.font = new Font(fontFamilyData.getInvariantName(), Font.PLAIN, size);
        }

        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        this.metric = graphics.getFontMetrics(font);
        graphics.dispose();
    }

    private Font createFontFromFile(String path, int size) {
        Font font;

        try {
            // Try to create and register new font
            File fontFile = Gdx.files.local(path).file();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(size);
            ge.registerFont(font);
        } catch (Exception ex) {
            // Fallback to default, if failed.
            font = new Font(Fonts.DEFAULT_FONT_FAMILY, Font.PLAIN, size);
        }

        return font;
    }

    @Override
    public int getFontSize() {
        return font.getSize();
    }

    @Override
    public Pixmap getCharPixmap(char ch) {
        int width = metric.charWidth(ch);
        int height = metric.getAscent() + metric.getDescent();
        if (width == 0) {
            height = font.getSize();
            width = height;
        }
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(Character.toString(ch), 0, metric.getAscent());
        Pixmap pixmap = new Pixmap(bi.getWidth(), bi.getHeight(), Pixmap.Format.RGBA8888);
        int[] data = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
        for (int i = 0; i < bi.getWidth(); i ++) {
            for (int j = 0; j < bi.getHeight(); j ++) {
                pixmap.setColor(Integer.reverseBytes(data[i + (j * bi.getWidth())]));
                pixmap.drawPixel(i, j);
            }
        }
        g.dispose();
        return pixmap;
    }

    @Override
    public List<FontFamilyData> getSystemFonts() {
        String cjkLanguage = " CJK " + System.getProperty("user.language").toUpperCase();

        return Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
            .filter(e -> !e.getFontName().contains(" CJK ") || e.getFontName().contains(cjkLanguage))
            .map(e -> new FontFamilyData(e.getFamily(), e.getFamily(Locale.ROOT)))
            .filter(distinctByKey(FontFamilyData::getInvariantName))
            .collect(Collectors.toList());
    }
}
