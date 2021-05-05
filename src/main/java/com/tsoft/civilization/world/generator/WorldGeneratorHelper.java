package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.FeatureFactory;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileFactory;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.util.Point;

class WorldGeneratorHelper {

    public void addTileWithFeatures(TilesMap tilesMap, Point location, String[] uuids) {
        if (uuids == null || uuids.length == 0) {
            throw new IllegalArgumentException("classes length must be more than 0");
        }

        // First goes a tile
        String tileClassUuid = uuids[0];
        AbstractTile tile = TileFactory.newInstance(tileClassUuid);

        tilesMap.setTile(location, tile);

        // Next may be features
        for (int i = 1; i < uuids.length; i ++) {
            AbstractFeature feature = FeatureFactory.newInstance(uuids[i], tile);
        }
    }
}
