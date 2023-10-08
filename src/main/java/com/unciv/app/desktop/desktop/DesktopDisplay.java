package com.unciv.app.desktop.desktop;

import com.unciv.models.metadata.GameSettings;
import com.unciv.app.desktop.display.PlatformDisplay;
import com.unciv.app.desktop.display.ScreenMode;

import java.util.HashMap;
import java.util.Map;

public class DesktopDisplay implements PlatformDisplay {

    @Override
    public Map<Integer, ScreenMode> getScreenModes() {
        Map<Integer, ScreenMode> map = new HashMap<>();

        for (DesktopScreenMode mode : DesktopScreenMode.values()) {
            map.put(mode.getId(), mode);
        }

        return map;
    }

    @Override
    public void setScreenMode(int id, GameSettings settings) {
        DesktopScreenMode.get(id).activate(settings);
    }

    @Override
    public boolean hasUserSelectableSize(int id) {
        return DesktopScreenMode.get(id).hasUserSelectableSize();
    }
}
