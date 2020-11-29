package com.tsoft.civilization.tile;

import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Dir6;

public enum MapType {

    SIX_TILES(1, new Dir6(-1, 0));

    private final int id;

    private final AbstractDir dir;

    MapType(int id, AbstractDir dir) {
        this.id = id;
        this.dir = dir;
    }

    public int getId() {
        return id;
    }

    public AbstractDir getDir() {
        return dir;
    }

    public static MapType getMapType(int id) {
        for (MapType mapType : values()) {
            if (mapType.getId() == id) {
                return mapType;
            }
        }
        throw new IllegalArgumentException("Unknown Id = " + id);
    }
}
