package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.improvement.city.action.BuyUnitAction.UNIT_WAS_BOUGHT;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BuyUnitActionTest {

    @Test
    public void fail_to_buy_unit_no_technology() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0))
        );

        world.startGame();

        assertThat(BuyUnitAction.buyUnit(world.city("Moscow"), Archers.CLASS_UUID))
            .isEqualTo(CityActionResults.WRONG_ERA_OR_TECHNOLOGY);
    }

    @Test
    public void buy_unit() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0))
        );

        world.startGame();
        russia.addTechnology(Technology.ARCHERY);
        world.city("Moscow").setPassScore(1);

        Civilization puppet = world.createCivilization(AMERICA, new MockScenario()
            .city("Columbus", new Point(2, 2))
            .archers("foreignArchers", new Point(2, 2))
        );

        AbstractUnit foreignArchers = world.unit("foreignArchers");
        Supply gift = Supply.builder().gold(foreignArchers.getGoldCost(russia)).build();
        russia.giftReceived(puppet, gift);

        assertThat(BuyUnitAction.buyUnit(world.city("Moscow"), Archers.CLASS_UUID))
            .isEqualTo(UNIT_WAS_BOUGHT);
    }
}
