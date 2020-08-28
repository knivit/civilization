package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.unit.util.UnitRoute;
import com.tsoft.civilization.util.Dir6;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveUnitActionTest {
    @Test
    public void failNotEnoughPassingScoreTrivial() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        // try out all possible directions - it must be impossible
        for (Dir6 dir : Dir6.staticGetDirs(1)) {
            Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 1));
            UnitRoute route = new UnitRoute(dir);

            ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers, route);

            assertEquals(1, moveResults.size());
            assertEquals(UnitMoveResult.FAIL_NOT_ENOUGH_PASSING_SCORE, moveResults.get(0));
        }
    }

    @Test
    public void successMovedTrivial() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. g g ",
                "1| g g g",
                "2|. g g ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        // try out all possible directions - it must be OK
        for (Dir6 dir : Dir6.staticGetDirs(1)) {
            Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 1));
            UnitRoute route = new UnitRoute(dir);
            settlers.setPassScore(1);

            ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers, route);

            assertEquals(1, moveResults.size());
            assertEquals(UnitMoveResult.SUCCESS_MOVED, moveResults.get(0));
        }
    }

    @Test
    public void successMovedWithoutObstacles() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 3 4 5 ",
                "-+------------",
                "0|g . . g . g ",
                "1| g g g . . .",
                "2|g . . . g g ",
                "3| . . . g . g");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        // try one complex route - it must be OK
        Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 1));
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

        assertEquals(10, moveResults.size());
        assertEquals(moveResults.size(), Collections.frequency(moveResults, UnitMoveResult.SUCCESS_MOVED));
        assertEquals(new Point(1, 1), settlers.getLocation());
        assertEquals(0, settlers.getPassScore());
        assertEquals(0, route.size());
    }

    // Try to move one unit on another - they must be swapped
    // conditions:
    // 1) both units have movements
    // 2) they are located near each other
    // 3) they are the same unit type
    @Test
    public void successSwapped() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g g",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        Settlers settlers1 = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 1));
        settlers1.setPassScore(1);

        Settlers settlers2 = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(2, 1));
        settlers2.setPassScore(1);

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers1, route);

        assertEquals(1, moveResults.size());
        assertEquals(UnitMoveResult.SUCCESS_SWAPPED, moveResults.get(0));
        assertEquals(new Point(2, 1), settlers1.getLocation());
        assertEquals(new Point(1, 1), settlers2.getLocation());
        assertEquals(0, settlers1.getPassScore());
        assertEquals(0, settlers2.getPassScore());
        assertEquals(0, route.size());
    }

    // Try to move one unit on another - they must NOT be swapped
    // conditions:
    // 1) both units DON'T have movements
    // 2) they are located near each other
    // 3) they are the same unit type
    @Test
    public void failNotEnoughPassingScoreToSwap() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g g",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        Settlers settlers1 = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 1));
        settlers1.setPassScore(1);

        Settlers settlers2 = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(2, 1));
        settlers2.setPassScore(0);

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers1, route);

        assertEquals(1, moveResults.size());
        assertEquals(UnitMoveResult.FAIL_NOT_ENOUGH_PASSING_SCORE_TO_SWAP, moveResults.get(0));
        assertEquals(new Point(1, 1), settlers1.getLocation());
        assertEquals(new Point(2, 1), settlers2.getLocation());
        assertEquals(1, settlers1.getPassScore());
        assertEquals(0, settlers2.getPassScore());
        assertEquals(1, route.size());
    }

    // Try to move one unit on another - they must NOT be swapped
    // conditions:
    // 1) both units have movements
    // 2) they are NOT located near each other
    // 3) they are the same unit type
    @Test
    public void failSwappingMustBeTheOnlyMove() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. g . ",
                "3| . g .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        Settlers settlers1 = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 1));
        settlers1.setPassScore(2);

        Settlers settlers2 = UnitFactory.newInstance(Settlers.CLASS_UUID, civilization, new Point(1, 3));
        settlers2.setPassScore(1);

        UnitRoute route = new UnitRoute(new Dir6(0, 1), new Dir6(0, 1));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(settlers1, route);

        assertEquals(2, moveResults.size());
        assertEquals(UnitMoveResult.SUCCESS_MOVED, moveResults.get(0));
        assertEquals(UnitMoveResult.FAIL_SWAPPING_MUST_BE_THE_ONLY_MOVE, moveResults.get(1));
        assertEquals(new Point(1, 2), settlers1.getLocation());
        assertEquals(new Point(1, 3), settlers2.getLocation());
        assertEquals(1, settlers1.getPassScore());
        assertEquals(1, settlers2.getPassScore());
        assertEquals(1, route.size());
    }

    // Try to move one unit on another - they must NOT be swapped
    // conditions:
    // 1) both units have movements
    // 2) they are located near each other
    // 3) they are NOT the same unit type
    @Test
    public void successMovedTwoUnitsOnTheSameTile() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g g",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID, civilization, new Point(1, 1));
        warriors.setPassScore(1);

        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, civilization, new Point(2, 1));
        workers.setPassScore(1);

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(warriors, route);

        assertEquals(1, moveResults.size());
        assertEquals(UnitMoveResult.SUCCESS_MOVED, moveResults.get(0));
        assertEquals(new Point(2, 1), warriors.getLocation());
        assertEquals(new Point(2, 1), workers.getLocation());
        assertEquals(0, warriors.getPassScore());
        assertEquals(1, workers.getPassScore());
        assertEquals(0, route.size());
    }

    @Test
    public void successEnteredIntoCity() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g g",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, civilization, new Point(1, 1));
        workers.setPassScore(1);

        City city = new City(civilization, new Point(2, 1));

        UnitRoute route = new UnitRoute(new Dir6(1, 0));
        ArrayList<UnitMoveResult> moveResults = MoveUnitAction.moveByRoute(workers, route);

        assertEquals(1, moveResults.size());
        assertEquals(UnitMoveResult.SUCCESS_ENTERED_INTO_OWN_CITY, moveResults.get(0));
        assertEquals(new Point(2, 1), workers.getLocation());
        assertEquals(0, workers.getPassScore());
        assertEquals(0, route.size());
    }

    // There is Great Artist in a city
    // Warriors can't enter to city because of that
    @Test
    public void failTileOccupiedInCity() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g g",
                "2|. . . ",
                "3| . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        City city = new City(civilization, new Point(2, 1));
        GreatArtist artist = UnitFactory.newInstance(GreatArtist.CLASS_UUID, civilization, city.getLocation());
        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, civilization, new Point(1, 1));

        ActionAbstractResult result = MoveUnitAction.move(workers, city.getLocation());

        assertEquals(MoveUnitActionResults.NO_LOCATIONS_TO_MOVE, result);
        assertEquals(city.getLocation(), artist.getLocation());
        assertEquals(new Point(1, 1), workers.getLocation());
        assertEquals(2, artist.getPassScore());
        assertEquals(5, workers.getPassScore());

        assertEquals(city, civilization.getCityAtLocation(new Point(2, 1)));
        UnitCollection units = civilization.getUnits();
        assertEquals(2, units.size());
        units = civilization.getUnitsAtLocation(new Point(2, 1));
        assertEquals(1, units.size());
    }

    @Test
    public void getTilesToMove() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 3 4 ", " |0 1 2 3 4 ",
                "-+----------", "-+----------",
                "0|g g g g g ", "0|. . . . . ",
                "1| g g g g g", "1| . . . . .",
                "2|g g g g g ", "2|M . . . . ",
                "3| g g g g g", "3| . M M . .",
                "4|g g g g g ", "4|M . . . . ",
                "5| g g g g g", "5| . . . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);

        Workers workers1 = UnitFactory.newInstance(Workers.CLASS_UUID, c1, new Point(2, 2));
        workers1.setPassScore(2);

        // one tile (possible to move in) is occupied by foreign workers
        // it must not be available
        Workers workers2 = UnitFactory.newInstance(Workers.CLASS_UUID, c2, new Point(2, 1));

        Set<Point> locationsToMove = MoveUnitAction.getLocationsToMove(workers1);
        assertEquals(10, locationsToMove.size());
    }

    @Test
    public void findRoute1() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 3 4 ", " |0 1 2 3 4 ",
                "-+----------", "-+----------",
                "0|g g g g g ", "0|. . . . . ",
                "1| g g g g g", "1| . M M . .",
                "2|g g g g g ", "2|M . . M . ",
                "3| g g g g g", "3| . M M . .",
                "4|g g g g g ", "4|M . . . . ",
                "5| g g g g g", "5| . . . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);

        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, c1, new Point(2, 2));
        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertEquals(3, route.size());
    }

    @Test
    public void findRoute2() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 3 4 ", " |0 1 2 3 4 ",
                "-+----------", "-+----------",
                "0|g g g g g ", "0|. . . . . ",
                "1| g g g g g", "1| M M M . .",
                "2|g g g g g ", "2|M . . M . ",
                "3| g g g g g", "3| . M M . .",
                "4|g g g g g ", "4|M . . . . ",
                "5| g g g g g", "5| . . . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);

        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, c1, new Point(2, 2));

        // route goes from bottom line to top (map-cyclic test)
        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertEquals(5, route.size());
    }

    @Test
    public void findRoute3() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 3 4 ", " |0 1 2 3 4 ",
                "-+----------", "-+----------",
                "0|g g g g g ", "0|. . . . . ",
                "1| g g g g g", "1| M M M . M",
                "2|g g g g g ", "2|M . . M . ",
                "3| g g g g g", "3| . M M . M",
                "4|g g g g g ", "4|M . . . M ",
                "5| g g g g g", "5| M M M M M");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);

        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, c1, new Point(2, 2));

        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertEquals(11, route.size());
    }

    @Test
    public void findRoute4() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 3 4 ", " |0 1 2 3 4 ",
                "-+----------", "-+----------",
                "0|g g g g g ", "0|. . . . . ",
                "1| g g g g g", "1| M M M M M",
                "2|g g g g g ", "2|M . . M . ",
                "3| g g g g g", "3| . M M . M",
                "4|g g g g g ", "4|M . . . M ",
                "5| g g g g g", "5| M M M M M");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);

        Workers workers = UnitFactory.newInstance(Workers.CLASS_UUID, c1, new Point(2, 2));

        // there is no route here
        UnitRoute route = MoveUnitAction.findRoute(workers, new Point(1, 0));

        assertEquals(0, route.size());
    }
}
