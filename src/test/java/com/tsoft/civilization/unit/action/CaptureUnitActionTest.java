package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.WorldRender;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.CaptureUnitAction.FOREIGN_UNIT_CAPTURED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaptureUnitActionTest {

    @Test
    public void targets_for_melee_unit_to_capture() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 ",
            "-+--------",
            "0|. g g . ",
            "1| . g g .",
            "2|. . g . ",
            "3| . . . .");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);

        Warriors warriors = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors, new Point(1, 0)));
        Workers foreignWorkers = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWorkers, new Point(1, 1)));

        WorldRender.of(this).createHtml(world, c1);

        // see what we can capture
        List<Point> locations = CaptureUnitAction.getLocationsToCapture(warriors);
        assertThat(locations)
            .isNotNull()
            .hasSize(1);

        assertThat(CaptureUnitAction.getTargetToCaptureAtLocation(warriors, locations.get(0)))
            .isEqualTo(foreignWorkers);

        // capture the foreign workers and move on it's tile
        assertThat(CaptureUnitAction.capture(warriors, foreignWorkers.getLocation()))
            .isEqualTo(FOREIGN_UNIT_CAPTURED);

        assertThat(warriors)
            .returns(0, Warriors::getPassScore)
            .returns(new Point(1, 1), Warriors::getLocation);

        assertThat(foreignWorkers)
            .returns(warriors.getLocation(), Workers::getLocation)
            .returns(warriors.getCivilization(), Workers::getCivilization)
            .returns(0, Workers::getPassScore);
    }

    @Test
    public void targets_for_ranged_unit_to_capture() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 5 6 ", " |0 1 2 3 4 5 6 ",
            "-+--------------", "-+--------------",
            "0|. . g g . . . ", "0|. . M M . . . ",
            "1| . . g g . . .", "1| . . . j . . .",
            "2|. . g . g . . ", "2|. . f . . . . ",
            "3| . g g g . . .", "3| . . . h . . .");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);

        // our forces
        Archers archers = UnitFactory.newInstance(c1, Archers.CLASS_UUID);
        assertTrue(c1.units().addUnit(archers, new Point(2, 1)));

        // foreign forces
        Workers foreignWorkers = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWorkers, new Point(3, 1)));

        // see what we can capture
        List<Point> locations = CaptureUnitAction.getLocationsToCapture(archers);
        assertThat(locations).hasSize(1);
        assertThat(CaptureUnitAction.getTargetToCaptureAtLocation(archers, locations.get(0))).isEqualTo(foreignWorkers);

        // capture the foreign workers
        assertThat(CaptureUnitAction.capture(archers, foreignWorkers.getLocation())).isEqualTo(FOREIGN_UNIT_CAPTURED);
        assertThat(foreignWorkers.getLocation()).isEqualTo(archers.getLocation());
        assertThat(foreignWorkers.getCivilization()).isEqualTo(archers.getCivilization());
    }
}
