package com.unciv.utils;

import com.unciv.models.metadata.GameSettings;
import com.unciv.app.desktop.display.PlatformDisplay;
import com.unciv.app.desktop.display.ScreenMode;

import java.util.Map;

public class Display {

    public static PlatformDisplay platform;

    public static boolean hasOrientation() {
        return platform.hasOrientation();
    }

    public static void setOrientation(ScreenOrientation orientation) {
        platform.setOrientation(orientation);
    }

    public static boolean hasCutout() {
        return platform.hasCutout();
    }

    public static void setCutout(boolean enabled) {
        platform.setCutout(enabled);
    }

    public static Map<Integer, ScreenMode> getScreenModes() {
        return platform.getScreenModes();
    }

    public static void setScreenMode(int id, GameSettings settings) {
        platform.setScreenMode(id, settings);
    }

    public static boolean hasUserSelectableSize(int id) {
        return platform.hasUserSelectableSize(id);
    }
}
