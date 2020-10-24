package com.tsoft.civilization.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.MapRender;
import com.tsoft.civilization.world.generator.WorldGeneratorService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CivilizationTest {

    private static final MapRender mapRender = new MapRender(CivilizationTest.class);

    @Test
    public void getCivilizationsStartPoints1() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . .");
        mapRender.createSvg(map);

        List<Point> locations = WorldGeneratorService.getCivilizationsStartLocations(1, map);
        assertEquals(1, locations.size());
        assertEquals(new Point(1, 1), locations.get(0));
    }

    @Test
    public void getCivilizationsStartPoints2() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|g g . ",
            "1| . . .",
            "2|. . g ",
            "3| . . .");
        mapRender.createSvg(map);

        List<Point> locations = WorldGeneratorService.getCivilizationsStartLocations(2, map);

        assertEquals(2, locations.size());
        assertTrue(locations.contains(new Point(2, 2)));
        assertTrue(locations.contains(new Point(0, 0)) || locations.contains(new Point(1, 0)));
    }

    // Test for Civilization's initialization
    // After initialization, there must be Settlers and Warriors units
    @Test
    public void initCivilization1() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. g . ",
            "3| . . .");
        mapRender.createSvg(map);

        MockWorld world = new MockWorld(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        civilization.units().addFirstUnits();
        UnitList<?> units = civilization.units().getUnits();
        assertEquals(2, units.size());
        assertEquals(1, units.getUnitClassCount(Settlers.class));
        assertEquals(1, units.getUnitClassCount(Warriors.class));
    }


    // Test for Civilization's initialization
    // There is only one tile available, so after initialization, there must be only Settlers unit
    @Test
    public void initCivilization2() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . .");
        mapRender.createSvg(map);

        MockWorld world = new MockWorld(map);
        Civilization civilization = world.createCivilization(RUSSIA);
        civilization.units().addFirstUnits();

        UnitList<?> units = civilization.units().getUnits();
        assertEquals(1, units.size());
        assertEquals(1, units.getUnitClassCount(Settlers.class));
    }
}
