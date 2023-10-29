package com.tsoft.civilization.unit;

import com.tsoft.civilization.util.json.JsonLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonUnitCatalog {

    private static final Map<String, JsonUnit> JSON_UNIT_CATALOG = new HashMap<>();

    public static void load(String jsonFileName) {
        List<JsonUnit> list = JsonLoader.loadFromFile(JsonUnit.class, jsonFileName);

        for (JsonUnit unit : list) {
            JSON_UNIT_CATALOG.put(unit.getName(), unit);
        }
    }

    public static Stream<Map.Entry<String, JsonUnit>> entries() {
        return JSON_UNIT_CATALOG.entrySet().stream();
    }

    public static JsonUnit get(String name) {
        return JSON_UNIT_CATALOG.get(name);
    }
}
