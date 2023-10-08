package com.unciv.ui.images;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.Map;

public class ImageGetter {

    private static final String whiteDotLocation = "OtherIcons/whiteDot";

    // We then shove all the drawables into a hashmap, because the atlas specifically tells us
    //   that the search on it is inefficient
    private static Map<String, TextureRegionDrawable> textureRegionDrawables = new HashMap<>();

    public static TextureRegionDrawable getDrawable(String fileName) {
        TextureRegionDrawable result = textureRegionDrawables.get(fileName);
        return (result == null) ? textureRegionDrawables.get(whiteDotLocation) : result;
    }
}
