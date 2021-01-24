package com.tsoft.civilization.action.unit.settlers;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.settlers.action.BuildCityAction;
import com.tsoft.civilization.unit.civil.settlers.action.SettlersActionResults;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildCityActionTest {
    @Test
    public void buildCity1() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // Build a city
        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(1, 1)));
        assertEquals(SettlersActionResults.CITY_BUILT, BuildCityAction.buildCity(settlers1));

        // check the city
        assertEquals(1, civilization.cities().size());
        City city = civilization.cities().getAny();

        // city's tiles
        assertEquals(7, city.getLocations().size());

        // settlers must be destroyed
        assertEquals(0, civilization.units().size());

        // A city can't be build on a tile where another city is built
        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(1, 1)));
        assertEquals(SettlersActionResults.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY, BuildCityAction.buildCity(settlers2));
    }

    @Test
    public void buildCity2() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . g");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // check 1
        // Build a city
        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(1, 1)));
        assertEquals(SettlersActionResults.CITY_BUILT, BuildCityAction.buildCity(settlers1));

        // check 2
        // A city can not be build less than 4 tiles away from other city
        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(2, 3)));
        assertEquals(SettlersActionResults.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY, BuildCityAction.buildCity(settlers2));
    }

    @Test
    public void buildCity3() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 3 4 5 6 7 ",
                "-+----------------",
                "0|g . . . . . . . ",
                "1| . . . . . . . .",
                "2|. . . . . . . . ",
                "3| . . . . . . . g");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // check 1
        // Build a city
        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(0, 0)));
        assertEquals(SettlersActionResults.CITY_BUILT, BuildCityAction.buildCity(settlers1));

        // check 2
        // a city can not be build less than 4 tiles away from other city
        // (cities looks like far away, but don't forget - the map is cyclic)
        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(7, 3)));
        assertEquals(SettlersActionResults.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY, BuildCityAction.buildCity(settlers2));
    }

    @Test
    public void buildCity4() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 3 4 5 6 7 ",
                "-+----------------",
                "0|. . . . . . . . ",
                "1| . . . . . . . .",
                "2|. . g . . . . . ",
                "3| . . . . . . . .",
                "4|. . . . . . g . ",
                "5| . . . . . . . .",
                "6|. . . . . . . . ",
                "7| . . . . . . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // check 1
        // Build a city
        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(2, 2)));
        assertEquals(SettlersActionResults.CITY_BUILT, BuildCityAction.buildCity(settlers1));

        // check 2
        // Build a city - it's OK now
        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(6, 4)));
        assertEquals(SettlersActionResults.CITY_BUILT, BuildCityAction.buildCity(settlers2));
    }
}
