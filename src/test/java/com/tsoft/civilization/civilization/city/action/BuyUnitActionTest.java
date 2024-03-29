package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.ui.CityActionResults;
import com.tsoft.civilization.civilization.city.ui.CityUnitActionResults;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.catalog.archers.Archers;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

class BuyUnitActionTest {

    private final BuyUnitAction buyUnitAction = new BuyUnitAction();

    @Test
    public void fail_to_buy_unit_no_technology() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0)));

        assertThat(buyUnitAction.buyUnit(world.city("Moscow"), Archers.CLASS_UUID))
            .isEqualTo(CityActionResults.WRONG_ERA_OR_TECHNOLOGY);
    }

    @Test
    public void buy_unit() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0)));

        russia.addTechnology(Technology.ARCHERY);
        world.city("Moscow").setPassScore(1);

        Civilization puppet = world.createCivilization(AMERICA, new MockScenario()
            .city("Columbus", new Point(2, 2))
            .archers("foreignArchers", new Point(2, 2)));

        AbstractUnit foreignArchers = world.get("foreignArchers");
        Supply gift = Supply.builder().gold(foreignArchers.getGoldCost(russia)).build();
        russia.giftReceived(puppet, gift);

        assertThat(buyUnitAction.buyUnit(world.city("Moscow"), Archers.CLASS_UUID))
            .isEqualTo(CityUnitActionResults.UNIT_WAS_BOUGHT);
    }
}