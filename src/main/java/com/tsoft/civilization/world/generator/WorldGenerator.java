package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.world.Climate;

public interface WorldGenerator {
    void generate(TilesMap tilesMap, Climate climate);
}
