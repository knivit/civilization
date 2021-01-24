package com.tsoft.civilization.action.city;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.action.BuyUnitAction;
import com.tsoft.civilization.improvement.city.action.CityActionResults;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.Supply;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.AMERICA;
import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyUnitActionTest {
    @Test
    public void failToBuyUnitNoTechnology() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civilization = world.createCivilization(RUSSIA);
        City city = civilization.createCity(new Point(2, 0));

        assertEquals(CityActionResults.WRONG_ERA_OR_TECHNOLOGY, BuyUnitAction.buyUnit(city, Archers.CLASS_UUID));
    }

    @Test
    public void buyUnit() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civilization = world.createCivilization(RUSSIA);
        civilization.addTechnology(Technology.ARCHERY);
        City city = civilization.createCity(new Point(2, 0));

        Civilization puppet = world.createCivilization(AMERICA);

        city.setPassScore(1);
        Archers archers = UnitFactory.newInstance(puppet, Archers.CLASS_UUID);
        civilization.giftReceived(puppet, Supply.builder().gold(archers.getGoldCost()).build());

        assertEquals(CityActionResults.UNIT_WAS_BOUGHT, BuyUnitAction.buyUnit(city, Archers.CLASS_UUID));
    }
}
