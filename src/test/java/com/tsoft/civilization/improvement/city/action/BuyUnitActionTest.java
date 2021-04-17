package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.Supply;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BuyUnitActionTest {

    @Test
    public void fail_to_buy_unit_no_technology() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization civilization = world
            .civilization(RUSSIA)
            .city(new Point(2, 0))
            .build();

        City city = civilization.cities().getAny();

        assertThat(BuyUnitAction.buyUnit(city, Archers.CLASS_UUID))
            .isEqualTo(CityActionResults.WRONG_ERA_OR_TECHNOLOGY);
    }

    @Test
    public void buy_unit() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization civilization = world
            .civilization(RUSSIA)
            .city(new Point(2, 0))
            .build();

        civilization.addTechnology(Technology.ARCHERY);
        City city = civilization.cities().getAny();
        city.setPassScore(1);

        Civilization puppet = world
            .civilization(AMERICA)
            .city(new Point(2, 2))
            .unit(new Point(2, 2), Archers.CLASS_UUID)
            .build();

        AbstractUnit archers = puppet.units().getAny();
        civilization.giftReceived(puppet, Supply.builder().gold(archers.getGoldCost()).build());

        assertThat(BuyUnitAction.buyUnit(city, Archers.CLASS_UUID))
            .isEqualTo(CityActionResults.UNIT_WAS_BOUGHT);
    }
}
