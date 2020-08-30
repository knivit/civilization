package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.tile.base.snow.Snow;
import com.tsoft.civilization.tile.base.tundra.Tundra;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.tile.luxury.LuxuryList;
import com.tsoft.civilization.tile.util.ResourceList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.*;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.Rect;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/*
 * One big continent, small ocean (80% earth, 20% ocean)
*/
public class OneContinentWorldGenerator implements WorldGenerator {
    private TilesMap tilesMap;
    private Climate climate;

    @Override
    public void generate(TilesMap tilesMap, Climate climate) {
        this.tilesMap = tilesMap;
        this.climate = climate;

        Rect continent = calcContinentDimension(tilesMap.getWidth(), tilesMap.getHeight());

        defineContinent(continent);

        applyClimate();

        noiseBoundaries();

        addShallowWater();

        changeBaseTiles();

        addMountains();

        addRivers();

        LuxuryList luxuryList = new LuxuryList(tilesMap, climate);
        luxuryList.addToMap();

        ResourceList resourceList = new ResourceList(tilesMap, climate);
        resourceList.addToMap();
    }

    private void addMountains() {

    }

    private static class TP {
        private String[] tileClasses;
        private int probabilityPercent;

        TP(int probabilityPercent, String ... tileClasses) {
            this.probabilityPercent = probabilityPercent;
            this.tileClasses = tileClasses;
        }
    }

    private void changeBaseTiles() {
        changeTiles(90, 100,
                new TP(100, Ice.CLASS_UUID));

        changeTiles(80, 90,
                new TP(70, Snow.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID),
                new TP(10, Ice.CLASS_UUID));

        changeTiles(60, 80,
                new TP(20, Snow.CLASS_UUID),
                new TP(30, Tundra.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID, Marsh.CLASS_UUID),
                new TP(10, Tundra.CLASS_UUID, Hill.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID, Forest.CLASS_UUID));

        changeTiles(30, 60,
                new TP(20, Grassland.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID, Hill.CLASS_UUID),
                new TP(20, Grassland.CLASS_UUID, Hill.CLASS_UUID),
                new TP(20, Grassland.CLASS_UUID, Hill.CLASS_UUID, Forest.CLASS_UUID));

        changeTiles(-10, 30,
                new TP(40, Desert.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID),
                new TP(20, Desert.CLASS_UUID, Hill.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID, Hill.CLASS_UUID));

        changeTiles(-30, -10,
                new TP(20, Desert.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID),
                new TP(10, Desert.CLASS_UUID, Oasis.CLASS_UUID),
                new TP(30, Plain.CLASS_UUID, Jungle.CLASS_UUID),
                new TP(10, Desert.CLASS_UUID, Hill.CLASS_UUID),
                new TP(10, Plain.CLASS_UUID, Hill.CLASS_UUID, Jungle.CLASS_UUID));

        changeTiles(-60, -30,
                new TP(20, Grassland.CLASS_UUID),
                new TP(40, Grassland.CLASS_UUID, Jungle.CLASS_UUID),
                new TP(10, Plain.CLASS_UUID),
                new TP(10, Plain.CLASS_UUID, Hill.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID, Hill.CLASS_UUID, Jungle.CLASS_UUID));

        changeTiles(-80, -60,
                new TP(20, Tundra.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID, Forest.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID, Hill.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID, Hill.CLASS_UUID, Forest.CLASS_UUID),
                new TP(10, Plain.CLASS_UUID),
                new TP(10, Snow.CLASS_UUID));

        changeTiles(-90, -80,
                new TP(40, Snow.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID),
                new TP(20, Plain.CLASS_UUID),
                new TP(20, Tundra.CLASS_UUID, Forest.CLASS_UUID));

        changeTiles(90, 100,
                new TP(100, Ice.CLASS_UUID));
    }

