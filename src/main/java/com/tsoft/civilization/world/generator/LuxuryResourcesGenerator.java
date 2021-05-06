package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceFactory;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.tile.resource.luxury.*;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Climate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class LuxuryResourcesGenerator {

    @Getter
    private static class LuxuryInfo {
        private final String classUuid;
        private final int count;
        private final ResourceType luxuryType;

        public LuxuryInfo(String classUuid, int count) {
            this.classUuid = classUuid;
            this.count = count;

            AbstractResource luxury = ResourceFactory.findByClassUuid(classUuid);
            luxuryType = luxury.getResourceType();
        }
    }

    private final ArrayList<LuxuryInfo> luxuryInfoList = new ArrayList<>();
    private final TilesMap tilesMap;
    private final Climate climate;

    public LuxuryResourcesGenerator(TilesMap tilesMap, Climate climate) {
        this.tilesMap = tilesMap;
        this.climate = climate;
    }

    public void addToMap() {
        buildLuxuryList();
        addLuxuriesToMap();
    }

    private void buildLuxuryList() {
        luxuryInfoList.clear();

        switch (climate) {
            case COLD: break;

            case NORMAL: {
                // calculate the number of Luxuries depending on Map's size
                int count = (int)(Math.sqrt(tilesMap.getWidth() * tilesMap.getHeight()));

                luxuryInfoList.add(new LuxuryInfo(Cotton.CLASS_UUID, count / 4));
                luxuryInfoList.add(new LuxuryInfo(Silk.CLASS_UUID, count / 6));
                luxuryInfoList.add(new LuxuryInfo(Spices.CLASS_UUID, count / 2));
                luxuryInfoList.add(new LuxuryInfo(Sugar.CLASS_UUID, count / 4));
                luxuryInfoList.add(new LuxuryInfo(Whales.CLASS_UUID, count / 10));
                luxuryInfoList.add(new LuxuryInfo(Wine.CLASS_UUID, count / 8));
                break;
            }

            case HOT: break;

            default: {
                throw new IllegalArgumentException("Unknown climate = " + climate.name());
            }
        }
    }

    // Add luxuries to the Map
    private void addLuxuriesToMap() {
        addLuxuryOnFeature(Coast.class, ResourceType.LUXURY_SEA_FOOD);
        addLuxuryOnTile(Grassland.class, ResourceType.LUXURY_FOOD);
        addLuxuryOnTile(Plain.class, ResourceType.LUXURY_PRODUCTION);
    }

    private void addLuxuryOnFeature(Class<? extends AbstractFeature> featureClass, ResourceType luxuryType) {
        addLuxury(featureClass.getSimpleName(), tilesMap.getTerrainFeatureClassLocations(featureClass), luxuryType);
    }

    private void addLuxuryOnTile(Class<? extends AbstractTile> tileClass, ResourceType luxuryType) {
        addLuxury(tileClass.getSimpleName(), tilesMap.getTileClassLocations(tileClass), luxuryType);
    }

    private void addLuxury(String className, List<Point> locations, ResourceType luxuryType) {
        if (locations.size() == 0) {
            log.warn("There is no tiles (or features) with class = {}, so Luxury with type = {} is skipped",  className, luxuryType.name());
            return;
        }

        for (LuxuryInfo luxuryInfo : luxuryInfoList) {
            if (luxuryInfo.getLuxuryType() != luxuryType) {
                continue;
            }

            int count = 0;
            for (int n = 0 ; n < luxuryInfo.getCount(); n ++) {
                AbstractResource luxury = ResourceFactory.newInstance(luxuryInfo.getClassUuid());

                int index = ThreadLocalRandom.current().nextInt(locations.size());
                Point point = locations.get(index);
                AbstractTile tile = tilesMap.getTile(point);
                if (tile.setLuxury(luxury)) {
                    count ++;
                }
            }

            log.debug("{} luxury resources added", count);
        }
    }
}
