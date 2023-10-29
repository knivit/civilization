package com.tsoft.civilization.tile.terrain;

import com.tsoft.civilization.util.json.JsonLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonTerrainCatalog {

    private static final Map<String, JsonTerrain> JSON_TERRAIN_CATALOG = new HashMap<>();

    public static void load(String jsonFileName) {
        List<JsonTerrain> list = JsonLoader.loadFromFile(JsonTerrain.class, jsonFileName);

        for (JsonTerrain terrain : list) {
            JSON_TERRAIN_CATALOG.put(terrain.getName(), terrain);
        }
    }

    public static Stream<Map.Entry<String, JsonTerrain>> entries() {
        return JSON_TERRAIN_CATALOG.entrySet().stream();
    }

    public static JsonTerrain get(String name) {
        return JSON_TERRAIN_CATALOG.get(name);
    }
}
