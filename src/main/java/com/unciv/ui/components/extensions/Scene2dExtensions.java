package com.unciv.ui.components.extensions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unciv.ui.images.ImageGetter;
import com.unciv.ui.screens.basescreen.BaseScreen;
import com.unciv.ui.components.Fonts;
import com.unciv.ui.images.IconCircleGroup;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Scene2dExtensions {

    /**
     * Collection of extension functions mostly for libGdx widgets
     */

    public static class RestorableTextButtonStyle extends TextButton.TextButtonStyle {
        Button.ButtonStyle restoreStyle;

        RestorableTextButtonStyle(TextButton.TextButtonStyle baseStyle, Button.ButtonStyle restoreStyle) {
            super(baseStyle);
            this.restoreStyle = restoreStyle;
        }
    }

    /** Disable a [Button] by setting its [touchable][Button.touchable] and [style][Button.style] properties. */
    public static void disable(Button button) {
        button.setTouchable(Touchable.disabled);
        button.setDisabled(true);
        Button.ButtonStyle oldStyle = button.getStyle();
        if (oldStyle instanceof RestorableTextButtonStyle) return;
        TextButton.TextButtonStyle disabledStyle = BaseScreen.skin.get("disabled", TextButton.TextButtonStyle.class);
        button.setStyle(new RestorableTextButtonStyle(disabledStyle, oldStyle));
    }

    /** Enable a [Button] by setting its [touchable][Button.touchable] and [style][Button.style] properties. */
    public static void enable(Button button) {
        Button.ButtonStyle oldStyle = button.getStyle();
        if (oldStyle instanceof RestorableTextButtonStyle) {
            button.setStyle(((RestorableTextButtonStyle) oldStyle).restoreStyle);
        }
        button.setDisabled(false);
        button.setTouchable(Touchable.enabled);
    }

    /** Enable or disable a [Button] by setting its [touchable][Button.touchable] and [style][Button.style] properties,
     *  or returns the corresponding state.
     *
     *  Do not confuse with Gdx' builtin [isDisabled][Button.isDisabled] property,
     *  which is more appropriate to toggle On/Off buttons, while this one is good for 'click-to-do-something' buttons.
     */
    public static boolean isEnabled(Button button) {
        return button.getTouchable() == Touchable.enabled;
    }

    public static void isEnabled(Button button, boolean value) {
        if (value) enable(button);
        else disable(button);
    }

    public static Color colorFromHex(int hexColor) {
        int colorSize = 16 * 16; // 2 hexadecimal digits
        int r = hexColor / (colorSize * colorSize);
        int g = (hexColor / colorSize) % colorSize;
        int b = hexColor % colorSize;
        return colorFromRGB(r, g, b);
    }

    /** Create a new [Color] instance from [r]/[g]/[b] given as Integers in the range 0..255 */
    public static Color colorFromRGB(int r, int g, int b) { return new Color(r / 255f, g / 255f, b / 255f, 1f); }

    /** Create a new [Color] instance from r/g/b given as Integers in the range 0..255 in the form of a 3-element List [rgb] */
    public static Color colorFromRGB(List<Integer> rgb) { return colorFromRGB(rgb.get(0), rgb.get(1), rgb.get(2)); }

    /** Linearly interpolates between this [Color] and [BLACK][Color.BLACK] by [t] which is in the range [[0,1]].
     * The result is returned as a new instance. */
    public static Color darken(Color color, float t) { return new Color(color).lerp(Color.BLACK, t); }

    /** Linearly interpolates between this [Color] and [WHITE][Color.WHITE] by [t] which is in the range [[0,1]].
     * The result is returned as a new instance. */
    public static Color brighten(Color color, float t) { return new Color(color).lerp(Color.WHITE, t); }

    public static void centerX(Actor actor, Actor parent) { actor.setX(parent.getWidth() / 2 - actor.getWidth() / 2); }
    public static void centerY(Actor actor, Actor parent) { actor.setY(parent.getHeight() / 2 - actor.getHeight() / 2); }
    public static void center(Actor actor, Actor parent) { centerX(actor, parent); centerY(actor, parent); }

    public static void centerX(Actor actor, Stage parent) { actor.setX(parent.getWidth() / 2 - actor.getWidth() / 2); }
    public static void centerY(Actor actor, Stage parent) { actor.setY(parent.getHeight() / 2 - actor.getHeight() / 2); }
    public static void center(Actor actor, Stage parent) { centerX(actor, parent); centerY(actor, parent); }


    public static IconCircleGroup surroundWithCircle(Actor actor, float size, boolean resizeActor,
                                           Color color, String circleImageLocation) {
        return new IconCircleGroup(size, actor, resizeActor, color, circleImageLocation);
    }

    public static IconCircleGroup surroundWithThinCircle(Actor actor, Color color) {
        return surroundWithCircle(actor, actor.getWidth() + 2f, false, color, "OtherIcons/Circle");
    }

    public static Table addBorder(Actor actor, float size, Color color, boolean expandCell) {
        Table table = new Table();
        table.pad(size);
        table.setBackground(BaseScreen.skinStrings.getUiBackground("General/Border", tintColor = color));
        Cell<Actor> cell = table.add(actor);
        if (expandCell) cell.expand();
        cell.fill();
        table.pack();
        return table;
    }

    /** Gets a parent of this actor that matches the [predicate], or null if none of its parents match the [predicate]. */
    public static Actor getAscendant(Actor actor, Predicate<Actor> predicate) {
        Actor curParent = actor.getParent();
        while (curParent != null) {
            if (predicate.test(curParent)) return curParent;
            curParent = curParent.getParent();
        }
        return null;
    }

    /** The actors bounding box in stage coordinates */
    public static Rectangle getStageBoundingBox(Actor actor) {
        Vector2 bottomLeft = actor.localToStageCoordinates(Vector2.Zero.cpy());
        Vector2 topRight = actor.localToStageCoordinates(new Vector2(actor.getWidth(), actor.getHeight()));
        return new Rectangle(
                bottomLeft.x,
                bottomLeft.y,
                topRight.x - bottomLeft.x,
                topRight.y - bottomLeft.y
        );
    }

    /** @return the area where this [Rectangle] overlaps with [other], or `null` if it doesn't overlap. */
    public static Rectangle getOverlap(Rectangle rectangle, Rectangle other) {
        float overlapX = (rectangle.x > other.x) ? rectangle.x : other.x;

        float rightX = rectangle.x + rectangle.width;
        float otherRightX = other.x + other.width;
        float overlapWidth = ((rightX < otherRightX) ? rightX : otherRightX) - overlapX;

        float overlapY = (rectangle.y > other.y) ? rectangle.y : other.y;

        float topY = rectangle.y + rectangle.height;
        float otherTopY = other.y + other.height;
        float overlapHeight = ((topY < otherTopY) ? topY : otherTopY) - overlapY;

        boolean noOverlap = overlapWidth <= 0 || overlapHeight <= 0;
        if (noOverlap) return null;
        return new Rectangle(
                overlapX,
                overlapY,
                overlapWidth,
                overlapHeight
        );
    }

    public static float getTop(Rectangle rectangle) { return rectangle.y + rectangle.height; }

    public static float getRight(Rectangle rectangle) { return rectangle.x + rectangle.width; }

    public static void getTopBottomBorder(Group group, Color color) {
        ImageGetter.getDot(color).apply { width=group.width; height=size }
    }

    public static void getLeftRightBorder(Group group, Color color) {
        ImageGetter.getDot(color).apply { width=size; height=group.height }
    }

    public static Group addBorderAllowOpacity(Group group, float size, Color color) {
        group.addActor(getTopBottomBorder(group, color).apply { setPosition(0f, group.height, Align.topLeft) })
        group.addActor(getTopBottomBorder(group, color).apply { setPosition(0f, 0f, Align.bottomLeft) })

        group.addActor(getLeftRightBorder(group, color).apply { setPosition(0f, 0f, Align.bottomLeft) })
        group.addActor(getLeftRightBorder(group, color).apply { setPosition(group.width, 0f, Align.bottomRight) })
        return group;
    }


    /** get background Image for a new separator */
    private static Image getSeparatorImage(Color color) { return new Image(ImageGetter.getWhiteDotDrawable().tint(
        (color.a != 0f) ? color : BaseScreen.skin.getColor("color") //0x334d80
            ));
    }

    /**
     * Create a horizontal separator as an empty Container with a colored background.
     * @param colSpan Optionally override [colspan][Cell.colspan] which defaults to the current column count.
     */
    private static Cell<Image> addSeparator(Table table, Color color, int colSpan, float height) {
        if (!table.cells.isEmpty && !table.cells.last().isEndRow) row();
        Image separator = getSeparatorImage(color);
        Cell<Image> cell = table.add(separator)
                .colspan((colSpan == 0) ? table.columns : colSpan)
                .height(height).fillX();
        row();
        return cell;
    }

    /**
     * Create a vertical separator as an empty Container with a colored background.
     *
     * Note: Unlike the horizontal [addSeparator] this cannot automatically span several rows. Repeat the separator if needed.
     */
    private static Cell<Image> addSeparatorVertical(Table table, Color color, float width) {
        return table.add(getSeparatorImage(color)).width(width).fillY();
    }

/** Alternative to [Table].[add][Table] that returns the Table instead of the new Cell to allow a different way of chaining */
    public static <T extends Actor> Table addCell(Table table, T actor)  {
        table.add(actor);
        return table;
    }

/** Shortcut for [Cell].[pad][com.badlogic.gdx.scenes.scene2d.ui.Cell.pad] with top=bottom and left=right */
    public static <T extends Actor> Cell<T> pad(Cell<T> cell, float vertical, float horizontal) {
        return cell.pad(vertical, horizontal, vertical, horizontal);
    }

    /** Sets both the width and height to [size] */
    public static void setSize(Image image, float size) {
        image.setSize(size, size);
    }

    /** Translate a [String] and make a [TextButton] widget from it */
    public static TextButton toTextButton(String str, TextButton.TextButtonStyle style, boolean hideIcons) {
        String text = tr(str, hideIcons);
        return (style == null) ? new TextButton(text, BaseScreen.skin) : new TextButton(text, style);
    }

    /** Convert a texture path into an Image, make an ImageButton with a [tinted][overColor]
     *  hover version of the image from it, then [surroundWithCircle] it. */
    public static Group toImageButton(String str, float iconSize, float circleSize, Color circleColor, Color overColor) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        TextureRegionDrawable image = ImageGetter.getDrawable(str);
        style.imageUp = image;
        style.imageOver = image.tint(overColor);
        ImageButton button = new ImageButton(style);
        button.setSize(iconSize, iconSize);
        return surroundWithCircle(button, circleSize, false, circleColor);
    }

    /** Translate a [String] and make a [Label] widget from it */
    public static Label toLabel(String str) { return new Label(tr(str), BaseScreen.skin); }

    /** Make a [Label] widget containing this [Int] as text */
    public static Label toLabel(int val) { return toLabel(Integer.toString(val)); }

    /** Translate a [String] and make a [Label] widget from it with a specified font color and size */
    public static Label toLabel(String str, Color fontColor,
                       int fontSize,
                       int alignment,
                       boolean hideIcons) {
        // We don't want to use setFontSize and setFontColor because they set the font,
        //  which means we need to rebuild the font cache which means more memory allocation.
        Label.LabelStyle labelStyle = BaseScreen.skin.get(Label.LabelStyle.class);
        if (fontColor != Color.WHITE || fontSize != Constants.defaultFontSize) { // if we want the default we don't need to create another style
            labelStyle = new Label.LabelStyle(labelStyle); // clone this to another
            labelStyle.fontColor = fontColor;
            if (fontSize != Constants.defaultFontSize) labelStyle.font = Fonts.font;
        }

        return new Label(tr(str, hideIcons), labelStyle)
                .setFontScale(fontSize / Fonts.ORIGINAL_FONT_SIZE)
                .setAlignment(alignment);
        }


    /**
     * Translate a [String] and make a [CheckBox] widget from it.
     * @param changeAction A callback to call on change, with a boolean lambda parameter containing the current [isChecked][CheckBox.isChecked].
     */
    public static CheckBox toCheckBox(String str, boolean startsOutChecked, Consumer<Boolean> changeAction) {
        CheckBox cb = new CheckBox(tr(str), BaseScreen.skin);
        cb.setChecked(startsOutChecked);
        if (changeAction != null) cb.onChange = changeAction.accept(cb.isChecked());

        // Add a little distance between the icon and the text. 0 looks glued together,
        // 5 is about half an uppercase letter, and 1 about the width of the vertical line in "P".
        cb.getImageCell().padRight(1f);
        return cb;
    }

    /** Sets the [font color][Label.LabelStyle.fontColor] on a [Label] and returns it to allow chaining */
    public static Label setFontColor(Label label, Color color) {
        Label.LabelStyle style = new Label.LabelStyle(label.getStyle());
        style.fontColor = color;
        label.setStyle(style);
        return label;
    }

    /** Sets the font size on a [Label] and returns it to allow chaining */
    public static Label setFontSize(Label label, int size) {
        Label.LabelStyle style = new Label.LabelStyle(label.getStyle());
        style.font = Fonts.font;
        label.setStyle(style);
        label.setFontScale(size / Fonts.ORIGINAL_FONT_SIZE);
        return label;
    }

    /** [pack][WidgetGroup.pack] a [WidgetGroup] if its [needsLayout][WidgetGroup.needsLayout] is true.
     *  @return the receiver to allow chaining
     */
    public static WidgetGroup packIfNeeded(WidgetGroup wg) {
        if (wg.needsLayout()) wg.pack();
        return wg;
    }

    /** @return `true` if the screen is narrower than 4:3 landscape */
    public static boolean isNarrowerThan4to3(Stage stg) {
        return stg.getViewport().getScreenHeight() * 4 > stg.getViewport().getScreenWidth() * 3;
    }

    /** Wraps and returns an image in a [Group] of a given size*/
    public static Group toGroup(Image image, float size) {
        Group group = new Group();
        group.setSize(size, size);
        this@toGroup.setSize(size, size);
        this@toGroup.center(image);
        this@toGroup.setOrigin(Align.center);
        image.addActor(this@toGroup);
    }

    /** Adds actor to a [Group] and centers it */
    fun Group.addToCenter(actor: Actor) {
        addActor(actor)
        actor.center(this)
    }

