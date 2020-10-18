package com.tsoft.civilization.action.city;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.action.BuildUnitAction;
import com.tsoft.civilization.improvement.city.action.CityActionResults;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.SupplyMock;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BuildUnitActionTest {
    @Test
    public void failToBuildUnitNoTechnology() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civilization = world.createCivilization(RUSSIA);
        City city = civilization.createCity(new Point(2, 0));

        assertEquals(CityActionResults.WRONG_ERA_OR_TECHNOLOGY, BuildUnitAction.buildUnit(city, Archers.CLASS_UUID));
    }

    @Test
    public void buildUnit() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civilization = world.createCivilization(RUSSIA);
        civilization.addTechnology(Technology.ARCHERY);

        City city = civilization.createCity(new Point(2, 0));
        city.setPassScore(1);
        assertTrue(SupplyMock.equals("F1 P3 G3 S4 C1 H0 U1 O1", civilization.calcSupply()));

        // Start build Archers
        ActionAbstractResult actionResult = BuildUnitAction.buildUnit(city, Archers.CLASS_UUID);
        assertEquals(CityActionResults.UNIT_CONSTRUCTION_IS_STARTED, actionResult);

        // Wait till it builds
        Archers archers = UnitFactory.newInstance(Archers.CLASS_UUID);
        int neededSteps = archers.getProductionCost() / 3 + 1;
        for (int i = 0; i < neededSteps; i ++) {
            world.move();

            assertNotNull(city.getConstruction().getObject());
            assertEquals(0, civilization.units().size());
        }
        world.move();

        assertNull(city.getConstruction());
        assertEquals(1, civilization.units().size());

        UnitList<?> list = civilization.units().findByClassUuid(Archers.CLASS_UUID);
        assertTrue(list != null && list.size() == 1);
        assertNotNull(list.getAny().getId());
        assertEquals(city.getLocation(), list.getAny().getLocation());
        assertEquals(city.getCivilization(), list.getAny().getCivilization());
    }
}
