package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.MapSize;

public interface WorldGenerator {

    TilesMap  generate(MapSize mapSize, Climate climate);
}
