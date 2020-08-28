package com.tsoft.civilization.tile;

import com.tsoft.civilization.tile.base.*;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.util.Point;

import java.util.HashMap;

public class MockTilesMap extends TilesMap {
    private static HashMap<Character, String> tileCodes = new HashMap<>();

    static {
        // base tiles
        tileCodes.put('.', Ocean.CLASS_UUID);
        tileCodes.put('g', Grassland.CLASS_UUID);
        tileCodes.put('d', Desert.CLASS_UUID);
        tileCodes.put('i', Ice.CLASS_UUID);
        tileCodes.put('l', Lake.CLASS_UUID);
        tileCodes.put('p', Plain.CLASS_UUID);
        tileCodes.put('s', Snow.CLASS_UUID);
        tileCodes.put('t', Tundra.CLASS_UUID);

        // terrain features
        tileCodes.put(',', Coast.CLASS_UUID);
        tileCodes.put('f', Forest.CLASS_UUID);
        tileCodes.put('n', FloodPlain.CLASS_UUID);
        tileCodes.put('h', Hill.CLASS_UUID);
        tileCodes.put('M', Mountain.CLASS_UUID);
        tileCodes.put('j', Jungle.CLASS_UUID);
        tileCodes.put('m', Marsh.CLASS_UUID);
        tileCodes.put('o', Oasis.CLASS_UUID);
    }

    private HashMap<Character, String> asciiTileClasses = new HashMap<>();
    private HashMap<String, Character> tilesAscii = new HashMap<>();

    /** To use with one layer (i.e. tiles only, without features) */
    public MockTilesMap(MapType mapType, String ... asciiLines) {
        super(mapType, (asciiLines[0].length() - 2) / 2, asciiLines.length - 2);
        setTileCodes(tileCodes);
        setMockTiles(1, asciiLines);
    }

    /** To use with features on the tiles */
    public MockTilesMap(MapType mapType, int layerCount, String ... asciiLines) {
        super(mapType, (asciiLines[0].length() - 2) / 2, asciiLines.length / layerCount - 2);
        setTileCodes(tileCodes);
        setMockTiles(layerCount, asciiLines);
    }

    // Set tiles' ascii codes
    private void setTileCodes(HashMap<Character, String> tileCodes) {
        for (Character ch : tileCodes.keySet()) {
            String tileClass = tileCodes.get(ch);
            asciiTileClasses.put(ch, tileClass);
            tilesAscii.put(tileClass, ch);
        }
    }

    // Add tiles (first layer) and tiles' features (second etc)
    private void setMockTiles(int layerCount, String ... asciiLines) {
        // System.out.println("Map is " + getWidth() + " x " + getHeight() + " tiles, layers = " + layerCount);
        for (int layer = 0; layer < layerCount; layer ++) {
            for (int y = 2; y < asciiLines.length / layerCount; y ++) {
                setAsciiLine(y - 2, asciiLines[y * layerCount + layer], (layer == 0));
            }
        }
    }

    private void setAsciiLine(int y, String line, boolean isTile) {
        int x = 0, n = 2;
        if ((y % 2) == 1) n = 3;

        for ( ; n < line.length(); n += 2, x ++) {
            char ch = line.charAt(n);

            String classUuid = asciiTileClasses.get(ch);
            if (classUuid == null) {
                throw new IllegalStateException("Tile class doesn't defined for [" + ch + "] char");
            }

            if (isTile) {
                AbstractTile<?> tile = AbstractTile.newInstance(classUuid);
                if (tile == null) {
                    throw new IllegalArgumentException(("Unknown tile's char = " + ch));
                }

                // System.out.println("(" + x + "," + y + ") " + ch);
                setTile(new Point(x, y), tile);
                continue;
            }

            // for a feature layer, '.' char is a space marker
            if (ch == '.') {
                continue;
            }

            // System.out.println("(" + x + "," + y + ") " + ch);
            AbstractTile<?> tile = getTile(x, y);
            TerrainFeature<?> feature = TerrainFeature.newInstance(classUuid, tile);
            if (feature == null) {
                throw new IllegalArgumentException("Unknown feature's char = " + ch);
            }
        }
    }
}
