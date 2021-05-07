package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.FeatureFactory;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileFactory;
import com.tsoft.civilization.util.Point;

public class WorldGeneratorHelper {

    // Fill the continent with default tiles
    public void fill(TilesMap tilesMap, String tileClassUuid) {
        for (int y = 0; y < tilesMap.getHeight(); y ++) {
            for (int x = 0; x < tilesMap.getWidth(); x ++) {
                tilesMap.setTile(new Point(x, y), TileFactory.newInstance(tileClassUuid));
            }
        }
    }

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
            FeatureFactory.newInstance(uuids[i], tile);
        }
    }
}
