package com.tsoft.civilization.civilization;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class CivilizationCityServiceTest {

    @Test
    public void no_cities_with_available_actions() {
        World world = MockWorld.newSimpleWorld();
        Civilization c1 = world.createCivilization(PlayerType.HUMAN, RUSSIA);

        CivilizationCityService cityService = new CivilizationCityService(c1);

        // no cities
        assertThat(cityService.getCitiesWithAvailableActions()).isEmpty();

        // add a city
        City myCity = c1.createCity(new Point(0, 2));
        assertThat(cityService.getCitiesWithAvailableActions()).isEmpty();

        // add a foreign civilization (two units will be created, warriors and settlers)
        Civilization c2 = world.createCivilization(PlayerType.HUMAN, AMERICA);
        assertThat(c2.units().getCivilCount()).isEqualTo(1);
        assertThat(c2.units().getMilitaryCount()).isEqualTo(1);
        assertThat(world.getCivilizationsRelations(c1, c2))
            .isNotNull()
            .returns(true, CivilizationsRelations::isNeutral);
    }
}
