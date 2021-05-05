package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Climate;

public class EarthWorldGenerator implements WorldGenerator {

    private static final WorldGeneratorHelper helper = new WorldGeneratorHelper();

    @Override
    public void generate(TilesMap tilesMap, Climate climate) {
        createOcean(tilesMap);

        helper.addTileWithFeatures(tilesMap, new Point(0, 0), new String[] { Grassland.CLASS_UUID, Hill.CLASS_UUID });
    }

    // Fill the continent with default tiles
    private void createOcean(TilesMap tilesMap) {
        for (int y = 0; y < tilesMap.getHeight(); y ++) {
            for (int x = 0; x < tilesMap.getWidth(); x ++) {
                tilesMap.setTile(new Point(x, y), new Ocean());
            }
        }
    }
}
