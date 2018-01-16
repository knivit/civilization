package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;

public interface WorldGenerator {
    void generate(TilesMap tilesMap, Climate climate);
}
