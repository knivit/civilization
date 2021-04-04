package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.SupplyMock;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(actionResult).isEqualTo(CityActionResults.UNIT_CONSTRUCTION_IS_STARTED);

        // Wait till it builds
        Archers archers = UnitFactory.newInstance(civilization, Archers.CLASS_UUID);
        int neededSteps = archers.getProductionCost() / 3;
        for (int i = 0; i < neededSteps; i ++) {
            world.move();

            assertFalse(city.getConstructions().isEmpty());
            assertEquals(0, civilization.units().size());
        }
        world.move();

        assertTrue(city.getConstructions().isEmpty());
        assertThat(civilization.units().getUnits())
            .hasSize(1)
            .allMatch(e -> e.getClassUuid().equals(Archers.CLASS_UUID))
            .allMatch(e -> e.getLocation().equals(city.getLocation()))
            .allMatch(e -> e.getCivilization().equals(city.getCivilization()));
    }
}
