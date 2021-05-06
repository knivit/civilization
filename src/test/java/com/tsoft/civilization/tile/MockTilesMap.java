package com.tsoft.civilization.tile;

import com.tsoft.civilization.tile.tile.*;
import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.tile.tile.snow.Snow;
import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.tile.feature.*;
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
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.MapSize;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MockTilesMap extends TilesMap {
    private static final Map<Character, String> TILE_CODES;

    static {
        Map<Character, String> tileCodes = new HashMap<>();
        TILE_CODES = Collections.unmodifiableMap(tileCodes);

        // base tiles
        tileCodes.put('.', Ocean.CLASS_UUID);
        tileCodes.put('d', Desert.CLASS_UUID);
        tileCodes.put('g', Grassland.CLASS_UUID);
        tileCodes.put('i', Ice.CLASS_UUID);
        tileCodes.put('l', Lake.CLASS_UUID);
        tileCodes.put('p', Plain.CLASS_UUID);
        tileCodes.put('s', Snow.CLASS_UUID);
        tileCodes.put('t', Tundra.CLASS_UUID);

        // terrain features
        tileCodes.put('a', Atoll.CLASS_UUID);
        tileCodes.put('F', Fallout.CLASS_UUID);
        tileCodes.put('n', FloodPlain.CLASS_UUID);
        tileCodes.put('f', Forest.CLASS_UUID);
        tileCodes.put('h', Hill.CLASS_UUID);
        tileCodes.put('j', Jungle.CLASS_UUID);
        tileCodes.put('M', Mountain.CLASS_UUID);
        tileCodes.put('m', Marsh.CLASS_UUID);
        tileCodes.put('o', Oasis.CLASS_UUID);
        tileCodes.put(',', Coast.CLASS_UUID);
    }

    /** To use with one layer (i.e. tiles only, without features) */
    public MockTilesMap(String ... asciiLines) {
        this(1, asciiLines);
    }

    /** To use with features on the tiles */
    public MockTilesMap(int layerCount, String ... asciiLines) {
        super(MapSize.STANDARD, (asciiLines[0].length() - 2) / 2, asciiLines.length / layerCount - 2);
        setMockTiles(layerCount, asciiLines);
    }

    // Add tiles (first layer) and tiles' features (second etc)
    private void setMockTiles(int layerCount, String ... asciiLines) {
        for (int layer = 0; layer < layerCount; layer ++) {
            for (int y = 2; y < asciiLines.length / layerCount; y ++) {
                setAsciiLine(y - 2, asciiLines[y * layerCount + layer], (layer == 0));
            }
        }
    }

    private void setAsciiLine(int y, String line, boolean isTile) {
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
                setTile(new Point(x, y), tile);
                continue;
            }

            // for a feature layer, '.' char is a space marker
            if (ch == '.') {
                continue;
            }

            AbstractTile tile = getTile(x, y);
            AbstractFeature feature = FeatureFactory.newInstance(classUuid, tile);
        }
    }
}
