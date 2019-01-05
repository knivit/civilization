package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.Coast;
import com.tsoft.civilization.tile.base.Grassland;
import com.tsoft.civilization.tile.luxury.AbstractLuxury;
import com.tsoft.civilization.tile.luxury.Bananas;
import com.tsoft.civilization.tile.luxury.Fish;
import com.tsoft.civilization.tile.luxury.LuxuryType;
import com.tsoft.civilization.tile.luxury.Silk;
import com.tsoft.civilization.tile.luxury.Sugar;
import com.tsoft.civilization.tile.luxury.Whales;
import com.tsoft.civilization.tile.luxury.Wine;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class LuxuryList {
    private class LuxuryInfo {
        private Class<? extends AbstractLuxury> luxuryClass;

        private int count;

        private LuxuryType luxuryType;

        public LuxuryInfo(Class<? extends AbstractLuxury> luxuryClass, int count) {
            this.luxuryClass = luxuryClass;
            this.count = count;

            AbstractLuxury luxury = AbstractLuxury.newInstance(luxuryClass);
            luxuryType = luxury.getLuxuryType();
        }

        public Class<? extends AbstractLuxury> getLuxuryClass() {
            return luxuryClass;
        }

        public int getCount() {
            return count;
        }

        public LuxuryType getLuxuryType() {
            return luxuryType;
        }
    }

    private ArrayList<LuxuryInfo> luxuryInfoList = new ArrayList<LuxuryInfo>();

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
        addLuxury(Coast.class, LuxuryType.SEA_FOOD);
        addLuxury(Grassland.class, LuxuryType.FOOD);
    }

    private void addLuxury(Class<? extends AbstractTile> tileClass, LuxuryType luxuryType) {
        ArrayList<Point> tilePoints = tilesMap.getTileClassLocations(tileClass);
        if (tilePoints.size() == 0) {
            log.warn("There is no tile's class = {}, so Luxury with type = {} is skipped",  tileClass.getName(), luxuryType.name());
            return;
        }

        for (LuxuryInfo luxuryInfo : luxuryInfoList) {
            if (luxuryInfo.getLuxuryType() != luxuryType) {
                continue;
            }

            int count = 0;
            for (int n = 0 ; n < luxuryInfo.getCount(); n ++) {
                AbstractLuxury luxury = AbstractLuxury.newInstance(luxuryInfo.getLuxuryClass());

                int index = (int)(Math.random() * tilePoints.size());
                Point point = tilePoints.get(index);
                AbstractTile tile = tilesMap.getTile(point);
                if (tile.setLuxury(luxury)) {
                    count ++;
                }
            }

            log.info("Added {} luxuries of type {}",  count, luxuryInfo.getLuxuryClass().getName());
        }
    }
}
