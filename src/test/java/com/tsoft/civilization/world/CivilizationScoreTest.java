package com.tsoft.civilization.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CitySupplyStrategy;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.web.render.MapRender;
import com.tsoft.civilization.web.render.WorldRender;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.economic.SupplyMock;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.*;

public class CivilizationScoreTest {
    private static final MapRender mapRender = new MapRender(CivilizationScoreTest.class);
    private static final WorldRender worldRender = new WorldRender(CivilizationScoreTest.class);

    @Test
    public void noCitiesScore() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");
        mapRender.createPng(map);

        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);
        worldRender.createPng(world);

        world.move();
        assertTrue(SupplyMock.equals(Supply.EMPTY_SUPPLY, c1.calcSupply()));
    }

    @Test
    public void oneCityOneTileScore() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");
        mapRender.createPng(map);

        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);
        City city = c1.createCity(new Point(1, 1));

        // Step 1
        // food | prod | gold | science | culture | happiness | unhappiness | population | produced/consumed by
        //      |    3 |    3 |       3 |       1 |           |             |            | Palace
        //    2 |      |      |         |         |           |             |            | grassland
        //   -1 |      |      |       1 |         |           |           1 |          1 | 1 citizen
        // -------------------------------------------------------------------------------
        //    1 |    3 |    3 |       4 |       1 |           |           1 |          1 |
        world.move();
        assertTrue(SupplyMock.equals("F1 P3 G3 S4 C1 H0 U1 O1", c1.calcSupply()));

        // Step 2
        // food | prod | gold | science | culture | happiness | unhappiness | population | produced/consumed by
        //      |    3 |    3 |       3 |       1 |           |             |            | Palace
        //    2 |      |      |         |         |           |             |            | grassland
        //   -1 |      |      |       1 |         |           |           1 |          1 | 1 citizen
        // -------------------------------------------------------------------------------
        //    1 |    3 |    3 |       4 |       1 |           |           1 |            |
        // -------------------------------------------------------------------------------
        //    1 |    3 |    3 |       4 |       1 |           |           1 |          1 | previous step
        // --------------------------------------------------------------------------------
        //    2 |    6 |    6 |       6 |       2 |           |           2 |          1 |
        world.move();
        assertTrue(SupplyMock.equals("F1 P3 G3 S4 C1 H0 U1 O1", c1.calcSupply()));
        assertTrue(SupplyMock.equals("F2 P6 G6 S8 C2 H0 U2 O1", c1.getSupply()));
    }

    @Test
    public void oneCityAllTypesOfTilesScore() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 3,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------", "-+----------",
            "0|. g d p . ", "0|. . . i . ", "0|. . . . . ",
            "1| l g p s t", "1| . M . . .", "1| . . . . .",
            "2|g g g g g ", "2|n f h h m ", "2|. . . f . ",
            "3| . g p . .", "3| . j o . .", "3| . . . . .");

        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);
        City city = civilization.createCity(new Point(2, 1));
        worldRender.createHtml(world, civilization);

        // add all other tiles
        Collection<Point> locations = map.getLocationsAround(new Point(2, 1), 3);
        city.addLocations(locations);

        // add citizens to work on the tiles
        // four of them will be unemployed
        for (int i = 0; i < 14; i ++) {
            city.addCitizen();
        }
        assertEquals(15, city.getCitizenCount());
        assertEquals(11, city.getCitizenLocations().size());

        // food | prod | gold | science | culture | happiness | unhappiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |           |          1  |          1 | Palace
        //    2 |    0 |    0 |         |         |           |             |            | grassland (1, 0)
        //    0 |    0 |    0 |         |         |           |             |            | desert (2, 0)
        //    0 |    0 |    0 |         |         |           |             |            | ice (3, 0)
        //    2 |    0 |    1 |         |         |           |             |            | lake (0, 1)
        //    0 |    0 |    0 |         |         |           |             |            | mountain (1, 1)
        //    1 |    1 |    0 |         |         |           |             |            | plain (2, 1)
        //    0 |    0 |    0 |         |         |           |             |            | snow (3, 1)
        //    1 |    0 |    0 |         |         |           |             |            | tundra (4, 1)
        //    4 |    0 |    0 |         |         |           |             |            | grassland + flood plain (0, 2)
        //    1 |    1 |    0 |         |         |           |             |            | grassland + forest (1, 2)
        //    0 |    2 |    0 |         |         |           |             |            | grassland + hill (2, 2)
        //    1 |    1 |    0 |         |         |           |             |            | grassland + hill + forest (3, 2)
        //    1 |    0 |    0 |         |         |           |             |            | grassland + marsh (4, 2)
        //    2 |   -1 |    0 |         |         |           |             |            | grassland + jungle (1, 3)
        //    4 |    1 |    1 |         |         |           |             |            | plain + oasis (2, 3)
        //  -15 |    0 |    0 |         |         |           |             |            | 15 citizens
        // -------------------------------------------------------------------------------
        //   4  |    8 |    5 |       3 |       1 |           |           1 |          1 |
        world.move();
        assertTrue(SupplyMock.equals("F4 P8 G5 S3 C1 H0 U1 O1", civilization.calcSupply()));

        // check the citizens don't work on tiles with empty supply
        assertTrue(Collections.disjoint(city.getCitizenLocations(), Arrays.asList(new Point(2, 0), new Point(3, 0), new Point(1, 1), new Point(3, 1))));
    }

    @Test
    public void maxSupplyStrategies() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------",
                "0|. g . ", "0|. h . ",
                "1| g g .", "1| m f .",
                "2|. g . ", "2|. n . ",
                "3| . . .", "3| . . .");
        mapRender.createPng(map);

        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);
        City city = c1.createCity(new Point(1, 1));
        city.addCitizen();

        // Step 1
        // food | prod | gold | science | culture | happiness | unhappiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |           |             |            | Palace
        //    0 |    2 |    0 |         |         |           |             |            | grassland + hill (1, 0) - citizen 1 work here (hills give +2 production)
        //    1 |    1 |    0 |         |         |           |             |            | grassland + forest (1, 1) - citizen 2 here (forest gives +1 production)
        //   -2 |    0 |    0 |       2 |         |           |           2 |          2 | 2 citizens
        // -------------------------------------------------------------------------------
        //   -1 |    6 |    3 |       5 |       1 |           |           2 |          2 |
        city.setSupplyStrategy(CitySupplyStrategy.MAX_PRODUCTION);
        world.move();
        assertEquals(Arrays.asList(new Point(1, 0), new Point(1, 1)), city.getCitizenLocations());
        assertTrue(SupplyMock.equals("F-1 P6 G3 S5 C1 H0 U2 O2", c1.calcSupply()));

        // Step 2
        // food | prod | gold | science | culture | unhappiness | population | produced/consumed by
        //    0 |    3 |    3 |       5 |       1 |             |            | Palace
        //    4 |      |      |         |         |             |            | (1, 2): grassland (+2 food) + flood plain (+2 food) - one citizen must work here
        //    1 |      |      |         |         |             |            | (0, 1): grassland (+2 food) + marsh (-1 food)  - another here, as forest is blocking the supply and return (+1 food)
        //   -2 |      |      |         |         |           2 |          2 | 2 citizens
        // -------------------------------------------------------------------
        //    3 |    3 |    3 |       5 |       1 |           2 |          2 | this step
        // -------------------------------------------------------------------
        //   -1 |    6 |    3 |       5 |       1 |           2 |          2 | from previous step
        // -------------------------------------------------------------------
        //    2 |    9 |    6 |      10 |       2 |           4 |          2 | total
        city.setSupplyStrategy(CitySupplyStrategy.MAX_FOOD);
        world.move();
        assertEquals(Arrays.asList(new Point(1, 2), new Point(0, 1)), city.getCitizenLocations());
        assertTrue(SupplyMock.equals("F3 P3 G3 S5 C1 U2 O2", c1.calcSupply()));
        assertTrue(SupplyMock.equals("F2 P9 G6 S10 C2 U4 O2", c1.getSupply()));
    }
}
