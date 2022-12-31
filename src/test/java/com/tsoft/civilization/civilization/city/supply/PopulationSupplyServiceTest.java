package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.economic.SupplyMock;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.civilization.city.supply.PopulationSupplyService;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.civilization.city.supply.TileSupplyStrategy.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PopulationSupplyServiceTest {

    @Test
    public void find_location_for_citizen_with_max_food_strategy() {
        MockWorld world = MockWorld.of(MockTilesMap.of(2,
            " |0 1 2 3 ", " |0 1 2 3 ",
            "-+--------", "-+--------",
            "0|d g l . ", "0|. . . i ",
            "1| p s t .", "1| h . . a",
            "2|d g l . ", "2|. f . . ",
            "3| p s t .", "3| m j , ."));

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(0, 0))
        );

        City city = world.city("city");
        addCityLocations(world, city);

        PopulationSupplyService supplyService = new PopulationSupplyService(city);
        supplyService.setSupplyStrategy(List.of(MAX_FOOD, MAX_PRODUCTION, MAX_GOLD));

        assertThat(supplyService.findLocationForCitizen(Collections.emptyList()))
            .isEqualTo(new Point(3, 1));

        assertThat(city.calcTilesSupply(new Point(3, 1)))
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F3 P1 G1"));
    }

    @Test
    public void find_location_for_citizen_with_max_production_strategy() {
        MockWorld world = MockWorld.of(MockTilesMap.of(2,
            " |0 1 2 3 ", " |0 1 2 3 ",
            "-+--------", "-+--------",
            "0|d g l . ", "0|. . . i ",
            "1| p s t .", "1| h . . a",
            "2|d g l . ", "2|. f . . ",
            "3| p s t .", "3| m j , ."));

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(0, 0))
        );

        City city = world.city("city");
        addCityLocations(world, city);

        PopulationSupplyService supplyService = new PopulationSupplyService(city);
        supplyService.setSupplyStrategy(List.of(MAX_PRODUCTION, MAX_GOLD, MAX_FOOD));

        assertThat(supplyService.findLocationForCitizen(Collections.emptyList()))
            .isEqualTo(new Point(0, 1));

        assertThat(city.calcTilesSupply(new Point(0, 1)))
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F0 P2 G0"));
    }

    @Test
    public void find_location_for_citizen_with_max_gold_strategy() {
        MockWorld world = MockWorld.of(MockTilesMap.of(2,
            " |0 1 2 3 ", " |0 1 2 3 ",
            "-+--------", "-+--------",
            "0|d g l . ", "0|. . . i ",
            "1| p s t .", "1| h . . a",
            "2|d g l . ", "2|. f . . ",
            "3| p s t .", "3| m j , ."));

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(0, 0))
        );

        City city = world.city("city");
        addCityLocations(world, city);

        PopulationSupplyService supplyService = new PopulationSupplyService(city);
        supplyService.setSupplyStrategy(List.of(MAX_GOLD, MAX_FOOD, MAX_PRODUCTION));

        assertThat(supplyService.findLocationForCitizen(Collections.emptyList()))
            .isEqualTo(new Point(3, 1));

        assertThat(city.calcTilesSupply(new Point(3, 1)))
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F3 P1 G1"));
    }

    private void addCityLocations(World world, City city) {
        for (int y = 0; y < world.getTilesMap().getHeight(); y ++) {
            for (int x = 0; x < world.getTilesMap().getWidth(); x ++) {
                city.getTileService().addLocations(List.of(new Point(x, y)));
            }
        }
    }
}
