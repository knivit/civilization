package com.tsoft.civilization.world.generator.resource;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceFactory;
import com.tsoft.civilization.tile.resource.ResourceCategory;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.world.Climate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LuxuryResourcesGenerator {

    private static final ResourcesGeneratorHelper resourceGenerator = new ResourcesGeneratorHelper();

    private final TilesMap tilesMap;
    private final Climate climate;

    private static final Map<ResourceType, Double> NORMAL_CLIMATE_PROBABILITIES = new HashMap<>() {{
        put(ResourceType.COTTON, 1.0 / 4);
        put(ResourceType.SILK, 1.0 / 6);
        put(ResourceType.SALT, 1.0 / 4);
        put(ResourceType.SPICES, 1.0 / 3);
        put(ResourceType.SUGAR, 1.0 / 4);
        put(ResourceType.WHALES, 1.0 / 6);
        put(ResourceType.WINE, 1.0 / 3);
    }};

    private static final Map<Climate, Map<ResourceType, Double>> PROBABILITIES = new HashMap<>() {{
       put(Climate.COLD, null);
       put(Climate.NORMAL, NORMAL_CLIMATE_PROBABILITIES);
       put(Climate.HOT, null);
    }};

    public void generate() {
        List<AbstractResource> resources = ResourceFactory.getAvailable(ResourceCategory.LUXURY);
        Map<ResourceType, Double> probabilities = PROBABILITIES.get(climate);
        resourceGenerator.generate(tilesMap, resources, probabilities);
    }
}
