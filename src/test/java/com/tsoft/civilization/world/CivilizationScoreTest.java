package com.tsoft.civilization.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CitySupplyStrategy;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.world.economic.CivilizationScoreMock;
import com.tsoft.civilization.util.Point;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CivilizationScoreTest {
 //   @Test
    public void noCitiesScore() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");

        MockWorld world = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(world, 0);

        world.step();
        assertTrue(new CivilizationScoreMock(c1, 0, 0, 0, 0, 0, 0, 0).isEqualTo(c1.getCivilizationScore()));
    }

    @Test
    public void oneCityOneTileScore() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");

        MockWorld world = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(world, 0);
        City city = new City(c1, new Point(1, 1));

        // Step 1
        // food | prod | gold | science | culture | happiness | population | produced/consumed by
        //    0 |    0 |    0 |       0 |       0 |        -1 |          1 | City
        //    0 |    3 |    3 |       3 |       1 |           |            | Palace
        //    2 |    0 |    0 |       0 |       0 |           |            | grassland
        //   -1 |    0 |    0 |       0 |       0 |           |            | 1 citizen
        // -----------------------------------------------------------------
        //    1 |    3 |    3 |       3 |       1 |        -1 |          1 |
        world.step();
        CivilizationScoreMock c1ScoreMock = new CivilizationScoreMock(c1, 1, 3, 3, 3, 1, -1, 1);
        assertTrue(c1ScoreMock.isEqualTo(c1.getCivilizationScore()));

        // Step 2
        // food | prod | gold | science | culture | happiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |        -1 |          1 | Palace
        //    2 |    0 |    0 |       0 |       0 |           |            | grassland
        //   -1 |    0 |    0 |       0 |       0 |           |            | 1 citizen
        //    0 |    0 |    3 |       3 |       1 |           |            | remains from the previous step
        // -----------------------------------------------------------------
        //    1 |    3 |    6 |       6 |       2 |        -1 |          1 |
        world.step();
        c1ScoreMock = new CivilizationScoreMock(c1, 1, 3, 6, 6, 2, -1, 1);
        assertTrue(c1ScoreMock.isEqualTo(c1.getCivilizationScore()));
    }

