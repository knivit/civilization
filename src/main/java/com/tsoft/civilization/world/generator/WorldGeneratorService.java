package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.Rect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WorldGeneratorService {
    private static final WorldGenerator[] generators = new WorldGenerator[] {
        new EarthWorldGenerator(),
        new OneContinentWorldGenerator()
    };

    public static WorldGenerator getGenerator(int index)  {
        if (index < 0 || index >= generators.length) {
            throw new IllegalArgumentException("Invalid generator's index = " + index + ", must be [0.." + (generators.length - 1) + "]");
        }

        return generators[index];
    }

    // Find out start locations (on the Map) for the Civilizations
    // We will try to place the Civilizations on equal distances from each other
    public static List<Point> getCivilizationsStartLocations(int civilizationCount, TilesMap tilesMap) {
        assert (civilizationCount > 0) : "Number of civilizations must be > 0";

        // Divide the Map on equal rectangles
        List<Rect> rectangles = getEqualMapsRectangles(civilizationCount, tilesMap);

        // for each rectangle, create a list of tiles which can be used for a settlement
        // exclude rectangles with no tiles
        @RequiredArgsConstructor
        class RectTiles implements Comparable<RectTiles> {
            @Getter
            private final List<Point> tiles;

            public int getSize() {
                return tiles.size();
            }

            @Override
            public int compareTo(RectTiles other) {
                return Integer.compare(this.getSize(), other.getSize());
            }
        }

        List<RectTiles> rectTiles = new ArrayList<>();
        for (Rect rect : rectangles) {
            List<Point> tiles = getTilesForSettlement(rect, tilesMap);
            if (tiles.size() > 0) {
                RectTiles rt = new RectTiles(tiles);
                rectTiles.add(rt);
            }
        }
        assert (rectTiles.size() > 0) : "There is no place for civilization(s) to start";

        // sort the list (areas with most tiles are on top)
        Collections.sort(rectTiles);

        // For each civilization, get its rectangle and find out its start point in this rectangle
        int index = 0;
        ArrayList<Point> startLocations = new ArrayList<>();
        for (int i = 0; i < civilizationCount; i ++) {
            List<Point> tiles = rectTiles.get(index).getTiles();

            // There are some tries to place a civilization
            for (int k = 0; k < civilizationCount; k ++) {
                if (placeCivilization(startLocations, tiles)) {
                    break;
                }
            }

            index ++;
            if (index >= rectTiles.size()) {
                index = 0;
            }
        }

        assert (startLocations.size() == civilizationCount) : "Cannot find an area to place the civilizations";
        return startLocations;
    }

    public static int getGeneratorCount() {
        return generators.length;
    }

    private static List<Rect> getEqualMapsRectangles(int civilizationCount, TilesMap tilesMap) {
        int n = (int)Math.ceil(Math.sqrt(civilizationCount));
        int rectWidth = (int)Math.ceil((double)tilesMap.getWidth() / n);
        int rectHeight = (int)Math.ceil((double)tilesMap.getHeight() / n);

        // Build a list of rectangles, which have an Earth tiles
        List<Rect> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            for (int k = 0; k < n; k ++) {
                int x1 = k * rectWidth;
                int x2 = Math.min((k + 1) * rectWidth, tilesMap.getWidth());
                int y1 = i * rectHeight;
                int y2 = Math.min((i + 1) * rectHeight, tilesMap.getHeight());

                // in this rect, try to find the place for the settlement
                boolean found = false;
                for (int y = y1; y < y2; y ++) {
                    for (int x = x1; x < x2; x ++) {
                        if (tilesMap.getTile(x, y).canBuildCity()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }

                if (found) {
                    Rect rect = new Rect(x1, y1, x2, y2);
                    rectangles.add(rect);
                }
            }
        }
        return rectangles;
    }

    private static boolean placeCivilization(List<Point> startPoints, List<Point> tiles) {
        int tileIndex = ThreadLocalRandom.current().nextInt(tiles.size());

        // check is this tile already assigned
        boolean found = false;
        for (Point point : startPoints) {
            if (point.equals(tiles.get(tileIndex))) {
                found = true;
                break;
            }
        }

        if (!found) {
            startPoints.add(tiles.get(tileIndex));
            return true;
        }
        return false;
    }

    private static List<Point> getTilesForSettlement(Rect rect, TilesMap tilesMap) {
        List<Point> points = new ArrayList<>();
        for (int y = rect.getY1(); y < rect.getY2(); y ++) {
            for (int x = rect.getX1(); x < rect.getX2(); x ++) {
                if (tilesMap.getTile(x, y).canBuildCity()) {
                    points.add(new Point(x, y));
                }
            }
        }

        return points;
    }
}
