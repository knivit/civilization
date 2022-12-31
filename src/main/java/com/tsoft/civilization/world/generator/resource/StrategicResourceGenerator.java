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
public class StrategicResourceGenerator {

    private static final ResourcesGeneratorHelper resourceGenerator = new ResourcesGeneratorHelper();

    private final TilesMap tilesMap;
    private final Climate climate;

    private static final Map<ResourceType, Double> NORMAL_CLIMATE_PROBABILITIES = new HashMap<>() {{
        put(ResourceType.ALUMINIUM, 1.0 / 14);
        put(ResourceType.COAL, 1.0 / 14);
        put(ResourceType.HORSES, 1.0 / 14);
        put(ResourceType.IRON, 1.0 / 16);
        put(ResourceType.OIL, 1.0 / 12);
        put(ResourceType.URANIUM, 1.0 / 14);
    }};

    private static final Map<Climate, Map<ResourceType, Double>> PROBABILITIES = new HashMap<>() {{
        put(Climate.COLD, null);
        put(Climate.NORMAL, NORMAL_CLIMATE_PROBABILITIES);
        put(Climate.HOT, null);
    }};

    public void generate() {
        List<AbstractResource> resources = ResourceFactory.getAvailable(ResourceCategory.STRATEGIC);
        Map<ResourceType, Double> probabilities = PROBABILITIES.get(climate);
        resourceGenerator.generate(tilesMap, resources, probabilities);
    }
}
