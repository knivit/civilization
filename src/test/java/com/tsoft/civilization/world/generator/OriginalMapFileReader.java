package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.world.MapSize;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class OriginalMapFileReader {

    @Test
    public void main() throws Exception {
        String fileName = OriginalMapFileReader.class.getResource("/original/Earth_Standard.Civ5Map").getFile();
        MapSize mapSize = MapSize.STANDARD;
        int width = mapSize.getWidth();
        int height = mapSize.getHeight();

        byte[] data = Files.readAllBytes(Path.of(fileName));

        StringBuilder layer0 = new StringBuilder();
        StringBuilder layer1 = new StringBuilder();
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                boolean even = ((y % 2) == 0);
                boolean first = (x == 0);
                boolean last = (x == width - 1);
                char tile = read(data, x, y, width, height, 0);
                char opts = read(data, x, y, width, height, 4);
                layer0.append((first && even) ? "" : " ");
                layer0.append(tile);
                layer0.append((last && even) ? " " : "");
            }
            layer0.append("\n");
        }
    }

    // TERRAIN_GRASS
    // TERRAIN_PLAINS
    // TERRAIN_DESERT
    // TERRAIN_TUNDRA
    // TERRAIN_SNOW
    // TERRAIN_COAST
    // TERRAIN_OCEAN
    //
    // FEATURE_ICE
    // FEATURE_JUNGLE
    // FEATURE_MARSH
    // FEATURE_OASIS
    // FEATURE_FLOOD_PLAINS
    // FEATURE_FOREST
    // FEATURE_FALLOUT
    // FEATURE_CRATER
    // FEATURE_FUJI
    // FEATURE_MESA
    // FEATURE_REEF
    // FEATURE_VOLCANO
    // FEATURE_GIBRALTAR
    // FEATURE_GEYSER
    //
    // RESOURCE_IRON
    // RESOURCE_HORSE
    // RESOURCE_COAL
    // RESOURCE_OIL
    // RESOURCE_ALUMINUM
    // RESOURCE_URANIUM
    // RESOURCE_WHEAT
    // RESOURCE_COW
    // RESOURCE_SHEEP
    // RESOURCE_DEER
    // RESOURCE_BANANA
    // RESOURCE_FISH
    // RESOURCE_WHALE
    // RESOURCE_PEARLS
    // RESOURCE_GOLD
    // RESOURCE_SILVER
    // RESOURCE_GEMS
    // RESOURCE_MARBLE
    // RESOURCE_IVORY
    // RESOURCE_FUR
    // RESOURCE_DYE
    // RESOURCE_SPICES
    // RESOURCE_SILK
    // RESOURCE_SUGAR
    // RESOURCE_COTTON
    // RESOURCE_WINE
    // RESOURCE_INCENSE

    //  00000320  00 06 ff 00 00 00 04 ff  00 06 ff 00 00 00 04 ff  |................|
    //  000003a0  00 05 ff 00 00 00 04 ff  00 05 ff 00 00 00 04 ff  |................|
    //  000003c0  00 06 ff 00 00 00 04 ff  00 05 ff 00 00 00 04 ff  |................|
    //  000003d0  00 05 ff 00 00 00 04 ff  00 04 ff ff 00 00 02 ff  |................|
    //  000003e0  00 04 ff ff 00 00 02 ff  00 05 ff 00 00 00 04 ff  |................|
    //  000003f0  00 05 ff 00 00 00 04 ff  00 05 ff 00 00 00 04 ff  |................|
    //  00000400  00 06 ff 00 00 00 04 ff  00 06 ff 00 00 00 04 ff  |................|

    private static final Map<String, Character> TILES_MAP = new HashMap<>() {{
        // tiles
        put("00 00 FF 01", 'g');
        put("00 00 FF 02", 'g');
        put("00 00 FF 05", 'g');
        put("00 00 FF FF", 'g');
        put("00 01 FF 01", 'g');
        put("00 01 FF 05", 'g');
        put("00 01 FF FF", 'g');
        put("00 02 FF 03", 'g');
        put("00 02 FF 04", 'g');
        put("00 02 FF FF", 'g');
        put("00 03 FF 05", 'g');
        put("00 03 FF FF", 'g');
        put("00 04 FF 00", 'g');
        put("00 04 FF 05", 'g');
        put("00 04 FF FF", 'g');
        put("00 05 FF 00", 'g');
        put("00 05 FF FF", 'g');
        put("00 06 FF 00", '.');
        put("00 06 FF FF", '.');

        // options
        put("00 00 00 FF", '.');
        put("00 00 02 FF", '.');
        put("00 00 03 FF", '.');
        put("00 00 04 FF", '.');
        put("00 01 02 FF", '.');
        put("00 01 03 FF", '.');
        put("00 01 04 FF", '.');
        put("00 02 02 FF", '.');
        put("00 02 03 FF", '.');
        put("00 02 04 FF", '.');
        put("01 00 02 FF", '.');
        put("01 00 03 FF", '.');
        put("01 01 02 FF", '.');
        put("01 01 03 FF", '.');
        put("02 01 03 FF", '.');
        put("04 00 02 FF", '.');
        put("04 00 03 FF", '.');
        put("04 01 03 FF", '.');
        put("04 02 02 FF", '.');
        put("04 02 03 FF", '.');
        put("09 00 02 FF", '.');
        put("09 00 03 FF", '.');
        put("0B 00 02 FF", '.');
        put("0B 00 03 FF", '.');
        put("0B 00 04 FF", '.');
        put("0B 01 02 FF", '.');
        put("0C 01 02 FF", '.');
        put("11 00 03 FF", '.');
        put("12 00 03 FF", '.');
        put("12 01 02 FF", '.');
        put("13 00 02 FF", '.');
        put("13 00 03 FF", '.');
        put("13 01 03 FF", '.');
        put("16 00 02 FF", '.');
        put("16 00 03 FF", '.');
        put("16 01 02 FF", '.');
        put("17 00 02 FF", '.');
        put("1F 00 02 FF", '.');
        put("1F 00 03 FF", '.');
        put("20 00 03 FF", '.');
        put("24 00 03 FF", '.');
        put("26 00 03 FF", '.');
    }};

    private static char read(byte[] data, int x, int y, int width, int height, int off) {
        int index = 0x320 + (y * width * 8) + (x * 8) + off;
        String code = String.format("%02X %02X %02X %02X", data[index], data[index + 1], data[index + 2], data[index + 3]);

        Character ch = TILES_MAP.get(code);
        if (ch == null) {
            throw new IllegalArgumentException("No value for '" + code + "' at index = " + String.format("%04X", index));
        }

        return ch;
    }
}
