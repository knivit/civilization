package com.tsoft.civilization.world.generator.earth;

import com.tsoft.civilization.improvement.ImprovementFactory;
import com.tsoft.civilization.improvement.ancientruins.AncientRuins;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.generator.AbstractWorldMap;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.MapGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.tsoft.civilization.world.MapSize.DUEL;

public class EarthWorldGenerator implements WorldGenerator {

    private static final Random random = new Random();
    private static final MapGenerator MAP_GENERATOR = new MapGenerator();
    private static final Map<MapSize, Supplier<AbstractWorldMap>> MAPS = new HashMap<>();

    static {
        MAPS.put(DUEL, EarthDuelMap::new);
    }

    @Override
    public TilesMap generate(MapSize mapSize, Climate climate) {
        AbstractWorldMap worldMap = MAPS.get(mapSize).get();
        TilesMap map = MAP_GENERATOR.create(mapSize, 1, worldMap.getMap());

        addImprovements(map);
        addResources(map);

        return map;
    }

    private void addImprovements(TilesMap map) {
        addAncientRuins(map);
    }

    private void addAncientRuins(TilesMap map) {
        AncientRuins catalogEntry = ImprovementFactory.findByClassUuid(AncientRuins.CLASS_UUID);

        List<AbstractTerrain> tiles = map.tiles()
            .filter(catalogEntry::acceptTile)
            .collect(Collectors.toList());

        int count = random.nextInt(tiles.size()) / 10;
        count = Math.max(count, 1);
        count = Math.min(count, 10);

        for (int i = 0; i < count; i ++) {
            int n = random.nextInt(tiles.size());

            AbstractTerrain tile = tiles.get(n);
            if (tile.getImprovement() == null) {
                ImprovementFactory.newInstance(AncientRuins.CLASS_UUID, tile, null);
            }
        }
    }

    private void addResources(TilesMap map) {
        addBonusResources(map);
        addLuxuryResources(map);
        addStrategicResources(map);
    }

    private void addBonusResources(TilesMap map) {

    }

    private void addLuxuryResources(TilesMap map) {

    }

    private void addStrategicResources(TilesMap map) {
    }
}
