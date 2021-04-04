package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.civil.greatartist.GreatArtist;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.UnitRoute;
import com.tsoft.civilization.util.Dir6;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.web.render.WorldRender;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.MoveUnitAction.NO_LOCATIONS_TO_MOVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveUnitActionTest {

    @Test
    public void fail_not_enough_passing_score_trivial() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        Settlers settlers = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers, new Point(1, 1)));

        // try out all possible directions - it must be impossible
        for (Dir6 dir : Dir6.staticGetDirs(1)) {
            UnitRoute route = new UnitRoute(dir);

            ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers, route);

            assertThat(moveResults)
                .hasSize(1)
                .containsExactly(UnitMoveResult.FAIL_NOT_ENOUGH_PASSING_SCORE);
        }
    }

    @Test
    public void success_moved_trivial() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. g g ",
            "1| g g g",
            "2|. g g ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // try out all possible directions - it must be OK
        for (Dir6 dir : Dir6.staticGetDirs(1)) {
            Settlers settlers = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
            assertTrue(civilization.units().addUnit(settlers, new Point(1, 1)));
            UnitRoute route = new UnitRoute(dir);
            settlers.setPassScore(1);

            ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers, route);

            assertThat(moveResults)
                .hasSize(1)
                .containsExactly(UnitMoveResult.SUCCESS_MOVED);
        }
    }

    @Test
    public void success_moved_without_obstacles() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 4 5 ",
            "-+------------",
            "0|g . . g . g ",
            "1| g g g . . .",
            "2|g . . . g g ",
            "3| . . . g . g");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        // try one complex route - it must be OK
        Settlers settlers = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers, new Point(1, 1)));
        settlers.setPassScore(10);

        UnitRoute route = new UnitRoute();
        route.add(new Dir6(1, 0));
        route.add(new Dir6(1, -1));
        route.add(new Dir6(0, -1));
        route.add(new Dir6(1, -1));
        route.add(new Dir6(1, 0));
        route.add(new Dir6(0, 1));
        route.add(new Dir6(0, 1));
        route.add(new Dir6(1, 0));
        route.add(new Dir6(0, 1));
        route.add(new Dir6(1, 0));

        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers, route);

        assertThat(moveResults)
            .hasSize(10)
            .allMatch(p -> p.equals(UnitMoveResult.SUCCESS_MOVED));

        assertThat(settlers)
            .returns(new Point(1, 1), Settlers::getLocation)
            .returns(0, Settlers::getPassScore);

        assertThat(route).isEmpty();
    }

    // Try to move one unit on another - they must be swapped
    // conditions:
    // 1) both units have movements
    // 2) they are located near each other
    // 3) they are the same unit type
    @Test
    public void success_swapped() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g g",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(1, 1)));
        settlers1.setPassScore(1);

        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(2, 1)));
        settlers2.setPassScore(1);

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers1, route);

        assertThat(moveResults)
            .hasSize(1)
            .containsExactly(UnitMoveResult.SUCCESS_SWAPPED);

        assertThat(settlers1)
            .returns(new Point(2, 1), Settlers::getLocation)
            .returns(0, Settlers::getPassScore);

        assertThat(settlers2)
            .returns(new Point(1, 1), Settlers::getLocation)
            .returns(0, Settlers::getPassScore);

        assertThat(route).isEmpty();
    }

    // Try to move one unit on another - they must NOT be swapped
    // conditions:
    // 1) both units DON'T have movements
    // 2) they are located near each other
    // 3) they are the same unit type
    @Test
    public void fail_not_enough_passing_score_to_swap() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g g",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(1, 1)));
        settlers1.setPassScore(1);

        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(2, 1)));
        settlers2.setPassScore(0);

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers1, route);

        assertThat(moveResults)
            .hasSize(1)
            .containsExactly(UnitMoveResult.FAIL_NOT_ENOUGH_PASSING_SCORE_TO_SWAP);

        assertThat(settlers1)
            .returns(new Point(1, 1), Settlers::getLocation)
            .returns(1, Settlers::getPassScore);

        assertThat(settlers2)
            .returns(new Point(2, 1), Settlers::getLocation)
            .returns(0, Settlers::getPassScore);

        assertThat(route)
            .hasSize(1)
            .containsExactly(new Dir6(1, 0));
    }

    // Try to move one unit on another - they must NOT be swapped
    // conditions:
    // 1) both units have movements
    // 2) they are NOT located near each other
    // 3) they are the same unit type
    @Test
    public void fail_swapping_must_be_the_only_move() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. g . ",
            "3| . g .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        Settlers settlers1 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers1, new Point(1, 1)));
        settlers1.setPassScore(2);

        Settlers settlers2 = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(settlers2, new Point(1, 3)));
        settlers2.setPassScore(1);

        UnitRoute route = new UnitRoute(new Dir6(0, 1), new Dir6(0, 1));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers1, route);

        assertThat(moveResults)
            .hasSize(2)
            .containsExactly(UnitMoveResult.SUCCESS_MOVED, UnitMoveResult.FAIL_SWAPPING_MUST_BE_THE_ONLY_MOVE);

        assertThat(settlers1)
            .returns(new Point(1, 2), Settlers::getLocation)
            .returns(1, Settlers::getPassScore);

        assertThat(settlers2)
            .returns(new Point(1, 3), Settlers::getLocation)
            .returns(1, Settlers::getPassScore);

        assertThat(route)
            .hasSize(1)
            .containsExactly(new Dir6(0, 1));
    }

    // Try to move one unit on another - they must NOT be swapped
    // conditions:
    // 1) both units have movements
    // 2) they are located near each other
    // 3) they are NOT the same unit type
    @Test
    public void success_moved_two_units_on_the_same_tile() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g g",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        Warriors warriors = UnitFactory.newInstance(civilization, Warriors.CLASS_UUID);
        assertTrue(civilization.units().addUnit(warriors, new Point(1, 1)));
        warriors.setPassScore(1);

        Workers workers = UnitFactory.newInstance(civilization, Workers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(workers, new Point(2, 1)));
        workers.setPassScore(1);

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(warriors, route);

        assertThat(moveResults)
            .hasSize(1)
            .containsExactly(UnitMoveResult.SUCCESS_MOVED);

        assertThat(warriors)
            .returns(new Point(2, 1), Warriors::getLocation)
            .returns(0, Warriors::getPassScore);

        assertThat(workers)
            .returns(new Point(2, 1), Workers::getLocation)
            .returns(1, Workers::getPassScore);

        assertThat(route).isEmpty();
    }

    @Test
    public void success_entered_into_city() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g g",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        Workers workers = UnitFactory.newInstance(civilization, Workers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(workers, new Point(1, 1)));
        workers.setPassScore(1);

        City city = civilization.createCity(new Point(2, 1));

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(workers, route);

        assertThat(moveResults)
            .hasSize(1)
            .containsExactly(UnitMoveResult.SUCCESS_ENTERED_INTO_OWN_CITY);

        assertThat(workers)
            .returns(new Point(2, 1), Workers::getLocation)
            .returns(0, Workers::getPassScore);

        assertThat(route).isEmpty();
    }

    // There is Great Artist in a city
    // Warriors can't enter to city because of that
    @Test
    public void fail_tile_occupied_in_city() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g g",
            "2|. . . ",
            "3| . . .");
        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        City city = civilization.createCity(new Point(2, 1));
        GreatArtist artist = UnitFactory.newInstance(civilization, GreatArtist.CLASS_UUID);
        assertTrue(civilization.units().addUnit(artist, city.getLocation()));
        Workers workers = UnitFactory.newInstance(civilization, Workers.CLASS_UUID);
        assertTrue(civilization.units().addUnit(workers, new Point(1, 1)));

        ActionAbstractResult result = MoveUnitAction.move(workers, city.getLocation());

        assertThat(result).isEqualTo(NO_LOCATIONS_TO_MOVE);

        assertThat(civilization.units().getUnits())
            .hasSize(2)
            .containsExactly(artist, workers);

        assertThat(civilization.cities().getCityAtLocation(new Point(2, 1)))
            .isEqualTo(city);

        assertThat(artist)
            .returns(city.getLocation(), GreatArtist::getLocation)
            .returns(2, GreatArtist::getPassScore);

        assertThat(civilization.units().getUnitsAtLocation(new Point(2, 1)))
            .hasSize(1)
            .containsExactly(artist);

        assertThat(workers)
            .returns(new Point(1, 1), Workers::getLocation)
            .returns(5, Workers::getPassScore);
    }

    @Test
    public void get_tiles_to_move() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------",
            "0|g g g g g ", "0|. . . . . ",
            "1| g g g g g", "1| . . . . .",
            "2|g g g g g ", "2|M . . . . ",
            "3| g g g g g", "3| . M M . .",
            "4|g g g g g ", "4|M . . . . ",
            "5| g g g g g", "5| . . . . .");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);

        Workers workers1 = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers1, new Point(2, 2)));
        workers1.setPassScore(2);

        // one tile (possible to move in) is occupied by foreign workers
        // it must not be available
        Workers workers2 = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(workers2, new Point(2, 1)));

        Set<Point> locationsToMove = MoveUnitAction.getLocationsToMove(workers1);
        assertThat(locationsToMove)
            .hasSize(10)
            .containsExactly(new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 0), new Point(1, 2),
                new Point(0, 3), new Point(3, 1), new Point(3, 2), new Point(4, 2), new Point(3, 3));
    }

    @Test
    public void find_route1() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------",
            "0|g g g g g ", "0|. . . . . ",
            "1| g g g g g", "1| . M M . .",
            "2|g g g g g ", "2|M . . M . ",
            "3| g g g g g", "3| . M M . .",
            "4|g g g g g ", "4|M . . . . ",
            "5| g g g g g", "5| . . . . .");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);

        Workers workers = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers, new Point(2, 2)));
        WorldRender.of(this).createHtml(world, c1);

        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertThat(route)
            .hasSize(3)
            .containsExactly(new Dir6(-1, 0), new Dir6(-1, -1), new Dir6(1, -1));
    }

    @Test
    public void find_route2() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------",
            "0|g g g g g ", "0|. . . . . ",
            "1| g g g g g", "1| M M M . .",
            "2|g g g g g ", "2|M . . M . ",
            "3| g g g g g", "3| . M M . .",
            "4|g g g g g ", "4|M . . . . ",
            "5| g g g g g", "5| . . . . .");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);

        Workers workers = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers, new Point(2, 2)));

        // route goes from bottom line to top (map-cyclic test)
        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertThat(route)
            .hasSize(5)
            .containsExactly(new Dir6(-1, 0), new Dir6(-1, 1), new Dir6(1, 1), new Dir6(0, 1), new Dir6(0, 1));
    }

    @Test
    public void find_route3() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------",
            "0|g g g g g ", "0|. . . . . ",
            "1| g g g g g", "1| M M M . M",
            "2|g g g g g ", "2|M . . M . ",
            "3| g g g g g", "3| . M M . M",
            "4|g g g g g ", "4|M . . . M ",
            "5| g g g g g", "5| M M M M M");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);

        Workers workers = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers, new Point(2, 2)));
        WorldRender.of(this).createHtml(world, c1);

        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertThat(route)
            .hasSize(11)
            .containsExactly(new Dir6(-1, 0), new Dir6(-1, 1), new Dir6(1, 1), new Dir6(1, 0), new Dir6(1, 0),
                new Dir6(0, -1), new Dir6(1, -1), new Dir6(-1, -1), new Dir6(0, -1), new Dir6(-1, 0), new Dir6(-1, 0));
    }

    @Test
    public void find_route4() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 ", " |0 1 2 3 4 ",
            "-+----------", "-+----------",
            "0|g g g g g ", "0|. . . . . ",
            "1| g g g g g", "1| M M M M M",
            "2|g g g g g ", "2|M . . M . ",
            "3| g g g g g", "3| . M M . M",
            "4|g g g g g ", "4|M . . . M ",
            "5| g g g g g", "5| M M M M M");
        MockWorld world = MockWorld.of(map);
        Civilization c1 = world.createCivilization(RUSSIA);

        Workers workers = UnitFactory.newInstance(c1, Workers.CLASS_UUID);
        assertTrue(c1.units().addUnit(workers, new Point(2, 2)));
        WorldRender.of(this).createHtml(world, c1);

        // there is no route here
        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertThat(route).isEmpty();
    }
}
