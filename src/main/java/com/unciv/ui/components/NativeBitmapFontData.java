package com.unciv.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.unciv.ui.images.ImageGetter;
import lombok.Getter;

public class NativeBitmapFontData extends BitmapFont.BitmapFontData implements Disposable {

    private final FontImplementation fontImplementation;

    @Getter
    private final Array<TextureRegion> regions;

    private boolean dirty;
    private final PixmapPacker packer;

    private final Texture.TextureFilter filter = Texture.TextureFilter.Linear;

    public NativeBitmapFontData(FontImplementation fontImplementation) {
        this.fontImplementation = fontImplementation;

        // set general font data
        flipped = false;
        lineHeight = (float)fontImplementation.getFontSize();
        capHeight = lineHeight;
        ascent = -lineHeight;
        down = -lineHeight;

        // Create a packer.
        int size = 1024;
        PixmapPacker.PackStrategy packStrategy = new PixmapPacker.GuillotineStrategy();
        packer = new PixmapPacker(size, size, Pixmap.Format.RGBA8888, 1, false, packStrategy);
        packer.setTransparentColor(Color.WHITE);
        packer.getTransparentColor().a = 0f;

        // Generate texture regions.
        regions = new Array<>();
        packer.updateTextureRegions(regions, filter, filter, false);

        // Set space glyph.
        BitmapFont.Glyph spaceGlyph = getGlyph(' ');
        spaceXadvance = (float)spaceGlyph.xadvance;

        setScale(Constants.defaultFontSize / Fonts.ORIGINAL_FONT_SIZE);
    }

    @Override
    public BitmapFont.Glyph getGlyph(char ch) {
        BitmapFont.Glyph glyph = super.getGlyph(ch);

        if (glyph == null) {
            Pixmap charPixmap = getPixmapFromChar(ch);

            glyph = new BitmapFont.Glyph();
            glyph.id = ch;
            glyph.width = charPixmap.getWidth();
            glyph.height = charPixmap.getHeight();
            glyph.xadvance = glyph.width;

            Rectangle rect = packer.pack(charPixmap);
            charPixmap.dispose();
            glyph.page = packer.getPages().size - 1; // Glyph is always packed into the last page for now.
            glyph.srcX = (int)rect.x;
            glyph.srcY = (int)rect.y;

            // If a page was added, create a new texture region for the incrementally added glyph.
            if (regions.size <= glyph.page) {
                packer.updateTextureRegions(regions, filter, filter, false);
            }

            setGlyphRegion(glyph, regions.get(glyph.page));
            setGlyph(ch, glyph);
            dirty = true;
        }

        return glyph;
    }

    private Pixmap getPixmap(String fileName) {
        return Fonts.extractPixmapFromTextureRegion(ImageGetter.getDrawable(fileName).getRegion());
    }

    private Pixmap getPixmapFromChar(char ch) {
        // Images must be 50*50px so they're rendered at the same height as the text - see Fonts.ORIGINAL_FONT_SIZE
        String symName = Fonts.allSymbols.get(ch);
        if (symName != null) {
            return getPixmap(symName);
        }

        Actor rulesetActor = Fonts.charToRulesetImageActor.get(ch);
        if (rulesetActor != null) {
            try {
                // This sometimes fails with a "Frame buffer couldn't be constructed: incomplete attachment" error, unclear why
                return Fonts.getPixmapFromActor(rulesetActor);
            } catch (Exception ex) {
                return new Pixmap(0,0, Pixmap.Format.RGBA8888); // Empty space
            }
        }

        return fontImplementation.getCharPixmap(ch);
    }

    @Override
    public void getGlyphs(GlyphLayout.GlyphRun run, CharSequence str, int start, int end, BitmapFont.Glyph lastGlyph) {
        packer.setPackToTexture(true); // All glyphs added after this are packed directly to the texture.
        super.getGlyphs(run, str, start, end, lastGlyph);
        if (dirty) {
            dirty = false;
            packer.updateTextureRegions(regions, filter, filter, false);
        }
    }

    @Override
    public void dispose() {
        packer.dispose();
    }
}
