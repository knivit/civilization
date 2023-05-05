package com.tsoft.civilization.unit.civil.settlers.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.catalog.settlers.action.BuildCityAction;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.catalog.settlers.action.BuildCityAction.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY;
import static com.tsoft.civilization.unit.catalog.settlers.action.BuildCityAction.CITY_BUILT;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildCityActionTest {

    @Test
    public void buildCity1() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers1", new Point(1, 1))
        );

        // Build a city
        assertThat(BuildCityAction.buildCity(world.get("settlers1")))
            .isEqualTo(CITY_BUILT);

        // check the city
        assertEquals(1, russia.getCityService().size());
        City city = russia.getCityService().getAny();

        // city's tiles
        assertEquals(7, city.getTileService().getTerritory().size());

        // settlers must be destroyed
        assertEquals(0, russia.getUnitService().size());

        // A city can't be build on a tile where another city is built
        Settlers settlers2 = UnitFactory.newInstance(russia, Settlers.CLASS_UUID);
        assertTrue(russia.getUnitService().addUnit(settlers2, new Point(1, 1)));
        world.nextYear();

        assertEquals(CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY, BuildCityAction.buildCity(settlers2));
    }

    @Test
    public void buildCity2() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . g"));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers1", new Point(1, 1))
        );

        // check 1
        // Build a city
        assertThat(BuildCityAction.buildCity(world.get("settlers1")))
            .isEqualTo(CITY_BUILT);

        // check 2
        // A city can not be build less than 4 tiles away from other city
        Settlers settlers2 = UnitFactory.newInstance(russia, Settlers.CLASS_UUID);
        assertTrue(russia.getUnitService().addUnit(settlers2, new Point(2, 3)));
        world.nextYear();

        assertEquals(CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY, BuildCityAction.buildCity(settlers2));
    }

    @Test
    public void buildCity3() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 3 4 5 6 7 ",
            "-+----------------",
            "0|g . . . . . . . ",
            "1| . . . . . . . .",
            "2|. . . . . . . . ",
            "3| . . . . . . . g"));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers1", new Point(0, 0))
        );

        // check 1
        // Build a city
        assertThat(BuildCityAction.buildCity(world.get("settlers1")))
            .isEqualTo(CITY_BUILT);

        // check 2
        // a city can not be build less than 4 tiles away from other city
        // (cities looks like far away, but don't forget - the map is cyclic)
        Settlers settlers2 = UnitFactory.newInstance(russia, Settlers.CLASS_UUID);
        assertTrue(russia.getUnitService().addUnit(settlers2, new Point(7, 3)));
        world.nextYear();

        assertEquals(CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY, BuildCityAction.buildCity(settlers2));
    }

    @Test
    public void buildCity4() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 3 4 5 6 7 ",
            "-+----------------",
            "0|. . . . . . . . ",
            "1| . . . . . . . .",
            "2|. . g . . . . . ",
            "3| . . . . . . . .",
            "4|. . . . . . g . ",
            "5| . . . . . . . .",
            "6|. . . . . . . . ",
            "7| . . . . . . . ."));

        Civilization civilization = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers1", new Point(2, 2))
        );

        // check 1
        // Build a city
        assertThat(BuildCityAction.buildCity(world.get("settlers1")))
            .isEqualTo(CITY_BUILT);

        // check 2
        // Build a city - it's OK now
        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.getUnitService().addUnit(settlers2, new Point(6, 4)));
        world.nextYear();

        assertEquals(CITY_BUILT, BuildCityAction.buildCity(settlers2));
    }
}
