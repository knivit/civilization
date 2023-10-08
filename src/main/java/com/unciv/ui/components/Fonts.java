package com.unciv.ui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unciv.GUI;
import com.unciv.models.metadata.GameSettings;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fonts {

    /** All text is originally rendered in 50px (set in AndroidLauncher and DesktopLauncher), and then scaled to fit the size of the text we need now.
     * This has several advantages: It means we only render each character once (good for both runtime and RAM),
     * AND it means that our 'custom' emojis only need to be once size (50px) and they'll be rescaled for what's needed. */
    public static float ORIGINAL_FONT_SIZE = 50f;
    public static final String DEFAULT_FONT_FAMILY = "";

    public static FontImplementation fontImplementation;
    public static BitmapFont font;

    private static final FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    private static final SpriteBatch spriteBatch = new SpriteBatch();

    private static final Character turn = '⏳';               // U+23F3 'hourglass'
    private static final Character strength = '†';            // U+2020 'dagger'
    private static final Character rangedStrength = '‡';      // U+2021 'double dagger'
    private static final Character movement = '➡';           // U+27A1 'black rightwards arrow'
    private static final Character range = '…';               // U+2026 'horizontal ellipsis'
    private static final Character production = '⚙';          // U+2699 'gear'
    private static final Character gold = '¤';                // U+00A4 'currency sign'
    private static final Character food = '⁂';                // U+2042 'asterism' (to avoid 🍏 U+1F34F 'green apple' needing 2 symbols in utf-16 and 4 in utf-8)
    private static final Character science = '⍾';             // U+237E 'bell symbol' (🧪 U+1F9EA 'test tube', 🔬 U+1F52C 'microscope')
    private static final Character culture = '♪';             // U+266A 'eighth note' (🎵 U+1F3B5 'musical note')
    private static final Character happiness = '⌣';            // U+2323 'smile' (😀 U+1F600 'grinning face')
    private static final Character faith = '☮';               // U+262E 'peace symbol' (🕊 U+1F54A 'dove of peace')
    private static final Character greatArtist = '♬';         // U+266C 'sixteenth note'
    private static final Character greatEngineer = '⚒';       // U+2692 'hammer'
    private static final Character greatGeneral = '⛤';        // U+26E4 'pentagram'
    private static final Character greatMerchant = '⚖';       // U+2696 'scale'
    private static final Character greatScientist = '⚛';      // U+269B 'atom'
    private static final Character death = '☠';               // U+2620 'skull and crossbones'
    private static final Character automate = '⛏';            // U+26CF 'pick'

    public static final Map<Character, String> allSymbols = new HashMap<>() {{
        put(turn, "EmojiIcons/Turn");
        put(strength, "StatIcons/Strength");
        put(rangedStrength, "StatIcons/RangedStrength");
        put(range, "StatIcons/Range");
        put(movement, "StatIcons/Movement");
        put(production, "EmojiIcons/Production");
        put(gold, "EmojiIcons/Gold");
        put(food, "EmojiIcons/Food");
        put(science, "EmojiIcons/Science");
        put(culture, "EmojiIcons/Culture");
        put(happiness, "EmojiIcons/Happiness");
        put(faith, "EmojiIcons/Faith");
        put(greatArtist, "EmojiIcons/Great Artist");
        put(greatEngineer, "EmojiIcons/Great Engineer");
        put(greatGeneral, "EmojiIcons/Great General");
        put(greatMerchant, "EmojiIcons/Great Merchant");
        put(greatScientist, "EmojiIcons/Great Scientist");
        put(death, "EmojiIcons/Death");
        put(automate, "EmojiIcons/Automate");
    }};
            //*MayaCalendar.allSymbols

    public static Map<Character, Actor> charToRulesetImageActor = new HashMap<>();

    /** This resets all cached font data in object Fonts.
     *  Do not call from normal code - reset the Skin instead: `BaseScreen.setSkin()`
     */
    public static void resetFont() {
        GameSettings settings = GUI.getSettings();
        fontImplementation.setFontFamily(settings.getFontFamilyData(), settings.getFontSize());
        font = fontImplementation.getBitmapFont();
        font.getData().markupEnabled = true;
    }

    /** Reduce the font list returned by platform-specific code to font families (plain variant if possible) */
    public List<FontFamilyData> getSystemFonts() {
        List<FontFamilyData> list = fontImplementation.getSystemFonts();
        list.sort(Comparator.comparing(FontFamilyData::getLocalName));
        return list;
    }

    /**
     * Turn a TextureRegion into a Pixmap.
     *
     * .dispose() must be called on the returned Pixmap when it is no longer needed, or else it will leave a memory leak behind.
     *
     * @return New Pixmap with all the size and pixel data from this TextureRegion copied into it.
     */
    // From https://stackoverflow.com/questions/29451787/libgdx-textureregion-to-pixmap
    public static Pixmap extractPixmapFromTextureRegion(TextureRegion textureRegion) {
        TextureData textureData = textureRegion.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }

        Pixmap pixmap = new Pixmap(
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                textureData.getFormat()
        );

        Pixmap textureDataPixmap = textureData.consumePixmap();
        pixmap.drawPixmap(
                textureDataPixmap, // The other Pixmap
                0,                              // The target x-coordinate (top left corner)
                0,                              // The target y-coordinate (top left corner)
                textureRegion.getRegionX(),     // The source x-coordinate (top left corner)
                textureRegion.getRegionY(),     // The source y-coordinate (top left corner)
                textureRegion.getRegionWidth(), // The width of the area from the other Pixmap in pixels
                textureRegion.getRegionHeight() // The height of the area from the other Pixmap in pixels
        );

        textureDataPixmap.dispose(); // Prevent memory leak.
        return pixmap;
    }

    public static Pixmap getPixmapFromActor(Actor actor) {
        frameBuffer.begin();

        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        actor.draw(spriteBatch, 1f);
        spriteBatch.end();

        int w = (int)actor.getWidth();
        int h = (int)actor.getHeight();
        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        Gdx.gl.glReadPixels(0, 0, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixmap.getPixels());
        frameBuffer.end();

        // Pixmap is now *upside down* so we need to flip it around the y axis
        pixmap.setBlending(Pixmap.Blending.None);
        for (int i = 0; i < w; i ++) {
            for (int j = 0; j < h / 2; j++) {
                int topPixel = pixmap.getPixel(i, j);
                int bottomPixel = pixmap.getPixel(i, h - j);
                pixmap.drawPixel(i, j, bottomPixel);
                pixmap.drawPixel(i, h - j, topPixel);
            }
        }

        return pixmap;
    }
}
