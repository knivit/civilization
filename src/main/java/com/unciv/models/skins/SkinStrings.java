package com.unciv.models.skins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tsoft.civilization.Civ5;

public class SkinStrings {

    private String skin;

    public SkinStrings() {
        skin = Civ5.Current.settings.skin;
    }

    private static final String skinLocation = "Skins/$skin/";
    private SkinConfig skinConfig = SkinCache.get(skin) ?: new SkinConfig()

    // Default shapes must always end with "Shape" so the UiElementDocsWriter can identify them
    public static final String roundedEdgeRectangleSmallShape = skinLocation + "roundedEdgeRectangle-small";
    public static final String roundedTopEdgeRectangleSmallShape = skinLocation + "roundedTopEdgeRectangle-small";
    public static final String roundedTopEdgeRectangleSmallBorderShape = skinLocation + "roundedTopEdgeRectangle-small-border";
    public static final String roundedEdgeRectangleMidShape = skinLocation + "roundedEdgeRectangle-mid";
    public static final String roundedEdgeRectangleMidBorderShape = skinLocation + "roundedEdgeRectangle-mid-border";
    public static final String roundedEdgeRectangleShape = skinLocation + "roundedEdgeRectangle";
    public static final String rectangleWithOutlineShape = skinLocation + "rectangleWithOutline";
    public static final String selectBoxShape = skinLocation + "select-box";
    public static final String selectBoxPressedShape = skinLocation + "select-box-pressed";
    public static final String checkboxShape = skinLocation + "checkbox";
    public static final String checkboxPressedShape = skinLocation + "checkbox-pressed";

    /**
     * Gets either a drawable which was defined inside skinConfig for the given path or the drawable
     * found at path itself or the default drawable to be applied as the background for an UI element.
     *
     * @param path      The path of the UI background in UpperCamelCase. Should be the location of the
     *                  UI element inside the UI tree e.g. WorldScreen/TopBar/StatsTable.
     *
     *                  If the UI element is used in multiple Screens start the path with General
     *                  e.g. General/Tooltip
     *
     *                  If the UI element has multiple states with different tints use a distinct
     *                  name for every state e.g.
     *                  - CityScreen/CityConstructionTable/QueueEntry
     *                  - CityScreen/CityConstructionTable/QueueEntrySelected
     *
     * @param default   The path to the background which should be used if path is not available.
     *                  Should be one of the predefined ones inside SkinStrings or null to get a
     *                  solid background.
     *
     * @param tintColor Default tint color if the UI Skin doesn't specify one. If both not specified,
     *                  the returned background will not be tinted. If the UI Skin specifies a
     *                  separate alpha value, it will be applied to a clone of either color.
     */
    public NinePatchDrawable getUiBackground(String path, String def, Color tintColor) {
        String locationByName = skinLocation + path;
        val skinVariant = skinConfig.skinVariants[path];
        val locationByConfigVariant = if (skinVariant?.image != null) skinLocation + skinVariant.image else null
        val tint = (skinVariant?.tint ?: tintColor)?.run {
            if (skinVariant?.alpha == null) this
            else cpy().apply { a = skinVariant.alpha }
        }
        String location = when {
            locationByConfigVariant != null && ImageGetter.ninePatchImageExists(locationByConfigVariant) -> locationByConfigVariant
            ImageGetter.ninePatchImageExists(locationByName) ->locationByName
            else -> def
        }
        return ImageGetter.getNinePatch(location, tint);
    }

    public Color getUIColor(String path, Color def) {
        return skinConfig.skinVariants[path] ?.tint
                ?:def
                ?:skinConfig.clearColor;
    }
}
