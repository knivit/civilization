package com.tsoft.civilization.web.view.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.GameServerTest;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.world.Civilization;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorldViewTest {
    @BeforeClass
    public static void classSetUp() {
        DefaultLogger.createLogger(WorldViewTest.class.getSimpleName());
    }

    @Test
    public void worldView() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|g g g ", "0|h h . ", "0|f . . ",
                "1| g g g", "1| h h .", "1| f . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);

        // a city with two units in it
        City city1 = new City(c1, new Point(0, 0));
        Warriors warriors = UnitFactory.newInstance(Warriors.INSTANCE, c1, new Point(0, 0));
        Workers workers = UnitFactory.newInstance(Workers.INSTANCE, c1, new Point(0, 0));
        Settlers settlers = UnitFactory.newInstance(Settlers.INSTANCE, c1, new Point(1, 0));

        JSONBlock worldBlock = mockWorld.getView().getJSON();
        assertEquals("{\"width\":\"3\",\"height\":\"2\",\"tiles\":[{\"name\":\"g\",\"features\":[{\"name\":\"h\"},{\"name\":\"f\"}]},{\"name\":\"g\",\"features\":[{\"name\":\"h\"}]},{\"name\":\"g\",\"features\":[]},{\"name\":\"g\",\"features\":[{\"name\":\"h\"},{\"name\":\"f\"}]},{\"name\":\"g\",\"features\":[{\"name\":\"h\"}]},{\"name\":\"g\",\"features\":[]}],\"civilizations\":[{\"name\":\"Russia\"}],\"units\":[{\"col\":\"0\",\"row\":\"0\",\"name\":\"Warriors\",\"civ\":\"Russia\"},{\"col\":\"0\",\"row\":\"0\",\"name\":\"Workers\",\"civ\":\"Russia\"},{\"col\":\"1\",\"row\":\"0\",\"name\":\"Settlers\",\"civ\":\"Russia\"}],\"cities\":[{\"col\":\"0\",\"row\":\"0\",\"name\":\"Moscow\",\"civilization\":\"Russia\",\"isCapital\":\"true\",\"locations\":[{\"col\":\"0\",\"row\":\"1\"},{\"col\":\"0\",\"row\":\"0\"},{\"col\":\"1\",\"row\":\"0\"},{\"col\":\"2\",\"row\":\"0\"},{\"col\":\"2\",\"row\":\"1\"}]}]}", worldBlock.getText());

        GameServerTest jsClient = new GameServerTest();

        jsClient.parseJSON(worldBlock.getText());
    }
}
