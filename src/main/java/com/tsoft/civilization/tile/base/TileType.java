package com.tsoft.civilization.tile.base;

public enum TileType {
    EARTH_MARSH(true),
    EARTH_PLAIN(true),
    EARTH_ROUGH(true),

    SEA(false);

    private final boolean isEarth;

    TileType(boolean isEarth) {
        this.isEarth = isEarth;
    }

    public boolean isEarth() {
        return isEarth;
    }
}
