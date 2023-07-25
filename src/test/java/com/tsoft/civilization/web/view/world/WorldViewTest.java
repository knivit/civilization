package com.tsoft.civilization.web.view.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.catalog.farm.farm.Farm;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class WorldViewTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void worldView() throws Exception {
        MockWorld world = MockWorld.of(MockTilesMap.of(3,
            " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------", "-+------",
            "0|g g g ", "0|h h . ", "0|f . . ",
            "1| g g g", "1| h h .", "1| f . ."));

        Civilization myCivilization = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(0, 0))
            .warriors("warriors", new Point(0, 0))
            .workers("workers", new Point(0, 0))
            .settlers("settlers", new Point(1, 0))
            .improvement("farm", new Point(0, 1), Farm.CLASS_UUID)
        );

        String expected = """
            {
                "width":"3","height":"2",
                "tiles":[
                    {"name":"Grassland","features":[{"name":"Hill"},{"name":"Forest"}]},
                    {"name":"Grassland","features":[{"name":"Hill"}]},
                    {"name":"Grassland"},
                    {"name":"Grassland","features":[{"name":"Hill"},{"name":"Forest"}],"improvements":["Farm"]},
                    {"name":"Grassland","features":[{"name":"Hill"}]},
                    {"name":"Grassland"}],
                "civilizations":[{"name":"Russia"}],
                "units":[
                    {"col":"0","row":"0","name":"Warriors","civ":"Russia"},
                    {"col":"0","row":"0","name":"Workers","civ":"Russia"},
                    {"col":"1","row":"0","name":"Settlers","civ":"Russia"}],
                "cities":[
                    {"col":"0","row":"0","name":"Moscow","civilization":"Russia","isCapital":"true","locations":[
                        {"col":"0","row":"0"},
                        {"col":"0","row":"1"},
                        {"col":"1","row":"0"},
                        {"col":"2","row":"0"},
                        {"col":"2","row":"1"}]}]
            }
            """;

        JsonBlock worldBlock = world.getView().getJson(myCivilization);

        assertThat(objectMapper.readTree(worldBlock.getText()))
            .isEqualTo(objectMapper.readTree(expected));
    }
}