/**
 *  These methods deal with a mistake in Gdx.Input.Keys, where DEL is defined as the keycode actually
 *  produced by the physical Backspace key, while the physical Del key fires the keycode Gdx lists as
 *  FORWARD_DEL. Neither valueOf("Del") and valueOf("Backspace") work as expected.
 *
 *  | Identifier | KeyCode | Physical key | toString() | valueOf(name.TitleCase) | valueOf(toString) |
 *  | ---- |:----:|:----:|:----:|:----:|:----:|
 *  | DEL | 67 | Backspace | Delete | -1 | 67 |
 *  | BACKSPACE | 67 | Backspace | Delete | -1 | 67 |
 *  | FORWARD_DEL | 112 | Del | Forward Delete | -1 | 112 |
 *
 *  This acts as proxy, you replace [Input.Keys] by [GdxKeyCodeFixes] and get sensible [DEL], [toString] and [valueOf].
 *  Differences in behaviour: toString will return an empty string for un-mapped keycodes and UNKNOWN
 *  instead of `null` or "Unknown" respectively,
 *  valueOf will return UNKNOWN for un-mapped names or "" instead of -1.
 */
    @Suppress("GDX_KEYS_BUG", "MemberVisibilityCanBePrivate")
    object GdxKeyCodeFixes {

    const val DEL = Input.Keys.FORWARD_DEL;
    const val BACKSPACE = Input.Keys.BACKSPACE;
    const val UNKNOWN = Input.Keys.UNKNOWN;

        fun toString(keyCode: Int): String = when(keyCode) {
            UNKNOWN -> ""
            DEL -> "Del"
            BACKSPACE -> "Backspace"
        else -> Input.Keys.toString(keyCode)
                    ?: ""
        }

        fun valueOf(name: String): Int = when (name) {
            "" -> UNKNOWN
            "Del" -> DEL
            "Backspace" -> BACKSPACE
        else -> {
                val code = Input.Keys.valueOf(name)
                if (code == -1) UNKNOWN else code
            }
        }
    }

    fun Input.areSecretKeysPressed() = isKeyPressed(Input.Keys.SHIFT_RIGHT) &&
            (isKeyPressed(Input.Keys.CONTROL_RIGHT) || isKeyPressed(Input.Keys.ALT_RIGHT))
}