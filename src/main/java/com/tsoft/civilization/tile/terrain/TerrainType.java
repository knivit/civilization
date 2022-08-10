package com.tsoft.civilization.tile.terrain;

public enum TerrainType {
    EARTH_MARSH(true),
    EARTH_PLAIN(true),
    EARTH_ROUGH(true),

    SEA(false);

    private final boolean isEarth;

    TerrainType(boolean isEarth) {
        this.isEarth = isEarth;
    }

    public boolean isEarth() {
        return isEarth;
    }
}
