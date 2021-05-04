package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.resource.bonus.Bananas;
import com.tsoft.civilization.tile.resource.bonus.Fish;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class LuxuryList {
    private static class LuxuryInfo {
        private Class<? extends AbstractLuxuryResource> luxuryClass;

        private int count;

        private LuxuryType luxuryType;

        public LuxuryInfo(Class<? extends AbstractLuxuryResource> luxuryClass, int count) {
            this.luxuryClass = luxuryClass;
            this.count = count;

            AbstractLuxuryResource luxury = AbstractLuxuryResource.newInstance(luxuryClass);
            luxuryType = luxury.getLuxuryType();
        }

        public Class<? extends AbstractLuxuryResource> getLuxuryClass() {
            return luxuryClass;
        }

        public int getCount() {
            return count;
        }

        public LuxuryType getLuxuryType() {
            return luxuryType;
        }
    }

    private ArrayList<LuxuryInfo> luxuryInfoList = new ArrayList<>();

    private TilesMap tilesMap;

    private Climate climate;

    public LuxuryList(TilesMap tilesMap, Climate climate) {
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
            case NORMAL: {
                // calculate the number of Luxuries depending on Map's size
                int count = (int)(Math.sqrt(tilesMap.getWidth() * tilesMap.getHeight()));

                luxuryInfoList.add(new LuxuryInfo(Wine.class, count / 4));
                luxuryInfoList.add(new LuxuryInfo(Silk.class, count / 6));
                luxuryInfoList.add(new LuxuryInfo(Bananas.class, count / 2));
                luxuryInfoList.add(new LuxuryInfo(Sugar.class, count / 4));
                luxuryInfoList.add(new LuxuryInfo(Fish.class, count / 8));
                luxuryInfoList.add(new LuxuryInfo(Whales.class, count / 10));
                break;
            }

            default: {
                throw new IllegalArgumentException("Unknown Climate =" + climate.name());
            }
        }
    }

    // Add luxuries to the Map
    private void addLuxuriesToMap() {
        addLuxuryOnFeature(Coast.class, LuxuryType.SEA_FOOD);
        addLuxuryOnTile(Grassland.class, LuxuryType.FOOD);
    }

    private void addLuxuryOnFeature(Class<? extends TerrainFeature> featureClass, LuxuryType luxuryType) {
        addLuxury(featureClass.getSimpleName(), tilesMap.getTerrainFeatureClassLocations(featureClass), luxuryType);
    }

    private void addLuxuryOnTile(Class<? extends AbstractTile> tileClass, LuxuryType luxuryType) {
        addLuxury(tileClass.getSimpleName(), tilesMap.getTileClassLocations(tileClass), luxuryType);
    }

    private void addLuxury(String className, List<Point> locations, LuxuryType luxuryType) {
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
                AbstractLuxuryResource luxury = AbstractLuxuryResource.newInstance(luxuryInfo.getLuxuryClass());

                int index = ThreadLocalRandom.current().nextInt(locations.size());
                Point point = locations.get(index);
                AbstractTile tile = tilesMap.getTile(point);
                if (tile.setLuxury(luxury)) {
                    count ++;
                }
            }

            log.info("Added {} luxuries of type {}",  count, luxuryInfo.getLuxuryClass().getName());
        }
    }
}
