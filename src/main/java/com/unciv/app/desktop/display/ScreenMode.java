package com.unciv.app.desktop.display;

public interface ScreenMode {

    int getId();

    default boolean hasUserSelectableSize() {
        return false;
    }
}
