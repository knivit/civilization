package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceFactory;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.tile.resource.bonus.*;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Climate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class BonusResourceGenerator {

    @Getter
    private static class ResourceInfo {
        private final String classUuid;
        private final int count;
        private final ResourceType resourceType;

        public ResourceInfo(String classUuid, int count) {
            this.classUuid = classUuid;
            this.count = count;

            AbstractResource resource = ResourceFactory.findByClassUuid(classUuid);
            resourceType = resource.getResourceType();
        }
    }

    private final TilesMap tilesMap;
    private final Climate climate;

    private final ArrayList<ResourceInfo> resourceInfoList = new ArrayList<>();

    public BonusResourceGenerator(TilesMap tilesMap, Climate climate) {
        this.tilesMap = tilesMap;
        this.climate = climate;
    }

    public void addToMap() {
        buildResourceList();
        addResourcesToMap();
    }

    private void buildResourceList() {
        resourceInfoList.clear();

        switch (climate) {
            case COLD: break;

            case NORMAL: {
                // calculate the number of Resources depending on Map's size
                int count = (int)(Math.sqrt(tilesMap.getWidth() * tilesMap.getHeight()));

                resourceInfoList.add(new ResourceInfo(Bananas.CLASS_UUID, count / 4));
                resourceInfoList.add(new ResourceInfo(Bison.CLASS_UUID, count / 4));
                resourceInfoList.add(new ResourceInfo(Cattle.CLASS_UUID, count / 12));
                resourceInfoList.add(new ResourceInfo(Deer.CLASS_UUID, count / 2));
                resourceInfoList.add(new ResourceInfo(Fish.CLASS_UUID, count / 18));
                resourceInfoList.add(new ResourceInfo(Sheep.CLASS_UUID, count / 10));
                resourceInfoList.add(new ResourceInfo(Stone.CLASS_UUID, count / 14));
                resourceInfoList.add(new ResourceInfo(Wheat.CLASS_UUID, count / 12));
                break;
            }

            case HOT: break;

            default: {
                throw new IllegalArgumentException("Unknown climate = " + climate.name());
            }
        }
    }

    // Add resources to the Map
    private void addResourcesToMap() {
        addResourcesOnFeature(Coast.class, ResourceType.EARTH_SEA);
        addResourcesOnTile(Grassland.class, ResourceType.EARTH);
    }

    private void addResourcesOnFeature(Class<? extends AbstractFeature> featureClass, ResourceType resourceType) {
        addResource(featureClass.getSimpleName(), tilesMap.getTerrainFeatureClassLocations(featureClass), resourceType);
    }

    private void addResourcesOnTile(Class<? extends AbstractTile> tileClass, ResourceType resourceType) {
        addResource(tileClass.getSimpleName(), tilesMap.getTileClassLocations(tileClass), resourceType);
    }

    private void addResource(String className, List<Point> locations, ResourceType resourceType) {
        if (locations.size() == 0) {
            log.warn("There is no tiles (or features) with class = {}, so Resource with type = {} is skipped",  className, resourceType.name());
            return;
        }

        for (ResourceInfo resourceInfo : resourceInfoList) {
            if (resourceInfo.getResourceType() != resourceType) {
                continue;
            }

            int count = 0;
            for (int n = 0 ; n < resourceInfo.getCount(); n ++) {
                AbstractResource resource = ResourceFactory.newInstance(resourceInfo.getClassUuid());

                int index = ThreadLocalRandom.current().nextInt(locations.size());
                Point point = locations.get(index);
                AbstractTile tile = tilesMap.getTile(point);
                if (tile.setResource(resource)) {
                    count ++;
                }
            }

            log.debug("{} resources added", count);
        }
    }
}
