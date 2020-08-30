package com.tsoft.civilization.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.civil.Settlers;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.military.Warriors;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.generator.WorldGeneratorService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CivilizationTest {
    @Test
    public void getCivilizationsStartPoints1() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");

        ArrayList<Point> locations = WorldGeneratorService.getCivilizationsStartLocations(1, mockTilesMap);
        assertEquals(1, locations.size());
        assertEquals(new Point(1, 1), locations.get(0));
    }

    @Test
    public void getCivilizationsStartPoints2() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|g g . ",
                "1| . . .",
                "2|. . g ",
                "3| . . .");

        ArrayList<Point> locations = WorldGeneratorService.getCivilizationsStartLocations(2, mockTilesMap);

        assertEquals(2, locations.size());
        assertTrue(locations.get(0).equals(new Point(0, 0)) || locations.get(0).equals(new Point(1, 0)));
        assertEquals(new Point(2, 2), locations.get(1));
    }

    // Test for Civilization's initialization
    // After initialization, there must be Settlers and Warriors units
    @Test
    public void initCivilization1() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. g . ",
                "3| . . .");

        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);

        civilization.addFirstUnits();
        UnitCollection units = civilization.getUnits();
        assertEquals(2, units.size());
        assertEquals(1, units.getUnitClassCount(Settlers.class));
        assertEquals(1, units.getUnitClassCount(Warriors.class));
    }


    // Test for Civilization's initialization
    // There is only one tile available, so after initialization, there must be only Settlers unit
    @Test
    public void initCivilization2() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 ",
                "-+------",
                "0|. . . ",
                "1| . g .",
                "2|. . . ",
                "3| . . .");

        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization civilization = new Civilization(mockWorld, 0);
        civilization.addFirstUnits();

        UnitCollection units = civilization.getUnits();
        assertEquals(1, units.size());
        assertEquals(1, units.getUnitClassCount(Settlers.class));
    }
}
