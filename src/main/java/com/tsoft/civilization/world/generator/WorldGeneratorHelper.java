package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.util.Point;

class WorldGeneratorHelper {

    public void addTileWithFeatures(TilesMap tilesMap, Point location, String[] classes) {
        if (classes == null || classes.length == 0) {
            throw new IllegalArgumentException("classes length must be more than 0");
        }

        // First goes a tile
        String tileClassName = classes[0];
        AbstractTile tile = AbstractTile.newInstance(tileClassName);
        if (tile == null) {
            throw new IllegalArgumentException("Invalid tile " + tileClassName);
        }

        tilesMap.setTile(location, tile);

        // Next may be features
        for (int i = 1; i < classes.length; i ++) {
            TerrainFeature feature = TerrainFeature.newInstance(classes[i], tile);
        }
    }
}
