package com.tsoft.civilization.action.city;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.Archers;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.economic.CivilizationScoreMock;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BuildUnitActionTest {
    @BeforeClass
    public static void classSetUp() {
        DefaultLogger.createLogger(BuildUnitActionTest.class.getSimpleName());
    }

    @Test
    public void failToBuildUnitNoTechnology() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization civilization = new Civilization(mockWorld, 0);
        City city = new City(civilization, new Point(2, 0));

        assertEquals(CityActionResults.WRONG_ERA_OR_TECHNOLOGY, BuildUnitAction.buildUnit(city, Archers.CLASS_UUID));
    }

    @Test
    public void buildUnit() {
        MockWorld mockWorld = MockWorld.newSimpleWorld();
        Civilization civilization = new Civilization(mockWorld, 0);
        civilization.addTechnology(Technology.ARCHERY);
        City city = new City(civilization, new Point(2, 0));
        city.setPassScore(1);

        assertTrue(new CivilizationScoreMock(civilization, 0, 3, 3, 3, 1, 0, 0).isEqualTo(civilization.getCivilizationScore()));
        assertEquals(CityActionResults.UNIT_CONSTRUCTION_IS_STARTED, BuildUnitAction.buildUnit(city, Archers.CLASS_UUID));

        int neededSteps = Archers.INSTANCE.getProductionCost() / 3;
        for (int i = 0; i < neededSteps; i ++) {
            mockWorld.step();

            assertNotNull(city.getConstruction().getObject());
            assertEquals(0, civilization.getUnits().size());
        }

        mockWorld.step();

        assertNull(city.getConstruction());
        assertEquals(1, civilization.getUnits().size());

        AbstractUnit unit = civilization.getUnits().findByClassUuid(Archers.CLASS_UUID);
        assertNotNull(unit);
        assertNotNull(unit.getId());
        assertEquals(city.getLocation(), unit.getLocation());
        assertEquals(city.getCivilization(), unit.getCivilization());
    }
}
