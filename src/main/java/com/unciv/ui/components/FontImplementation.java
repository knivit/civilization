package com.unciv.ui.components;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.List;

public interface FontImplementation {

    void setFontFamily(FontFamilyData fontFamilyData, int size);

    int getFontSize();

    Pixmap getCharPixmap(char ch);

    List<FontFamilyData> getSystemFonts();

    default BitmapFont getBitmapFont() {
        NativeBitmapFontData fontData = new NativeBitmapFontData(this);
        BitmapFont font = new BitmapFont(fontData, fontData.getRegions(), false);
        font.setOwnsTexture(true);
        return font;
    }
}
