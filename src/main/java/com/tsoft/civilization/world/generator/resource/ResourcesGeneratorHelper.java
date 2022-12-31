package com.tsoft.civilization.world.generator.resource;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceFactory;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
public class ResourcesGeneratorHelper {

    // Add resources on Map
    public void generate(TilesMap tilesMap, List<AbstractResource> resources, Map<ResourceType, Double> probabilities) {
        for (AbstractResource catalog : resources) {
            List<AbstractTerrain> tiles = tilesMap.tiles()
                .filter(catalog::acceptTile)
                .collect(Collectors.toList());

            if (tiles.size() == 0) {
                log.info("There is no tiles for resource '{}'",  catalog.getName());
                continue;
            }

            int count = (int)Math.max(1, Math.round(tiles.size() * probabilities.get(catalog.getType())));

            for (int n = 0 ; n < count; n ++) {
                AbstractResource resource = ResourceFactory.newInstance(catalog.getType());

                int index = ThreadLocalRandom.current().nextInt(tiles.size());
                AbstractTerrain tile = tiles.get(index);

                if (tile.getResource() == null) {
                    tile.setResource(resource);
                    count ++;
                }
            }

            log.debug("{} {} resources added", count, catalog.getType());
        }
    }
}

