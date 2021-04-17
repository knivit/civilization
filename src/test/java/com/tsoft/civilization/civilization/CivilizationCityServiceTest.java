package com.tsoft.civilization.civilization;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class CivilizationCityServiceTest {

    @Test
    public void no_cities_with_available_actions() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization c1 = world
            .civilization(RUSSIA)
            .unit(new Point(0, 2), Settlers.CLASS_UUID)
            .build();

        CivilizationCityService cityService = new CivilizationCityService(c1);

        // no cities
        assertThat(cityService.getCitiesWithAvailableActions()).isEmpty();

        // add a city
        Settlers settlers = (Settlers)c1.units().findByClassUuid(Settlers.CLASS_UUID).getAny();
        City myCity = c1.createCity(settlers);
        assertThat(cityService.getCitiesWithAvailableActions()).isEmpty();

        // add a foreign civilization (two units will be created, warriors and settlers)
        Civilization c2 = world
            .civilization(AMERICA)
            .unit(new Point(2, 2), Settlers.CLASS_UUID)
            .unit(new Point(1, 2), Warriors.CLASS_UUID)
            .build();

        assertThat(world.getCivilizationsRelations(c1, c2))
            .isNotNull()
            .returns(true, CivilizationsRelations::isNeutral);
    }
}
