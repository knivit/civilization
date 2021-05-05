package com.tsoft.civilization.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.web.render.MapRender;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CivilizationTest {

    private static final MapRender mapRender = new MapRender(CivilizationTest.class);

    // Test for Civilization's initialization
    // After initialization, there must be Settlers and Warriors units
    @Test
    public void initCivilization1() {
        MockTilesMap map = new MockTilesMap(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. g . ",
            "3| . . .");
        mapRender.createPng(map);

        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        UnitList units = civilization.getUnitService().getUnits();

        assertThat(units).hasSize(2);
        assertEquals(1, units.getUnitClassCount(Settlers.class));
        assertEquals(1, units.getUnitClassCount(Warriors.class));
    }


    // Test for Civilization's initialization
    // There is only one tile available, so after initialization, there must be only Settlers unit
    @Test
    public void initCivilization2() {
        MockTilesMap map = new MockTilesMap(
            " |0 1 2 ",
            "-+------",
            "0|. . . ",
            "1| . g .",
            "2|. . . ",
            "3| . . .");
        mapRender.createPng(map);

        MockWorld world = MockWorld.of(map);
        Civilization civilization = world.createCivilization(RUSSIA);

        UnitList units = civilization.getUnitService().getUnits();
        assertEquals(1, units.size());
        assertEquals(1, units.getUnitClassCount(Settlers.class));
    }
}
