package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.FeatureFactory;
import com.tsoft.civilization.tile.feature.atoll.Atoll;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.fallout.Fallout;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileFactory;
import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.tile.tile.snow.Snow;
import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.MapSize;

import java.util.HashMap;
import java.util.Map;

public class MapGenerator {

    private static final Map<Character, String> TILE_CODES = new HashMap<>();

    static {
        // base tiles
        TILE_CODES.put('.', Ocean.CLASS_UUID);
        TILE_CODES.put('d', Desert.CLASS_UUID);
        TILE_CODES.put('g', Grassland.CLASS_UUID);
        TILE_CODES.put('i', Ice.CLASS_UUID);
        TILE_CODES.put('l', Lake.CLASS_UUID);
        TILE_CODES.put('p', Plain.CLASS_UUID);
        TILE_CODES.put('s', Snow.CLASS_UUID);
        TILE_CODES.put('t', Tundra.CLASS_UUID);

        // terrain features
        TILE_CODES.put('a', Atoll.CLASS_UUID);
        TILE_CODES.put('F', Fallout.CLASS_UUID);
        TILE_CODES.put('n', FloodPlain.CLASS_UUID);
        TILE_CODES.put('f', Forest.CLASS_UUID);
        TILE_CODES.put('h', Hill.CLASS_UUID);
        TILE_CODES.put('j', Jungle.CLASS_UUID);
        TILE_CODES.put('M', Mountain.CLASS_UUID);
        TILE_CODES.put('m', Marsh.CLASS_UUID);
        TILE_CODES.put('o', Oasis.CLASS_UUID);
        TILE_CODES.put(',', Coast.CLASS_UUID);
    }

    public TilesMap create(MapSize mapSize, int layerCount, String ... asciiLines) {
        TilesMap map = new TilesMap(mapSize, (asciiLines[0].length() - 2) / 2, asciiLines.length / layerCount - 2);
        setMockTiles(map, layerCount, asciiLines);
        return map;
    }

    // Add tiles (first layer) and tiles' features (second etc)
    private void setMockTiles(TilesMap map, int layerCount, String ... asciiLines) {
        for (int layer = 0; layer < layerCount; layer ++) {
            for (int y = 2; y < asciiLines.length / layerCount; y ++) {
                setAsciiLine(map, y - 2, asciiLines[y * layerCount + layer], (layer == 0));
            }
        }
    }

    private void setAsciiLine(TilesMap map, int y, String line, boolean isTile) {
        int x = 0, n = 2;
        if ((y % 2) == 1) {
            n = 3;
        }

        for ( ; n < line.length(); n += 2, x ++) {
            char ch = line.charAt(n);

            String classUuid = TILE_CODES.get(ch);
            if (classUuid == null) {
                throw new IllegalStateException("Tile class doesn't defined for [" + ch + "] char");
            }

            if (isTile) {
                AbstractTile tile = TileFactory.newInstance(classUuid);
                map.setTile(new Point(x, y), tile);
                continue;
            }

            // for a feature layer, '.' char is a space marker
            if (ch == '.') {
                continue;
            }

            AbstractTile tile = map.getTile(x, y);
            FeatureFactory.newInstance(classUuid, tile);
        }
    }

    // Fill the continent with default tiles
    public void fill(TilesMap tilesMap, String tileClassUuid) {
        for (int y = 0; y < tilesMap.getHeight(); y ++) {
            for (int x = 0; x < tilesMap.getWidth(); x ++) {
                tilesMap.setTile(new Point(x, y), TileFactory.newInstance(tileClassUuid));
            }
        }
    }

    public void addTileWithFeatures(TilesMap tilesMap, Point location, String[] uuids) {
        if (uuids == null || uuids.length == 0) {
            throw new IllegalArgumentException("classes length must be more than 0");
        }

        // First goes a tile
        String tileClassUuid = uuids[0];
        AbstractTile tile = TileFactory.newInstance(tileClassUuid);
        tilesMap.setTile(location, tile);

        // Next may be features
        for (int i = 1; i < uuids.length; i ++) {
            FeatureFactory.newInstance(uuids[i], tile);
        }
    }
}
