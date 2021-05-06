package com.tsoft.civilization.world;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.HappinessMock;
import com.tsoft.civilization.economic.UnhappinessMock;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.supply.TileSupplyStrategy;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.economic.SupplyMock;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CivilizationScoreTest {

    @Test
    public void noCitiesScore() {
        MockTilesMap map = new MockTilesMap(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        world.move();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(Supply.EMPTY);
    }

    @Test
    public void oneCityOneTileScore() {
        MockTilesMap map = new MockTilesMap(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(1, 1))
        );

        // Step 1
        // food | prod | gold | science | culture | population | produced/consumed by
        //      |    3 |    3 |       3 |       1 |            | Palace
        //    2 |      |      |         |         |            | grassland
        //   -1 |      |      |       1 |         |          1 | 1 citizen
        // -----------------------------------------------------
        //    1 |    3 |    3 |       4 |       1 |          1 |
        world.move();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F1 P3 G3 S4 C1"));

        assertThat(russia.calcHappiness())
            .usingComparator(HappinessMock::compare)
            .isEqualTo(HappinessMock.of("D9 L0 B0 W0 N0 P0 G0"));

        assertThat(russia.calcUnhappiness())
            .usingComparator(UnhappinessMock::compare)
            .isEqualTo(UnhappinessMock.of("C1 A0 U0 P1 T4"));

        // Step 2
        // food | prod | gold | science | culture | population | produced/consumed by
        //      |    3 |    3 |       3 |       1 |            | Palace
        //    2 |      |      |         |         |            | grassland
        //   -1 |      |      |       1 |         |          1 | 1 citizen
        // -----------------------------------------------------
        //    1 |    3 |    3 |       4 |       1 |            |
        // -----------------------------------------------------
        //    1 |    3 |    3 |       4 |       1 |          1 | previous step
        // ------------------------------------------------------
        //    2 |    6 |    6 |       6 |       2 |          1 |
        world.move();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F1 P3 G3 S4 C1"));

        assertThat(russia.getSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F2 P6 G6 S8 C2"));

        assertThat(russia.calcHappiness())
            .usingComparator(HappinessMock::compare)
            .isEqualTo(HappinessMock.of("D9 L0 B0 W0 N0 P0 G0"));

        assertThat(russia.calcUnhappiness())
            .usingComparator(UnhappinessMock::compare)
            .isEqualTo(UnhappinessMock.of("C1 A0 U0 P1 T4"));
    }

    @Test
    public void oneCityAllTypesOfTilesScore() {
        MockTilesMap map = new MockTilesMap(3,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------", "-+----------",
            "0|. g d . . ", "0|. . . i . ", "0|. . . . . ",
            "1| l g p s t", "1| . M . . .", "1| . . . . .",
            "2|g g g g g ", "2|n f h h m ", "2|. . . f . ",
            "3| . g p . .", "3| . j o . .", "3| . . . . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 1))
        );

        City city = world.city("Moscow");

        // add all other tiles
        Collection<Point> locations = map.getLocationsAround(new Point(2, 1), 3);
        city.getTileService().addLocations(locations);
        assertEquals(20, city.getTileService().getLocations().size());

        // add citizens to work on the tiles
        // three of them will be unemployed (ice, mountain and jungle)
        for (int i = 0; i < 4 * 5 - 1; i ++) {
            city.addCitizen();
        }
        assertThat(city.getCitizenCount()).isEqualTo(4 * 5);
        assertThat(city.getCitizenLocations().size()).isEqualTo(4 * 5 - 3);

        world.move();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F2 P12 G10 S20 C1 H0 U17 O20"));

        // check the citizens don't work on tiles with empty supply
        assertTrue(Collections.disjoint(city.getCitizenLocations(), Arrays.asList(
            new Point(3, 0), // ice
            new Point(1, 1), // mountain
            new Point(1, 3)  // jungle
        )));
    }

    @Test
    public void maxSupplyStrategies() {
        MockTilesMap map = new MockTilesMap(2,
            " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------",
            "0|. g . ", "0|. h . ",
            "1| g g .", "1| m f .",
            "2|. g . ", "2|. n . ",
            "3| . . .", "3| . . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(1, 1))
        );

        City city = world.city("Moscow");
        city.addCitizen();

        // Step 1
        // food | prod | gold | science | culture | happiness | unhappiness | population | produced/consumed by
        //    0 |    3 |    3 |       3 |       1 |           |             |            | Palace
        //    0 |    2 |    0 |         |         |           |             |            | grassland + hill (1, 0) - citizen 1 work here (hills give +2 production)
        //    1 |    1 |    0 |         |         |           |             |            | grassland + forest (1, 1) - citizen 2 here (forest gives +1 production)
        //   -2 |    0 |    0 |       2 |         |           |           2 |          2 | 2 citizens
        // -------------------------------------------------------------------------------
        //   -1 |    6 |    3 |       5 |       1 |           |           2 |          2 |
        city.setSupplyStrategy(List.of(TileSupplyStrategy.MAX_PRODUCTION));
        world.move();

        assertEquals(Arrays.asList(new Point(1, 0), new Point(1, 1)), city.getCitizenLocations());

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F-1 P6 G3 S5 C1 H0 U2 O2"));

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
        city.setSupplyStrategy(List.of(TileSupplyStrategy.MAX_FOOD));
        world.move();

        assertEquals(Arrays.asList(new Point(1, 2), new Point(0, 1)), city.getCitizenLocations());

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F3 P3 G3 S5 C1 U2 O2"));

        assertThat(russia.getSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F2 P9 G6 S10 C2 U4 O2"));
    }
}
