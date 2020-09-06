package com.tsoft.civilization.action.city;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.military.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.Supply;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyUnitActionTest {
    @Test
    public void failToBuyUnitNoTechnology() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization civilization = new Civilization(mockWorld, 0);
        City city = new City(civilization, new Point(2, 0));

        assertEquals(CityActionResults.WRONG_ERA_OR_TECHNOLOGY, BuyUnitAction.buyUnit(city, Archers.CLASS_UUID));
    }

    @Test
    public void buyUnit() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization civilization = new Civilization(mockWorld, 0);
        civilization.addTechnology(Technology.ARCHERY);
        City city = new City(civilization, new Point(2, 0));

        Civilization puppet = new Civilization(mockWorld, 1);

        city.setPassScore(1);
        civilization.giftReceived(puppet, Supply.builder().gold(Archers.STUB.getGoldCost()).build());

        assertEquals(CityActionResults.UNIT_WAS_BOUGHT, BuyUnitAction.buyUnit(city, Archers.CLASS_UUID));
    }
}
