package com.unciv.app.desktop.display;

import com.unciv.models.metadata.GameSettings;
import com.unciv.utils.ScreenOrientation;

import java.util.HashMap;
import java.util.Map;

public interface PlatformDisplay {

    void setScreenMode(int id, GameSettings settings);

    default Map<Integer, ScreenMode> getScreenModes() {
        return new HashMap<>();
    }

    default boolean hasCutout() {
        return false;
    }

    default void setCutout(boolean enabled) { }

    default boolean hasOrientation() {
        return false;
    }

    default void setOrientation(ScreenOrientation orientation) { }

    default boolean hasUserSelectableSize(int id) {
        return false;
    }
}
