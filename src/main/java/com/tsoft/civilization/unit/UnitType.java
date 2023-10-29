package com.tsoft.civilization.unit;

import com.tsoft.civilization.util.json.JsonLoader;

import java.util.List;

public class UnitType {

    public static class JsonUnitType {
        private String name;
        private String movementType;
    }

    public static class JsonUnitTypes {
        private List<JsonUnitType> list;
    }

    public static void loadFromJson(String jsonFileName) {
        List<JsonUnitType> list = JsonLoader.loadFromFile(JsonUnitType.class, "/assets/assets/jsons/Civ V - Gods & Kings/UnitTypes.json");

    }
}
