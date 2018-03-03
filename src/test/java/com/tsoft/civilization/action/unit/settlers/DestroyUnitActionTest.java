package com.tsoft.civilization.action.unit.settlers;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.unit.DestroyUnitAction;
import com.tsoft.civilization.action.unit.DestroyUnitResults;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DestroyUnitActionTest {
    @Test
    public void buildCity1() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        // The only settlers can't be destroyed
        Settlers settlers1 = UnitFactory.newInstance(Settlers.INSTANCE, civilization, new Point(1, 1));
        assertEquals(DestroyUnitResults.LAST_SETTLERS_CANT_BE_DESTOYED, DestroyUnitAction.destroyUnit(settlers1));

        // Add another settlers
        Settlers settlers2 = UnitFactory.newInstance(Settlers.INSTANCE, civilization, new Point(2, 0));

        // Destroy the first ones
        DestroyUnitAction.destroyUnit(settlers1);
        assertEquals(DestroyUnitResults.UNIT_NOT_FOUND, DestroyUnitAction.destroyUnit(settlers1));

        // The only settlers can't be destroyed
        assertEquals(DestroyUnitResults.LAST_SETTLERS_CANT_BE_DESTOYED, DestroyUnitAction.destroyUnit(settlers2));
    }
}
