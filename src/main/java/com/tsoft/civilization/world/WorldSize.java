package com.tsoft.civilization.world;

public enum WorldSize {

    DUEL,
    TINY,
    SMALL,
    STANDARD,
    LARGE,
    HUGE;

    public static WorldSize find(int width, int height) {
        // TODO
        return STANDARD;
    }
}
