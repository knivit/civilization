package com.tsoft.civilization.unit.civil.settlers.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.unit.action.DestroyUnitAction;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.DestroyUnitAction.LAST_SETTLERS_CANT_BE_DESTROYED;
import static com.tsoft.civilization.unit.action.DestroyUnitAction.UNIT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DestroyUnitActionTest {
    @Test
    public void buildCity1() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . g ",
                "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // The only settlers can't be destroyed
        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(1, 1)));
        assertEquals(LAST_SETTLERS_CANT_BE_DESTROYED, DestroyUnitAction.destroyUnit(settlers1));

        // Add another settlers
        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(2, 2)));

        // Destroy the first ones
        assertTrue(DestroyUnitAction.destroyUnit(settlers1).isSuccess());
        assertEquals(UNIT_NOT_FOUND, DestroyUnitAction.destroyUnit(settlers1));

        // The only settlers can't be destroyed
        assertEquals(LAST_SETTLERS_CANT_BE_DESTROYED, DestroyUnitAction.destroyUnit(settlers2));
    }
}
