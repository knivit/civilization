package com.tsoft.civilization.unit.service.destroy;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.action.destroy.DestroyUnitService;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.DefaultUnitActionsResults.UNIT_NOT_FOUND;
import static com.tsoft.civilization.unit.action.destroy.DestroyUnitService.LAST_SETTLERS_CANT_BE_DESTROYED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DestroyUnitServiceTest {

    private static final DestroyUnitService destroyUnitService = new DestroyUnitService();

    @Test
    void buildCity1() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . g ",
            "3| . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers1", new Point(1, 1))
        );

        // The only settlers can't be destroyed
        assertThat(destroyUnitService.destroy(world.get("settlers1"))).isEqualTo(LAST_SETTLERS_CANT_BE_DESTROYED);

        // Add another settlers
        AbstractUnit settlers2 = UnitFactory.newInstance(russia, "Settlers");
        assertTrue(russia.getUnitService().addUnit(settlers2, new Point(2, 2)));
        world.nextYear();

        // Destroy the first ones
        assertTrue(destroyUnitService.destroy(world.get("settlers1")).isSuccess());
        assertThat(destroyUnitService.destroy(world.get("settlers1"))).isEqualTo(UNIT_NOT_FOUND);

        // The only settlers can't be destroyed
        assertThat(destroyUnitService.destroy(settlers2)).isEqualTo(LAST_SETTLERS_CANT_BE_DESTROYED);
    }
}
