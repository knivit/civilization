package com.tsoft.civilization.web.view.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.TestGameServer;
import com.tsoft.civilization.web.TestJavaScriptResult;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.world.Civilization;
import org.junit.Test;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

public class WorldViewTest {
    @Test
    public void worldView() throws ScriptException {
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

        TestGameServer jsClient = new TestGameServer();

        TestJavaScriptResult jsonObj = jsClient.parseJSON(worldBlock.getText());
        assertEquals(6, jsonObj.size());
        assertEquals(Integer.valueOf(3), jsonObj.getInt("width"));
        assertEquals(Integer.valueOf(2), jsonObj.getInt("height"));

        TestJavaScriptResult tilesObj = jsonObj.getChild("tiles");
        assertEquals(3 * 2, tilesObj.size());
        assertEquals("g", tilesObj.getChild("0").getString("name"));
        assertEquals(2, tilesObj.getChild("0").getChild("features").size()); // h + f
        assertEquals("g", tilesObj.getChild("1").getString("name"));
        assertEquals(1, tilesObj.getChild("1").getChild("features").size()); // h

        TestJavaScriptResult civilizationsObj = jsonObj.getChild("civilizations");
        assertEquals(1, civilizationsObj.size());
        assertEquals("Russia", civilizationsObj.getChild("0").getString("name"));

        TestJavaScriptResult unitsObj = jsonObj.getChild("units");
        assertEquals(3, unitsObj.size());

        TestJavaScriptResult citiesObj = jsonObj.getChild("cities");
        assertEquals(1, citiesObj.size());
    }
}
