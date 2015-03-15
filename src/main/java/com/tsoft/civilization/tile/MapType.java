package com.tsoft.civilization.tile;

import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Dir4;
import com.tsoft.civilization.util.Dir6;
import com.tsoft.civilization.util.Dir8;

public enum MapType {
    FOUR_TILES(1, new Dir4(-1, 0)), SIX_TILES(2, new Dir6(-1, 0)), EIGHT_TILES(3, new Dir8(-1, 0));

    private int id;

    private AbstractDir dir;

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
