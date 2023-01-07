package com.tsoft.civilization.world.generator.map;

import com.tsoft.civilization.world.MapSize;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OriginalMapFileReader {

    public String[] read(String fileName, MapSize mapSize) {
        int width = mapSize.getWidth();
        int height = mapSize.getHeight();

        byte[] data;
        try {
            data = Files.readAllBytes(Path.of(fileName));
        } catch (Exception ex) {
            log.error("Can't read file '{}'", fileName, ex);
            return null;
        }

        List<String>[] layers = new List[] { new ArrayList<>(), new ArrayList<>() };
        StringBuilder[] lines = new StringBuilder[] { new StringBuilder(), new StringBuilder() };

        // 0
        for (int x = 0; x < width; x ++) {
            for (int i = 0; i < lines.length; i ++) {
                lines[i].append((x == 0) ? "  |" : "").append(x).append((x < 10) ? " " : "");
            }
        }
        flush(layers, lines);

        // 1
        for (int x = 0; x < width; x ++) {
            for (int i = 0; i < lines.length; i ++) {
                lines[i].append((x == 0) ? "--+" : "").append("--");
            }
        }
        flush(layers, lines);

        // 2 - original starts from "odd" rows, add dummy line
        for (int i = 0; i < lines.length; i ++) {
            lines[i].append("<swap>");
        }
        flush(layers, lines);

        // 3 etc
        int row = 0;
        for (int y = height - 1; y >= 0; y --, row ++) {
            for (int x = 0; x < width; x ++) {
                boolean even = ((row % 2) == 1);
                boolean first = (x == 0);
                boolean last = (x == width - 1);
                OriginalTile tile = read(data, x, y, width);

                lines[0].append((first && even) ? "" : " ");
                lines[0].append(tile.terrain);
                lines[0].append((last && even) ? " " : "");

                lines[1].append((first && even) ? "" : " ");
                lines[1].append(tile.feature);
                lines[1].append((last && even) ? " " : "");
            }

            flush(layers, lines);
        }

        // swap
        for (int i = 0; i < layers.length; i ++) {
            String swap = layers[i].get(layers[i].size() - 1);
            layers[i].set(2, swap);
            layers[i].remove(layers[i].size() - 1);
        }

        // line numbers
        for (int i = 0; i < 2; i ++) {
            row = 0;
            for (int k = 2; k < layers[i].size(); k++, row++) {
                String line = layers[i].get(k);
                line = ((row < 10) ? " " : "") + row + "|" + line;
                layers[i].set(k, line);
            }
        }

        for (int i = 0; i < 2; i ++) {
            System.out.println(String.join("\n", layers[i]));
            System.out.println();
        }

        List<String> map = new ArrayList<>();
        for (int i = 0; i < layers[0].size(); i ++) {
            for (int k = 0; k < 2; k ++) {
                map.add(layers[k].get(i));
            }
        }

        return map.toArray(new String[0]);
    }

    private void flush(List<String>[] layers, StringBuilder[] lines) {
        for (int i = 0; i < layers.length; i ++) {
            layers[i].add(lines[i].toString());
            lines[i] = new StringBuilder();
        }
    }

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

    private static final Map<String, Character> TERRAIN_MAP = new HashMap<>() {{
            put("00 00", 'g');  // TERRAIN_GRASS
            put("00 01", 'p');  // TERRAIN_PLAINS
            put("00 02", 'd');  // TERRAIN_DESERT
            put("00 03", 't');  // TERRAIN_TUNDRA
            put("00 04", 's');  // TERRAIN_SNOW
            put("00 05", '.');  // TERRAIN_COAST
            put("00 06", '.');  // TERRAIN_OCEAN
        }};

    private static final Map<String, Character> FEATURE_MAP = new HashMap<>() {{
            put("00", 'i');  // FEATURE_ICE
            put("01", 'j');  // FEATURE_JUNGLE
            put("02", 'm');  // FEATURE_MARSH
            put("03", 'o');  // FEATURE_OASIS
            put("04", 'n');  // FEATURE_FLOOD_PLAINS
            put("05", 'f');  // FEATURE_FOREST
            put("06", 'F');  // FEATURE_FALLOUT
            put("07", '.');  // FEATURE_CRATER
            put("08", '.');  // FEATURE_FUJI
            put("09", '.');  // FEATURE_MESA
            put("0A", '.');  // FEATURE_REEF
            put("0B", '.');  // FEATURE_VOLCANO
            put("0C", '.');  // FEATURE_GIBRALTAR
            put("0D", '.');  // FEATURE_GEYSER
            put("FF", '.');  // ??
    }};

    // 00006ea0  00 05 ff ff 00 00 04 ff  00 01 ff ff 00 02 04 ff  |................|
    // 00006eb0  00 01 ff 05 00 01 04 ff  00 01 ff 05 00 01 04 ff  |................|
    // 00006ec0  00 05 ff ff 00 00 04 ff  00 05 ff ff 00 00 04 ff  |................|

    // 00006c20  00 05 ff ff 00 00 04 ff  00 01 ff ff 00 00 04 ff  |................|
    // 00006c30  00 05 ff ff 00 00 04 ff  00 01 ff 05 00 00 04 ff  |................|

    @Getter
    @Builder
    private static class OriginalTile {
        private char terrain;
        private char feature;
        private char resource;
        private char luxury;
    }

    // 8-byte record
    // - 0, 1 - terrain type
    // - 2 - always 0xFF (reserved ?)
    // - 3 - feature
    // - 4 - resource
    // - 5 - 0x00 - plain, 0x01 - hill, 0x02 - mountain
    // - 6 - ?
    // - 7 - ?
    private static OriginalTile read(byte[] data, int x, int y, int width) {
        int index = 0x320 + (y * width * 8) + (x * 8);

        // 0, 1
        String terrain = String.format("%02X %02X", data[index], data[index + 1]);

        // 2
        if (data[index + 2] != -1) {
            throw new IllegalStateException("Invalid format, expected 0xFF at " + String.format("%04X", index + 2) + ", got " + String.format("%02X", data[index + 2]));
        }

        // 3
        String feature = String.format("%02X", data[index + 3]);

        // 4
        String resource = String.format("%02X", data[index + 4]);

        // 5
        String hill = String.format("%02X", data[index + 5]);
        if (!("00".equals(hill) || "01".equals(hill) || "02".equals(hill))) {
            throw new IllegalStateException("Invalid format, expected 0x00 or 0x01 (hill) or 0x02 (mountain) at " + String.format("%04X", index + 5) + ", got " + String.format("%02X", data[index + 5]));
        }

        // 6
        String unknown = String.format("%02X", data[index + 6]);

        // 7
        String unknown2 = String.format("%02X", data[index + 7]);

        char terrainChar = TERRAIN_MAP.get(terrain);
        char featureChar = FEATURE_MAP.get(feature);
        if ("01".equals(hill)) {
            featureChar = 'h';
        }
        if ("02".equals(hill)) {
            featureChar = 'M';
        }

        return OriginalTile.builder()
            .terrain(terrainChar)
            .feature(featureChar)
            .build();
    }
}
