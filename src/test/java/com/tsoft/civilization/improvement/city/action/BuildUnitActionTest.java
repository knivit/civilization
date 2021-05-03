package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.unit.CivilizationUnitService;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.SupplyMock;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class BuildUnitActionTest {

    @Test
    public void failToBuildUnitNoTechnology() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0))
        );

        assertThat(BuildUnitAction.buildUnit(world.city("Moscow"), Archers.CLASS_UUID))
            .isEqualTo(CityActionResults.WRONG_ERA_OR_TECHNOLOGY);
    }

    @Test
    public void buildUnit() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0))
        );

        russia.addTechnology(Technology.ARCHERY);

        City city = world.city("Moscow");
        city.setPassScore(1);

        assertThat(russia.calcSupply())
            .usingComparator(SupplyMock::compare)
            .isEqualTo(SupplyMock.of("F1 P3 G3 S4 C1 H0 U1 O1"));

        // Start build Archers
        ActionAbstractResult actionResult = BuildUnitAction.buildUnit(city, Archers.CLASS_UUID);
        assertThat(actionResult)
            .isEqualTo(CityActionResults.UNIT_CONSTRUCTION_IS_STARTED);

        // Wait till it builds
        Archers archers = UnitFactory.newInstance(russia, Archers.CLASS_UUID);
        int neededSteps = archers.getProductionCost() / 3;
        for (int i = 0; i < neededSteps; i ++) {
            world.move();

            assertThat(city.getConstructions()).isNotEmpty();

            assertThat(russia.getUnitService())
                .returns(0, CivilizationUnitService::size);
        }
        world.move();

        assertThat(city.getConstructions()).isEmpty();

        assertThat(russia.getUnitService().getUnits())
            .hasSize(1)
            .allMatch(e -> e.getClassUuid().equals(Archers.CLASS_UUID))
            .allMatch(e -> e.getLocation().equals(city.getLocation()))
            .allMatch(e -> e.getCivilization().equals(city.getCivilization()));
    }
}