    private void changeTiles(int fromY, int toY, TP ... tps) {
        // -100% (South Pole) .. +100% (North Pole)
        double onePercent = tilesMap.getHeight() / 200.0;
        int y1 = (tilesMap.getHeight() / 2) + (int)Math.round(fromY * onePercent);
        int y2 = (tilesMap.getHeight() / 2) + (int)Math.round(toY * onePercent);

        // get locations of earth tiles
        ArrayList<Point> points = new ArrayList<>();
        for (int y = y1; y < y2; y ++) {
            for (int x = 0; x < tilesMap.getWidth(); x ++) {
                AbstractTile<?> tile = tilesMap.getTile(x, y);
                if (tile.getTileType().isEarth()) {
                    points.add(tile.getLocation());
                }
            }
        }

        // changing
        if (tps.length == 1) {
            for (Point point : points) {
                addTileWithFeatures(point, tps[0].tileClasses);
            }
        } else {
            for (Point point : points) {
                int random = ThreadLocalRandom.current().nextInt(100 + 1);
                for (TP tp : tps) {
                    random -= tp.probabilityPercent;
                    if (random < 0) {
                        addTileWithFeatures(point, tp.tileClasses);
                        break;
                    }
                }
            }
        }
    }

    private void addTileWithFeatures(Point location, String[] classes) {
        assert (classes.length > 0) : "Length must be more than 0";

        // First goes a tile
        AbstractTile<?> tile = AbstractTile.newInstance(classes[0]);
        assert (tile != null) : "Invalid tile " + classes[0];
        tilesMap.setTile(location, tile);

        // Next may be features
        for (int i = 1; i < classes.length; i ++) {
            TerrainFeature<?> feature = TerrainFeature.newInstance(classes[i], tile);
        }
    }

    // Fill the continent with default tiles
    private void defineContinent(Rect continent) {
        for (int y = 0; y < tilesMap.getHeight(); y ++) {
            for (int x = 0; x < tilesMap.getWidth(); x ++) {
                boolean isContinent =
                    (x >= continent.getX1() && x < continent.getX2()) &&
                    (y >= continent.getY1() && y < continent.getY2());

                if (isContinent) {
                    tilesMap.setTile(new Point(x, y), new Grassland());
                } else {
                    tilesMap.setTile(new Point(x, y), new Ocean());
                }
            }
        }
    }

    // Somewhere change continent's borders
    private void noiseBoundaries() {

    }

    private void addRivers() {

    }

    private void applyClimate() {

    }

    private void addShallowWater() {
        addShallowWaterOutsideContinents(tilesMap, climate);
        addShallowWaterInsideContinents();
    }

    public void addShallowWaterOutsideContinents(TilesMap tilesMap, Climate climate) {
        switch (climate) {
            // There always should be a path of shallow water around the continents
            case NORMAL: {
                for (int y = 0; y < tilesMap.getHeight(); y ++) {
                    for (int x = 0; x < tilesMap.getWidth(); x ++) {
                        // if the current tile is a deep water tile and
                        // is connected with an Earth tile,
                        // then make it a shallow water tile
                        if (! Ocean.class.equals(tilesMap.getTile(x, y).getClass())) {
                            continue;
                        }

                        ArrayList<Point> locations = tilesMap.getLocationsAround(new Point(x, y), 1);
                        for (Point loc : locations) {
                            AbstractTile<?> tile = tilesMap.getTile(loc);
                            if (tile.getTileType().isEarth()) {
                                tile.addFeature(new Coast());
                                break;
                            }
                        }
                    }
                }
                break;
            }

            default: {
                throw new IllegalArgumentException("Unknown Climate =" + climate.name());
            }
        }
    }

    private void addShallowWaterInsideContinents() {

    }

    public Rect calcContinentDimension(long worldWidth, long worldHeight) {
        // Calculate dimension of the inner continent
        // which area is 60% of World's are
        //
        //   |---------------------|
        //   | S1  dY              |
        //   |   |-------------|   |
        // Y |   | S2          |   |
        //   |   |-------------|   |
        //   | dX                  |
        //   |---------------------|
        //              X
        // S1 = X*Y
        // S2 = (X-2*dX)*(Y-2*dY)
        // S2/S1 = 60/100
        // X/Y = dX/dY
        //
        // Then
        //      Y*dX
        // dY = ----
        //       X
        //
        //      X * sqrt(1 + (100-60)/100) - X
        // dX = ------------------------------
        //                   2
        //
        double k = Math.sqrt(1 + (100.0 - 60.0)/100.0);
        double dX = (k * worldWidth - worldWidth) / 2;
        double dY = (worldHeight * dX) / worldWidth;

        long ldX = Math.round(dX);
        long ldY = Math.round(dY);

        return new Rect((int)ldX, (int)ldY, (int)(worldWidth - ldX), (int)(worldHeight - ldY));
    }
}
