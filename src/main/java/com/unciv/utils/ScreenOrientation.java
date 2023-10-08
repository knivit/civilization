package com.unciv.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScreenOrientation {

    Landscape("Landscape (fixed)"),
    Portrait("Portrait (fixed)"),
    Auto("Auto (sensor adjusted)");

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