//    @Test
    public void oneCityAllTypesOfTilesScore() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 3 4 ", " |0 1 2 3 4 ", " |0 1 2 3 4 ",
                "-+----------", "-+----------", "-+----------",
                "0|. g d i . ", "0|. . . . . ", "0|. . . . . ",
                "1| l M p s t", "1| . . . . .", "1| . . . . .",
                "2|g g g g g ", "2|n f h h m ", "2|. . . f . ",
                "3| . g p . .", "3| . j o . .", "3| . . . . .");

        MockWorld world = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(world, 0);
        City city = new City(c1, new Point(2, 1));

        // add all other tiles
        Collection<Point> locations = mockTilesMap.getLocationsAround(new Point(2, 1), 3);
        city.addLocations(locations);

        // add citizens to work on the tiles
        // four of them will be unemployed
        for (int i = 0; i < 14; i ++) {
            city.addCitizen(null);
        }
        assertEquals(15, city.getCitizenCount());
        assertEquals(11, city.getCitizenLocations().size());

        // food | prod | gold | science | culture | happiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |        -1 |          1 | Palace
        //    2 |    0 |    0 |         |         |           |            | grassland (1, 0)
        //    0 |    0 |    0 |         |         |           |            | desert (2, 0)
        //    0 |    0 |    0 |         |         |           |            | ice (3, 0)
        //    2 |    0 |    1 |         |         |           |            | lake (0, 1)
        //    0 |    0 |    0 |         |         |           |            | mountain (1, 1)
        //    1 |    1 |    0 |         |         |           |            | plain (2, 1)
        //    0 |    0 |    0 |         |         |           |            | snow (3, 1)
        //    1 |    0 |    0 |         |         |           |            | tundra (4, 1)
        //    4 |    0 |    0 |         |         |           |            | grassland + flood plain (0, 2)
        //    1 |    1 |    0 |         |         |           |            | grassland + forest (1, 2)
        //    0 |    2 |    0 |         |         |           |            | grassland + hill (2, 2)
        //    1 |    1 |    0 |         |         |           |            | grassland + hill + forest (3, 2)
        //    1 |    0 |    0 |         |         |           |            | grassland + marsh (4, 2)
        //    2 |   -1 |    0 |         |         |           |            | grassland + jungle (1, 3)
        //    4 |    1 |    1 |         |         |           |            | plain + oasis (2, 3)
        //  -15 |    0 |    0 |         |         |           |            | 15 citizens
        // -----------------------------------------------------------------
        //   4  |    8 |    5 |       3 |       1 |        -1 |          1 |
        world.step();
        CivilizationScoreMock c1ScoreMock = new CivilizationScoreMock(c1, 4, 8, 5, 3, 1, -1, 1);
        assertTrue(c1ScoreMock.isEqualTo(c1.getCivilizationScore()));

        // check the citizens don't work on tiles with empty supply
        assertTrue(Collections.disjoint(city.getCitizenLocations(), Arrays.asList(new Point(2, 0), new Point(3, 0), new Point(1, 1), new Point(3, 1))));
    }

 //   @Test
    public void maxSupplyStrategies() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|. g . ", "0|. h . ", "0|. . . ",
                "1| g g .", "1| m . .", "1| . . .",
                "2|. g . ", "2|. h . ", "2|. f . ",
                "3| . . .", "3| . . .", "3| . . .");

        MockWorld world = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(world, 0);
        City city = new City(c1, new Point(1, 1));
        city.addCitizen(null);

        // Step 1
        // food | prod | gold | science | culture | happiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |        -1 |          1 | Palace
        //    0 |    2 |    0 |         |         |           |            | grassland + hill (1, 0) - one citizen must work here
        //    1 |    1 |    0 |         |         |           |            | grassland + hill + forest (1, 2) - another citizen
        //   -2 |    0 |    0 |         |         |           |            | 2 citizens
        // -----------------------------------------------------------------
        //   -1 |    6 |    3 |       3 |       1 |        -1 |          1 |
        CivilizationScoreMock c1ScoreMock = new CivilizationScoreMock(c1, -1, 6, 3, 3, 1, -1, 1);

        city.setSupplyStrategy(CitySupplyStrategy.MAX_PRODUCTION);
        world.step();
        assertTrue(city.getCitizenLocations().contains(new Point(1, 0)));
        assertTrue(city.getCitizenLocations().contains(new Point(1, 2)));
        assertTrue(c1ScoreMock.isEqualTo(c1.getCivilizationScore()));

        // Step 2
        // food | prod | gold | science | culture | happiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |        -1 |          1 | Palace
        //    2 |    0 |    0 |         |         |           |            | grassland (1, 1) - one citizen must work here
        //    1 |    1 |    0 |         |         |           |            | grassland + hill + forest (1, 2) - another citizen (grassland + marsh (0, 1) don't give a production)
        //   -2 |    0 |    0 |         |         |           |            | 2 citizens
        //   -1 |    6 |    3 |       3 |       1 |           |            | from previous step
        // -----------------------------------------------------------------
        //    0 |   10 |    6 |       6 |       2 |        -1 |          1 |
        c1ScoreMock = new CivilizationScoreMock(c1, 0, 10, 6, 6, 2, -1, 1);

        city.setSupplyStrategy(CitySupplyStrategy.MAX_FOOD);
        world.step();
        assertTrue(city.getCitizenLocations().contains(new Point(1, 1)));
        assertTrue(city.getCitizenLocations().contains(new Point(1, 2)));
        assertTrue(c1ScoreMock.isEqualTo(c1.getCivilizationScore()));
    }
}
