package com.tsoft.civilization.web.view.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.*;

public class WorldViewTest {

    @Test
    public void worldView() throws Exception {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 3,
            " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------", "-+------",
            "0|g g g ", "0|h h . ", "0|f . . ",
            "1| g g g", "1| h h .", "1| f . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(0, 0))
            .warriors("warriors", new Point(0, 0))
            .workers("workers", new Point(0, 0))
            .settlers("settlers", new Point(1, 0))
        );

        JsonBlock worldBlock = world.getView().getJson();
        JsonNode jsonObj = new ObjectMapper().readTree(worldBlock.getText());
        assertNotNull(jsonObj);
        assertEquals(3 * 2, jsonObj.size());
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
