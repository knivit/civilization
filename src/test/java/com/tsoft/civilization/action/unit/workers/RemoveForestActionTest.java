package com.tsoft.civilization.action.unit.workers;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.unit.civil.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RemoveForestActionTest {
    @Test
    public void cantRemoveForestedHill() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|. . . ", "0|. . . ", "0|. . . ",
                "1| . g .", "1| . h .", "1| . f .",
                "2|. . . ", "2|. . . ", "2|. . . ",
                "3| . . .", "3| . . .", "3| . . .");

        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);
        AbstractTile<?> tile = mockTilesMap.getTile(1, 1);
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID);
        civilization.addUnit(workers, tile.getLocation());

        // case 1
        // no needed technology
        assertEquals(WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY, RemoveForestAction.removeForest(workers));

        // case 2
        // we must remove forest before hill's removal
        civilization.addTechnology(Technology.MINING);
        assertEquals(WorkersActionResults.FAIL_FOREST_MUST_BE_REMOVED_FIRST, RemoveHillAction.removeHill(workers));
    }

    @Test
    public void removeForestedHill() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|. . . ", "0|. . . ", "0|. . . ",
                "1| . g .", "1| . h .", "1| . f .",
                "2|. . . ", "2|. . . ", "2|. . . ",
                "3| . . .", "3| . . .", "3| . . .");

        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);
        civilization.addTechnology(Technology.MINING);

        AbstractTile<?> tile = mockTilesMap.getTile(1, 1);
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID);
        civilization.addUnit(workers, tile.getLocation());

        for (int i = 0; i < 4; i ++) {
            assertEquals(WorkersActionResults.REMOVING_FOREST, RemoveForestAction.removeForest(workers));
        }

        ActionAbstractResult forestResult = RemoveForestAction.removeForest(workers);
        assertEquals(WorkersActionResults.FOREST_IS_REMOVED, forestResult);
        assertEquals(Grassland.class, tile.getClass());
        assertEquals(1, tile.getTerrainFeatures().size());
        assertNotNull(tile.getTerrainFeatures().getByClass(Hill.class));

        for (int i = 0; i < 4; i ++) {
            assertEquals(WorkersActionResults.REMOVING_HILL, RemoveHillAction.removeHill(workers));
        }

        ActionAbstractResult hillResult = RemoveHillAction.removeHill(workers);
        assertEquals(WorkersActionResults.HILL_IS_REMOVED, hillResult);
        assertEquals(Grassland.class, tile.getClass());
        assertEquals(0, tile.getTerrainFeatures().size());
    }
}
