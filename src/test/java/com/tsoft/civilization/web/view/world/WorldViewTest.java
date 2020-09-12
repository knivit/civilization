package com.tsoft.civilization.web.view.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.civil.Settlers.Settlers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorldViewTest {
    @Test
    public void worldView() throws Exception {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|g g g ", "0|h h . ", "0|f . . ",
                "1| g g g", "1| h h .", "1| f . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);

        // a city with two units in it
        City city1 = new City(c1, new Point(0, 0));
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.addUnit(warriors, new Point(0, 0));
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c1.addUnit(workers, new Point(0, 0));
        Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID);
        c1.addUnit(settlers, new Point(1, 0));

        JSONBlock worldBlock = mockWorld.getView().getJSON();
        //assertEquals("{\"width\":\"3\",\"height\":\"2\",
        // \"tiles\":[
        //   {\"name\":\"g\",
        //    \"features\":[
        //       {\"name\":\"h\"},
        //       {\"name\":\"f\"}]},
        //   {\"name\":\"g\",
        //    \"features\":[
        //      {\"name\":\"h\"}]},{\"name\":\"g\",\"features\":[]},{\"name\":\"g\",\"features\":[{\"name\":\"h\"},{\"name\":\"f\"}]},{\"name\":\"g\",\"features\":[{\"name\":\"h\"}]},{\"name\":\"g\",\"features\":[]}],
        //
        // \"civilizations\":[{\"name\":\"Russia\"}],
        //
        // \"units\":[
        //   {\"col\":\"0\",\"row\":\"0\",\"name\":\"Warriors\",\"civ\":\"Russia\"},
        //   {\"col\":\"0\",\"row\":\"0\",\"name\":\"Workers\",\"civ\":\"Russia\"},
        //   {\"col\":\"1\",\"row\":\"0\",\"name\":\"Settlers\",\"civ\":\"Russia\"}],
        //
        // \"cities\":[{\"col\":\"0\",\"row\":\"0\",\"name\":\"Moscow\",\"civilization\":\"Russia\",\"isCapital\":\"true\",\"locations\":[{\"col\":\"0\",\"row\":\"1\"},{\"col\":\"0\",\"row\":\"0\"},{\"col\":\"1\",\"row\":\"0\"},{\"col\":\"2\",\"row\":\"0\"},{\"col\":\"2\",\"row\":\"1\"}]}]}", worldBlock.getText());

        JsonNode jsonObj = new ObjectMapper().readTree(worldBlock.getText());
        assertNotNull(jsonObj);
        assertEquals(6, jsonObj.size());
        assertEquals(3, jsonObj.get("width").asInt());
        assertEquals(2, jsonObj.get("height").asInt());

        JsonNode tilesObj = jsonObj.get("tiles");
        assertEquals(3 * 2, tilesObj.size());
        assertEquals("g", tilesObj.get(0).get("name").asText());
        assertEquals(2, tilesObj.get(0).get("features").size()); // h + f
        assertEquals("g", tilesObj.get(1).get("name").asText());
        assertEquals(1, tilesObj.get(1).get("features").size()); // h
        assertEquals("g", tilesObj.get(2).get("name").asText());
        assertEquals(0, tilesObj.get(2).get("features").size()); // <empty>
        assertEquals("g", tilesObj.get(3).get("name").asText());
        assertEquals(2, tilesObj.get(3).get("features").size()); // h + f
        assertEquals("g", tilesObj.get(4).get("name").asText());
        assertEquals(1, tilesObj.get(4).get("features").size()); // h
        assertEquals("g", tilesObj.get(5).get("name").asText());
        assertEquals(0, tilesObj.get(5).get("features").size()); // <empty>

        JsonNode civilizationsObj = jsonObj.get("civilizations");
        assertEquals(1, civilizationsObj.size());
        assertEquals("Russia", civilizationsObj.get(0).get("name").asText());

        JsonNode unitsObj = jsonObj.get("units");
        assertEquals(3, unitsObj.size());
        assertEquals("Warriors", unitsObj.get(0).get("name").asText());
        assertEquals("Russia", unitsObj.get(0).get("civ").asText());

        JsonNode citiesObj = jsonObj.get("cities");
        assertEquals(1, citiesObj.size());
    }
}
