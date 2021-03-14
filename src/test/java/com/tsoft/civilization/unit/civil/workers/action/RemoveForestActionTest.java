package com.tsoft.civilization.unit.civil.workers.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction.FOREST_IS_REMOVED;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction.REMOVING_FOREST;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction.HILL_IS_REMOVED;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction.REMOVING_HILL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveForestActionTest {
    @Test
    public void cantRemoveForestedHill() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|. . . ", "0|. . . ", "0|. . . ",
                "1| . g .", "1| . h .", "1| . f .",
                "2|. . . ", "2|. . . ", "2|. . . ",
                "3| . . .", "3| . . .", "3| . . .");

        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);
        AbstractTile tile = map.getTile(1, 1);
        Workers workers = UnitFactory.newInstance(civilization, Workers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(workers, tile.getLocation()));

        // case 1
        // no needed technology
        assertThat(RemoveForestAction.removeForest(workers)).isEqualTo(WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY);

        // case 2
        // we must remove forest before hill's removal
        civilization.addTechnology(Technology.MINING);
        assertThat(RemoveHillAction.removeHill(workers)).isEqualTo(WorkersActionResults.FAIL_FOREST_MUST_BE_REMOVED_FIRST);
    }

    @Test
    public void removeForestedHill() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
                "-+------", "-+------", "-+------",
                "0|. . . ", "0|. . . ", "0|. . . ",
                "1| . g .", "1| . h .", "1| . f .",
                "2|. . . ", "2|. . . ", "2|. . . ",
                "3| . . .", "3| . . .", "3| . . .");

        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);
        civilization.addTechnology(Technology.MINING);

        AbstractTile tile = map.getTile(1, 1);
        Workers workers = UnitFactory.newInstance(civilization, Workers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(workers, tile.getLocation()));

        // remove a forest
        for (int i = 0; i < 4; i ++) {
            assertThat(RemoveForestAction.removeForest(workers)).isEqualTo(REMOVING_FOREST);
        }

        // forest removed
        assertThat(RemoveForestAction.removeForest(workers)).isEqualTo(FOREST_IS_REMOVED);
        assertThat(tile.getClass()).isEqualTo(Grassland.class);

        // now there is a hill
        assertThat(tile.getTerrainFeatures())
            .isNotNull()
            .returns(1, ArrayList::size)
            .extracting(list -> list.get(0))
            .isNotNull()
            .returns(Hill.class, TerrainFeature::getClass);

        // remove a hill
        for (int i = 0; i < 4; i ++) {
            assertThat(RemoveHillAction.removeHill(workers)).isEqualTo(REMOVING_HILL);
        }

        // hill removed
        assertThat(RemoveHillAction.removeHill(workers)).isEqualTo(HILL_IS_REMOVED);
        assertThat(tile.getClass()).isEqualTo(Grassland.class);

        // no any features
        assertThat(tile.getTerrainFeatures())
            .isNotNull()
            .returns(0, ArrayList::size);
    }
}
