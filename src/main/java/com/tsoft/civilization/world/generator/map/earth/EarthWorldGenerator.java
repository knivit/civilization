package com.tsoft.civilization.world.generator.map.earth;

import com.tsoft.civilization.improvement.ImprovementFactory;
import com.tsoft.civilization.improvement.ancientruins.AncientRuins;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.generator.map.AbstractWorldMap;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.map.MapGenerator;
import com.tsoft.civilization.world.generator.resource.BonusResourceGenerator;
import com.tsoft.civilization.world.generator.resource.LuxuryResourcesGenerator;
import com.tsoft.civilization.world.generator.resource.StrategicResourceGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.tsoft.civilization.world.MapSize.DUEL;
import static com.tsoft.civilization.world.MapSize.STANDARD;

public class EarthWorldGenerator implements WorldGenerator {

    private static final Random random = new Random();
    private static final MapGenerator MAP_GENERATOR = new MapGenerator();
    private static final Map<MapSize, Supplier<AbstractWorldMap>> MAPS = new HashMap<>();

    static {
        MAPS.put(DUEL, EarthDuelMap::new);
        MAPS.put(STANDARD, EarthStandardMap::new);
    }

    @Override
    public TilesMap generate(MapSize mapSize, Climate climate) {
        AbstractWorldMap worldMap = MAPS.get(mapSize).get();
        TilesMap map = MAP_GENERATOR.create(mapSize, 2, worldMap.getMap());

        addImprovements(map);
        addResources(map, climate);

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

    private void addResources(TilesMap map, Climate climate) {
        addBonusResources(map, climate);
        addLuxuryResources(map, climate);
        addStrategicResources(map, climate);
    }

    private void addBonusResources(TilesMap map, Climate climate) {
        BonusResourceGenerator bonusResourceGenerator = new BonusResourceGenerator(map, climate);
        bonusResourceGenerator.generate();
    }

    private void addLuxuryResources(TilesMap map, Climate climate) {
        LuxuryResourcesGenerator luxuryResourcesGenerator = new LuxuryResourcesGenerator(map, climate);
        luxuryResourcesGenerator.generate();
    }

    private void addStrategicResources(TilesMap map, Climate climate) {
        StrategicResourceGenerator strategicResourceGenerator = new StrategicResourceGenerator(map, climate);
        strategicResourceGenerator.generate();
    }
}
