package com.tsoft.civilization.unit.service.destroy;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.service.destroy.DestroyUnitService.LAST_SETTLERS_CANT_BE_DESTROYED;
import static com.tsoft.civilization.unit.service.destroy.DestroyUnitService.UNIT_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DestroyUnitServiceTest {

    private static final DestroyUnitService destroyUnitService = new DestroyUnitService();

    @Test
    public void buildCity1() {
        MockWorld world = MockWorld.of(new MockTilesMap(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . g ",
            "3| . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers1", new Point(1, 1))
        );

        world.startGame();

        // The only settlers can't be destroyed
        assertThat(destroyUnitService.destroy(world.unit("settlers1")))
            .isEqualTo(LAST_SETTLERS_CANT_BE_DESTROYED);

        // Add another settlers
        Settlers settlers2 = UnitFactory.newInstance(russia, Settlers.CLASS_UUID);
        assertTrue(russia.getUnitService().addUnit(settlers2, new Point(2, 2)));

        // Destroy the first ones
        assertTrue(destroyUnitService.destroy(world.unit("settlers1")).isSuccess());
        assertThat(destroyUnitService.destroy(world.unit("settlers1")))
            .isEqualTo(UNIT_NOT_FOUND);

        // The only settlers can't be destroyed
        assertThat(destroyUnitService.destroy(settlers2))
            .isEqualTo(LAST_SETTLERS_CANT_BE_DESTROYED);
    }
}
