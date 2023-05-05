package com.tsoft.civilization.world;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.HappinessMock;
import com.tsoft.civilization.economic.UnhappinessMock;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.tile.TileSupplyStrategy;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.economic.SupplyMock;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.WorldRender;
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
    void noCitiesScore() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        world.nextYear();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(Supply.EMPTY);
    }

    @Test
    void oneCityOneTileScore() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . ."));

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
        world.nextYear();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F1 P3 G3 S4 C1"));

        assertThat(russia.getHappiness())
            .usingComparator(HappinessMock::compare)
            .isEqualTo(HappinessMock.of("D9 L0 B0 W0 N0 P0 G0"));

        assertThat(russia.getUnhappiness())
            .usingComparator(UnhappinessMock::compare)
            .isEqualTo(UnhappinessMock.of("C3 M1 A0 U0 P1 T2"));

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
        world.nextYear();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F1 P3 G3 S4 C1"));

        assertThat(russia.getSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F2 P6 G6 S8 C2"));

        assertThat(russia.getHappiness())
            .usingComparator(HappinessMock::compare)
            .isEqualTo(HappinessMock.of("D9 L0 B0 W0 N0 P0 G0"));

        assertThat(russia.getUnhappiness())
            .usingComparator(UnhappinessMock::compare)
            .isEqualTo(UnhappinessMock.of("C3 M1 A0 U0 P1 T1"));
    }

    @Test
    void oneCityAllTypesOfTilesScore() {
        MockWorld world = MockWorld.of(MockTilesMap.of(3,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------", "-+----------",
            "0|. g d . . ", "0|. . . i . ", "0|. . . . . ",
            "1| l g p s t", "1| . M . . .", "1| . . . . .",
            "2|g g g g g ", "2|n f h h m ", "2|. . . f . ",
            "3| . g p . .", "3| . j o . .", "3| . . . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 1))
        );

        City city = world.city("Moscow");

        // add all other tiles
        Collection<Point> locations = world.getTilesMap().getLocationsAround(new Point(2, 1), 3);
        city.getTileService().addLocations(locations);
        assertEquals(20, city.getTileService().getTerritory().size());

        // add citizens to work on the tiles
        // five of them will be unemployed (desert, ice, mountain, snow and jungle)
        for (int i = 0; i < 4 * 5 - 1; i ++) {
            city.addCitizen();
        }
        WorldRender.of(this).createHtml(world, russia);
        assertThat(city.getCitizenCount()).isEqualTo(20);
        assertThat(city.getCitizenLocations().size()).isEqualTo(15);

        world.nextYear();

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F2 P14 G10 S18 C1 A0 T0 E0"));

        // check the citizens don't work on tiles with empty supply
        assertTrue(Collections.disjoint(city.getCitizenLocations(), Arrays.asList(
            new Point(2, 0), // desert
            new Point(3, 0), // ice
            new Point(1, 1), // mountain
            new Point(3, 1), // snow
            new Point(1, 3)  // jungle
        )));
    }

    @Test
    void maxSupplyStrategies() {
        MockWorld world = MockWorld.of(MockTilesMap.of(2,
            " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------",
            "0|. g . ", "0|. h . ",
            "1| g g .", "1| m f .",
            "2|. g . ", "2|. n . ",
            "3| . . .", "3| . . ."));

        // Default strategies: MAX_FOOD, MAX_PRODUCTION, MAX_GOLD
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(1, 1))
        );

        City city = world.city("Moscow");

        // add second citizen
        city.addCitizen();

        assertThat(city.getCitizenLocations())
            .containsExactlyInAnyOrder(new Point(1, 2), new Point(1, 1));

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F3 P4 G3 S5 C1"));

        // Step 1
        city.setSupplyStrategy(List.of(TileSupplyStrategy.MAX_PRODUCTION));
        world.nextYear();

        assertThat(city.getCitizenLocations())
            .containsExactlyInAnyOrder(new Point(1, 2), new Point(1, 1));

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F3 P4 G3 S5 C1"));

        // Step 2
        city.setSupplyStrategy(List.of(TileSupplyStrategy.MAX_FOOD));
        world.nextYear();

        assertThat(city.getCitizenLocations())
            .containsExactlyInAnyOrder(new Point(1, 2), new Point(1, 1), new Point(2, 0));

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F3 P4 G4 S6 C1"));
    }
}
